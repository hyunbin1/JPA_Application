package jpabook.jpashop.service;

import jpabook.jpashop.domain.Delivery;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.Orders;
import jpabook.jpashop.domain.item.Items;
import jpabook.jpashop.repository.ItemsRepository;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemsRepository itemsRepository;

    /* 상품 주문 */
    @Transactional
    public Long order(Long memberId, Long itemId, int count) {
        //엔티티 조회
        Member orderMember = memberRepository.findOne(memberId);
        Items orderedItem = itemsRepository.findOne(itemId);

        // 배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(orderMember.getAddress());

       // 주문상품 생성
        OrderItem orderItem = OrderItem.createOrderitem(orderedItem, orderedItem.getPrice(), count);

        // 주문 생성
        Orders order = Orders.createOrder(orderMember, delivery, orderItem);

        // 주문 저장
        orderRepository.saveOrder(order);


        return order.getOrderId();

    }

    /* 주문 취소 */
    @Transactional
    public void cancelOrder(Long orderId) {
        Orders order = orderRepository.findOneOrder(orderId);
        order.orderCancel();
    }

    /* 주문 검색 */
    public List<Orders> findOrders(OrderSearch orderSearch) {
        return orderRepository.findAllOrders(orderSearch);
    }



}
