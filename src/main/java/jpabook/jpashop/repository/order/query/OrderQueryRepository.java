package jpabook.jpashop.repository.order.query;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderQueryRepository {
    private final EntityManager entityManager;

    public List<OrderQueryDto> findOrderQueryDtos() {
        List<OrderQueryDto> result = findOrders();
        result.forEach(o->{
            List<OrderItemQueryDto> orderItems = findOrderItems(o.getOrderId());
            o.setOrderItems(orderItems);
        });
        return result;
    }

    private List<OrderItemQueryDto> findOrderItems(Long orderId) {
        return entityManager.createQuery(" select new jpabook.jpashop.repository.order.query.OrderItemQueryDto(oi.order.id, i.itemName, oi.orderPrice, oi.count) " +
                "from OrderItem oi " +
                "join oi.item i " +
                "where oi.order.id = :orderId", OrderItemQueryDto.class)
                .setParameter("orderId", orderId)
                .getResultList();

    }

    public List<OrderQueryDto> findOrders() {
        return entityManager.createQuery(
                "select new jpabook.jpashop.repository.order.query.OrderQueryDto(order.id, member.username, order.orderDateTime, order.orderStatus, delivery.address)" +
                        " from Orders order" +
                        " join order.member member" +
                        " join order.delivery delivery", OrderQueryDto.class).getResultList();
    }

}
