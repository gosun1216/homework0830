package kh.homework.app01.board.dto;

import kh.homework.app01.board.entity.AttachmentEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AttachmentDto {

    private Long no;
    private String originalName;
    private String storedPath;
    private Long sizeBytes;

    public static AttachmentDto from(AttachmentEntity e) {
        AttachmentDto dto = new AttachmentDto();
        dto.setNo(e.getNo());
        dto.setOriginalName(e.getOriginalName());
        dto.setStoredPath(e.getStoredPath());
        dto.setSizeBytes(e.getSizeBytes());
        return dto;
    }
}
