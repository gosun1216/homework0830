package kh.homework.app01.member.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "MEMBER")
@Getter
@SequenceGenerator(
        name = "member_gen_seq",
        sequenceName = "SEQ_MEMBER",
        allocationSize = 1,
        initialValue = 1
)
public class MemberEntity {

    @Id
    @GeneratedValue(generator = "member_gen_seq", strategy = GenerationType.SEQUENCE)
    private Long no;

    @Column(nullable = false, length = 50)
    private String userNick;
}
