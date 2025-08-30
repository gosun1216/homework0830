package kh.homework.app01.board.entity;

import kh.homework.app01.board.dto.AttachmentDto;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Table(name = "ATTACHMENT")
@Getter
@SequenceGenerator(
        name = "attachment_seq_gen",
        sequenceName = "SEQ_ATTACHMENT",
        allocationSize = 1,
        initialValue = 1
)
public class AttachmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "attachment_seq_gen")
    private Long no;

    @Column(nullable = false, length = 255)
    private String originalName;

    @Column(nullable = false, length = 255)
    private String storedPath;

    @Column(nullable = false)
    private Long sizeBytes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "boardNo", nullable = false)
    private BoardEntity board;

    private String delYn;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public AttachmentEntity() {
        this.delYn = "N";
        this.createdAt = LocalDateTime.now();
    }

    public static AttachmentEntity from(AttachmentDto dto) {
        AttachmentEntity e = new AttachmentEntity();
        e.originalName = dto.getOriginalName();
        e.storedPath = dto.getStoredPath();
        e.sizeBytes = dto.getSizeBytes();
        return e;
    }

    public void bindBoard(BoardEntity board) {
        this.board = board;
    }

    public void delete() {
        this.delYn = "Y";
        this.updatedAt = LocalDateTime.now();
    }
}
