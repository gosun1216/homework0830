package kh.homework.app01.board.entity;

import kh.homework.app01.board.dto.BoardDto;
import kh.homework.app01.member.entity.MemberEntity;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "BOARD")
@Getter
@SequenceGenerator(
        name = "board_seq_gen",
        sequenceName = "SEQ_BOARD",
        allocationSize = 1,
        initialValue = 1
)
public class BoardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "board_seq_gen")
    private Long no;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "text")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writerNo", nullable = false)
    private MemberEntity writer;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<AttachmentEntity> attachments = new ArrayList<>();

    private String delYn;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public BoardEntity() {
        this.delYn = "N";
        this.createdAt = LocalDateTime.now();
    }

    public static BoardEntity from(BoardDto dto, MemberEntity writer) {
        BoardEntity entity = new BoardEntity();
        entity.title = dto.getTitle();
        entity.content = dto.getContent();
        entity.writer = writer;
        return entity;
    }

    public void addAttachment(AttachmentEntity attachment) {
        this.attachments.add(attachment);
        attachment.bindBoard(this);
    }

    public void update(BoardDto dto) {
        this.title = dto.getTitle();
        this.content = dto.getContent();
        this.updatedAt = LocalDateTime.now();
    }

    public void delete() {
        this.delYn = "Y";
        this.updatedAt = LocalDateTime.now();
        for (AttachmentEntity a : attachments) a.delete();
    }
}
