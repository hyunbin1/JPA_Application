package jpabook.jpashop.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.*;
import jpabook.jpashop.service.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

import static jpabook.jpashop.domain.QMember.*;
import static jpabook.jpashop.domain.QMember.member;
import static jpabook.jpashop.domain.QOrders.orders;

@Repository
//@RequiredArgsConstructor
//@Transactional(readOnly = true)
public class OrderRepository {
    private final EntityManager entityManager;
    private final JPAQueryFactory query;

    public OrderRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.query = new JPAQueryFactory((entityManager));
    }


    public void saveOrder(Orders order) {
        entityManager.persist(order);
    }

    public Orders findOneOrder(Long orderId) {
        return entityManager.find(Orders.class, orderId);
    }

    public List<Orders> findAllOrders(OrderSearch orderSearch) {
//        JPAQueryFactory query = new JPAQueryFactory(entityManager);
//        QOrders order = QOrders.orders; // static import로 줄여서 사용할 수 있다.
//        QMember member = QMember.member;

        return query
                .select(orders)
                .from(orders)
                .join(orders.member, member)
                .where(statusEq(orderSearch.getOrderStatus()), nameLike(orderSearch.getMemberName()))
                .limit(1000)
                .fetch();
    }

    private BooleanExpression nameLike(String memberName) {
        if(!StringUtils.hasText(memberName)){
            return null;
        }
        return member.username.like(memberName);
    }

    private BooleanExpression statusEq(OrderStatus statusCondition){
        if(statusCondition == null) {
            return null;
        }
        return orders.orderStatus.eq(statusCondition);
    }

//    public List<Orders> findAllOrders(OrderSearch orderSearch) {
//        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
//        CriteriaQuery<Orders> cq = cb.createQuery(Orders.class);
//        Root<Orders> o = cq.from(Orders.class);
//        Join<Orders, Member> m = o.join("member", JoinType.INNER); //회원과 조인
//        List<Predicate> criteria = new ArrayList<>();
//        //주문 상태 검색
//        if (orderSearch.getOrderStatus() != null) {
//            Predicate orderStatus = cb.equal(o.get("orderStatus"),
//                    orderSearch.getOrderStatus());
//            criteria.add(orderStatus);
//        }
//        //회원 이름 검색
//        if (StringUtils.hasText(orderSearch.getMemberName())) {
//            Predicate username =
//                    cb.like(m.<String>get("username"), "%" +
//                            orderSearch.getMemberName() + "%");
//            criteria.add(username);
//        }
//        cq.where(cb.and(criteria.toArray(new Predicate[criteria.size()])));
//        TypedQuery<Orders> query = entityManager.createQuery(cq).setMaxResults(1000); //최대 1000건
//        return query.getResultList();
//    }

    public List<Orders> findAllWithMemberDelivery() {
        return entityManager.createQuery(
                "select o from Orders o" +
                " join fetch o.member m" +
                " join fetch o.delivery d", Orders.class
                ).getResultList();

    }

    // 컬렉션은 페치 조인을 하게되면 db에서 order에 관한건 뻥튀기가 된다.
    // distinct 를 추가해주는 것이 해결 방안이지만, hibernate 6.0 부터는 자동 적용된다.
    // distict는 db는 서로 다른 제품을 샀을 경우에는 그대로 남아있지만, jpa에서는 애플리케이션 컬렉션에 담을 때 order 아이디가 동일하면 중복을 제거해준다.
    // 엔티티가 중복인 경우에 걸러서 컬렉션에 담아주는 역할을 하는 것.
    // 가장 큰 단점은 페이징이 불가능하다는 점에 있다.
    public List<Orders> findWithItem() {
        return entityManager.createQuery(
                "select distinct o from Orders o " +
                        "join fetch o.member m " +
                        "join fetch o.delivery d " +
                        "join fetch o.orderItems oi " +
                        "join fetch oi.item i", Orders.class)
                .getResultList();
    }

    public List<Orders> findAllWithMemberDelivery(int offset, int limit) {
        return entityManager.createQuery(
                "select o from Orders o" +
                        " join fetch o.member m" +
                        " join fetch o.delivery d", Orders.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }
}
