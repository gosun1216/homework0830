package kh.homework.app01.board.repository;

import kh.homework.app01.board.entity.AttachmentEntity;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class AttachmentRepository {

    private final EntityManager em;

    public void insert(AttachmentEntity e) {
        em.persist(e);
    }

    public AttachmentEntity findByNo(Long no) {
        return em.find(AttachmentEntity.class, no);
    }

    public List<AttachmentEntity> findByBoardNo(Long boardNo) {
        String jpql = """
            select a
            from AttachmentEntity a
            where a.board.no = :boardNo
              and a.delYn = 'N'
            order by a.no asc
        """;
        return em.createQuery(jpql, AttachmentEntity.class)
                .setParameter("boardNo", boardNo)
                .getResultList();
    }
}
