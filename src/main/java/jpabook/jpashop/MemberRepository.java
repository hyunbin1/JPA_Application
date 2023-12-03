package jpabook.jpashop;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class MemberRepository {


    @PersistenceContext
    private EntityManager entityManager;

    public Long save(Member member) {
        entityManager.persist(member);
        return member.getId(); // 커멘드와 쿼리를 분리하기 위해 return 값은 member로 받지 않고 id 정도만 받는다.
    }

    public Member find(Long id) {
        return entityManager.find(Member.class, id);
    }

}
