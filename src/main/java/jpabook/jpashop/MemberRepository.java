package jpabook.jpashop;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

//    @PersistenceContext // Entitymanager은 원래 이 어노테이션을 써야만 했다.
//    하지만 스프링 Boot를 사용하면 JPA를 지원해주어서 persistenceContext 대신 Autowired를 사용할 수 있다.
//    @Autowired // 이걸 사용할 수 있기 때문에 그냥 @RequriedArgsConstructor을 사용하면 된다.
    private final EntityManager entityManager;

    public Long save(Member member) {
        entityManager.persist(member);
        return member.getId(); // 커멘드와 쿼리를 분리하기 위해 return 값은 member로 받지 않고 id 정도만 받는다.
    }

    public Member find(Long id) {
        return entityManager.find(Member.class, id);
    }

}
