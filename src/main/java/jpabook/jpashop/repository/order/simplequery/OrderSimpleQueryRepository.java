package jpabook.jpashop.repository.order.simplequery;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderSimpleQueryRepository {

    private final EntityManager entityManager;
    // api 스팩에 맞춰서 쿼리를 직접 짠 것이다. repository는 엔티티 객체 그래프들을 조회하는데 사용하는 곳이다. 따라서 v3가 좀 더 확장 가능하고,
    // v4 경우에는 논리적으로는 계층이 파괴된 것과 같다는 단점이 있다. api 스팩이 repository에 들어와 았는 것과 같다.
    // 하지만 성능은 v3보다야 좋지만 성능은 미미하다. v3 사용할것.
    // 리파지토리에서 sql 혹은 api 스팩을 직접적으로 사용하지 않아, 계층을 파괴하지 않기 위해서 따로 패키지를 만들어준다.
    public List<OrderQuerySimpleDto> findOrderDtos() {
        return entityManager.createQuery("select new jpabook.jpashop.repository.order.simplequery.OrderQuerySimpleDto(o.id, m.username, o.orderDateTime, o.orderStatus, d.address) " +
                        "from Orders o " +
                        "join o.member m " +
                        "join o.delivery d", OrderQuerySimpleDto.class)
                .getResultList();
    }
}
