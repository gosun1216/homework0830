package kh.homework.app01.board.service;

import kh.homework.app01.board.dto.AttachmentDto;
import kh.homework.app01.board.dto.BoardDto;
import kh.homework.app01.board.entity.AttachmentEntity;
import kh.homework.app01.board.entity.BoardEntity;
import kh.homework.app01.board.repository.AttachmentRepository;
import kh.homework.app01.board.repository.BoardRepository;
import kh.homework.app01.member.repository.MemberRepository;
import kh.homework.app01.common.exception.BadRequestException;
import kh.homework.app01.common.exception.NotFoundException;
import kh.homework.app01.member.entity.MemberEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final AttachmentRepository attachmentRepository;
    private final MemberRepository memberRepository;

    private static final int MAX_ATTACH = 10;

    @Transactional
    public BoardDto write(BoardDto dto, List<AttachmentDto> attachments) {
        if (dto.getTitle() == null || dto.getTitle().isBlank())
            throw new BadRequestException("title 필수");
        if (dto.getContent() == null || dto.getContent().isBlank())
            throw new BadRequestException("content 필수");
        if (dto.getWriterNo() == null)
            throw new BadRequestException("writerNo 필수");

        MemberEntity writer = memberRepository.findByNo(dto.getWriterNo());
        if (writer == null) throw new NotFoundException("작성자 없음: " + dto.getWriterNo());

        BoardEntity board = BoardEntity.from(dto, writer);
        boardRepository.insert(board);

        if (attachments != null && !attachments.isEmpty()) {
            if (attachments.size() > MAX_ATTACH)
                throw new BadRequestException("첨부는 최대 " + MAX_ATTACH + "개");
            for (AttachmentDto attachmentDto : attachments) {
                if (attachmentDto.getOriginalName() == null || attachmentDto.getStoredPath() == null || attachmentDto.getSizeBytes() == null)
                    throw new BadRequestException("첨부 필드 누락");

                AttachmentEntity attachmentEntity = AttachmentEntity.from(attachmentDto);
                board.addAttachment(attachmentEntity);
                attachmentRepository.insert(attachmentEntity);
            }
        }
        return BoardDto.from(board);
    }

    @Transactional(readOnly = true)
    public BoardDto detail(Long no) {
        BoardEntity entity = boardRepository.findBoardByNo(no);
        if (entity == null || !"N".equals(entity.getDelYn()))
            throw new NotFoundException("게시글 없음: " + no);
        entity.getAttachments().size(); // LAZY 초기화
        return BoardDto.from(entity);
    }

    @Transactional(readOnly = true)
    public List<BoardDto> list() {
        return boardRepository.findBoardAll().stream()
                .map(BoardDto::from)
                .toList();
    }

    @Transactional
    public BoardDto modify(Long no, BoardDto dto) {
        BoardEntity entity = boardRepository.findBoardByNo(no);
        if (entity == null || !"N".equals(entity.getDelYn()))
            throw new NotFoundException("게시글 없음: " + no);

        if (dto.getTitle() == null || dto.getTitle().isBlank())
            throw new BadRequestException("title 필수");
        if (dto.getContent() == null || dto.getContent().isBlank())
            throw new BadRequestException("content 필수");

        entity.update(dto);
        return BoardDto.from(entity);
    }

    @Transactional
    public void remove(Long no) {
        BoardEntity entity = boardRepository.findBoardByNo(no);
        if (entity == null || !"N".equals(entity.getDelYn()))
            throw new NotFoundException("게시글 없음: " + no);
        entity.delete();
    }

    @Transactional
    public BoardDto addAttachment(Long boardNo, AttachmentDto dto) {
        BoardEntity entity = boardRepository.findBoardByNo(boardNo);
        if (entity == null || !"N".equals(entity.getDelYn()))
            throw new NotFoundException("게시글 없음: " + boardNo);

        long activeCount = entity.getAttachments().stream().filter(a -> "N".equals(a.getDelYn())).count();
        if (activeCount >= MAX_ATTACH)
            throw new BadRequestException("첨부는 최대 " + MAX_ATTACH + "개");

        if (dto.getOriginalName() == null || dto.getStoredPath() == null || dto.getSizeBytes() == null)
            throw new BadRequestException("첨부 필드 누락");

        AttachmentEntity attachmentEntity = AttachmentEntity.from(dto);
        entity.addAttachment(attachmentEntity);
        attachmentRepository.insert(attachmentEntity);
        return BoardDto.from(entity);
    }

    @Transactional
    public void removeAttachment(Long attachmentNo) {
        AttachmentEntity attachmentEntity = attachmentRepository.findByNo(attachmentNo);
        if (attachmentEntity == null || !"N".equals(attachmentEntity.getDelYn()))
            throw new NotFoundException("첨부 없음: " + attachmentNo);
        attachmentEntity.delete();
    }

}
