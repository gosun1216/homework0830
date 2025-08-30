package kh.homework.app01.member.repository;

import kh.homework.app01.member.entity.MemberEntity;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;

    public MemberEntity findByNo(Long no) {
        return em.find(MemberEntity.class, no);
    }
}
