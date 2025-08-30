package kh.homework.app01.board.controller;

import kh.homework.app01.board.dto.AttachmentDto;
import kh.homework.app01.board.dto.BoardDto;
import kh.homework.app01.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/boards")
public class BoardApiController {


    private final BoardService boardService;

    // 게시글 작성 + 첨부 배열
    @PostMapping
    public BoardDto write(@RequestBody BoardDto dto) {
        return boardService.write(dto, dto.getAttachments());
    }

    // 게시글 목록
    @GetMapping
    public List<BoardDto> list() {
        return boardService.list();
    }

    // 게시글 단건 조회
    @GetMapping("/{no}")
    public BoardDto detail(@PathVariable Long no) {
        return boardService.detail(no);
    }

    // 게시글 수정
    @PutMapping("/{no}")
    public BoardDto modify(@PathVariable Long no, @RequestBody BoardDto dto) {
        return boardService.modify(no, dto);
    }

    // 게시글 삭제(소프트)
    @DeleteMapping("/{no}")
    public void remove(@PathVariable Long no) {
        boardService.remove(no);
    }

    // 게시글에 첨부 추가
    @PostMapping("/{no}/attachments")
    public BoardDto addAttachment(@PathVariable Long no, @RequestBody AttachmentDto dto) {
        return boardService.addAttachment(no, dto);
    }

    // 게시글 첨부 삭제(소프트)
    @DeleteMapping("/attachments/{attachmentNo}")
    public void removeAttachment(@PathVariable Long attachmentNo) {
        boardService.removeAttachment(attachmentNo);
    }

}
