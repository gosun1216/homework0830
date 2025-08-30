package kh.homework.app01.board.dto;

import kh.homework.app01.board.entity.BoardEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
public class BoardDto {

    private Long no;
    private String title;
    private String content;
    private Long writerNo;
    private String writerNick;
    private String delYn;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private List<AttachmentDto> attachments;

    public static BoardDto from(BoardEntity entity) {
        BoardDto dto = new BoardDto();
        dto.setNo(entity.getNo());
        dto.setTitle(entity.getTitle());
        dto.setContent(entity.getContent());
        dto.setWriterNo(entity.getWriter().getNo());
        dto.setWriterNick(entity.getWriter().getUserNick());
        dto.setDelYn(entity.getDelYn());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        dto.setAttachments(
                entity.getAttachments().stream()
                        .filter(a -> "N".equals(a.getDelYn()))
                        .map(AttachmentDto::from)
                        .toList()
        );
        return dto;
    }
}
