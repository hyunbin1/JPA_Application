package jpabook.jpashop.repository.order.query;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    public List<OrderQueryDto> findAllByDto_optimization() {
        List<OrderQueryDto> result = findOrders();
        Map<Long, List<OrderItemQueryDto>> orderItemMap = findOrderItemMap(toOrderIds(result));

        // 이전에는 루프를 돌릴때마다 쿼리를 계속 날렸는데, 지금 이 방법은 메모리에 값을 다 가져온다음에 세팅 해준다음에 쿼리가 나가게[ 된다.
        // 이렇게 최적화를 해주면 쿼리가 두번만 나가게 되서 N+1 문제를 해결할 수 있다.
        result.forEach(o->o.setOrderItems(orderItemMap.get(o.getOrderId())));

        return result;


    }
// 이 방법은 쿼리를 한번에 불러낼 수 있다. 하지만 이것은 페이징을 할 수 없다는 한계가 있었다.
    public List<OrderFlatDto> findAllByDto_flat() {
        return entityManager.createQuery(
                "select new jpabook.jpashop.repository.order.query.OrderFlatDto(o.orderId, m.username, o.orderDateTime, o.orderStatus, d.address, i.itemName, oi.orderPrice, oi.count)" +
                        " from Orders o" +
                        " join o.member m" +
                        " join o.delivery d" +
                        " join o.orderItems oi" +
                        " join oi.item i", OrderFlatDto.class).getResultList();
    }

    // 서브 메서드 //
    private Map<Long, List<OrderItemQueryDto>> findOrderItemMap(List<Long> orderIds) {
        List<OrderItemQueryDto> orderItems = entityManager.createQuery(
                        " select new jpabook.jpashop.repository.order.query.OrderItemQueryDto(oi.order.id, i.itemName, oi.orderPrice, oi.count) " +
                                "from OrderItem oi " +
                                "join oi.item i " +
                                "where oi.order.id in :orderIds", OrderItemQueryDto.class)
                .setParameter("orderIds", orderIds)
                .getResultList();

        // 람다로 orderitems를 맵으로 최적화 하기
        Map<Long, List<OrderItemQueryDto>> orderItemMap = orderItems.stream()
                .collect(Collectors.groupingBy(orderItemQueryDto -> orderItemQueryDto.getOrderId()));
        return orderItemMap;
    }

    private static List<Long> toOrderIds(List<OrderQueryDto> result) {
        List<Long> orderIds = result.stream()
                .map(o -> o.getOrderId())
                .collect(Collectors.toList());
        return orderIds;
    }


}
