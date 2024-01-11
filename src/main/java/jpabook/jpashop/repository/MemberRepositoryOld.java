package jpabook.jpashop.repository;


import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryOld {

    private final EntityManager em;

//    @PersistenceUnit
//    private EntityManagerFactory emf; // 거의 사용할 일 없음

    /* Method: 회원 정보 저장 */
    public void save(Member member) {
        em.persist(member);
    }

    /* Method: 회원 ID를 통해 회원 정보 조회 */
    public Member findOne(Long memberId) {
        return em.find(Member.class, memberId);
    }

    /* Method: 모든 회원 목록 조회 - JPQL을 사용하였다. */
    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    /* Method: 회원 이름을 통해 회원 정보 조회 - JPQL을 사용하였다.*/
    public List<Member> findMemberByUsername(String username) {
        return em.createQuery("select m from Member m where m.username = :username", Member.class)
                .setParameter("username", username)
                .getResultList();
    }

}
