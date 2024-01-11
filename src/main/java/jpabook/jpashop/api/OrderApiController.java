package jpabook.jpashop.api;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.Orders;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.order.query.OrderFlatDto;
import jpabook.jpashop.repository.order.query.OrderQueryDto;
import jpabook.jpashop.repository.order.query.OrderQueryRepository;
import jpabook.jpashop.service.OrderSearch;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OrderApiController {
    private final OrderRepository orderRepository;
    private final OrderQueryRepository orderQueryRepository;

    @GetMapping("/api/v1/orders")
    public List<Orders> ordersV1() {
    List<Orders> all = orderRepository.findAllOrders(new OrderSearch());
        for (Orders orders : all) {
            orders.getMember().getUsername();
            orders.getDelivery().getAddress();
            List<OrderItem> orderItems = orders.getOrderItems();
            orderItems.stream().forEach(o -> o.getItem().getItemName());
        }
        return all;
    }

    @GetMapping("/api/v2/orders")
    public List<OrderDto> ordersV2() {
        List<Orders> orders = orderRepository.findAllOrders(new OrderSearch());
        List<OrderDto> collect = orders.stream()
                .map(o->new OrderDto(o))
                .collect(Collectors.toList());

        return collect;
    }

    @GetMapping("/api/v3/orders")
    public List<OrderDto> ordersV3() {
        List<Orders> orders = orderRepository.findWithItem();

        for(Orders order : orders) {
            System.out.println("order ref=" + order + " id=" + order.getOrderId());
        }

        List<OrderDto> result = orders.stream()
                .map(o-> new OrderDto(o))
                .collect(Collectors.toList());

        return result;
    }

    @GetMapping("/api/v3.1/orders")
    public List<OrderDto> ordersV3_page(@RequestParam(value = "offset", defaultValue = "0") int offset,
                                        @RequestParam(value = "limit", defaultValue = "0") int limit
                                        ) {
        List<Orders> orders = orderRepository.findAllWithMemberDelivery(offset, limit);

        List <OrderDto> result = orders.stream()
                .map(o-> new OrderDto(o))
                .collect(Collectors.toList());
        return result;
    }

    @GetMapping("/api/v4/orders")
    public List<OrderQueryDto> ordersV4() {
        return orderQueryRepository.findOrderQueryDtos();
    }


    @GetMapping("/api/v5/orders")
    public List<OrderQueryDto> ordersV5() {
        return orderQueryRepository.findAllByDto_optimization();
    }

    @GetMapping("/api/v6/orders")
    public List<OrderFlatDto> ordersV6() {
        return orderQueryRepository.findAllByDto_flat();
    }


    @Data // no properties 오류는 보통 게터 세터가 없어서 그렇다.
    static class OrderDto {
        private Long orderId;
        private String name;
        private LocalDateTime localDateTime;
        private OrderStatus orderStatus;
        private Address address;
        private List<OrderItemDto> orderItems;

        public OrderDto(Orders order) {
            orderId = order.getOrderId();
            name = order.getMember().getUsername();
            localDateTime = order.getOrderDateTime();
            orderStatus = order.getOrderStatus();
            address = order.getMember().getAddress();
//            // 버전 1: orderItem은 엔티티기 때문에 프록시 초기화를 해주어야한다.
//            order.getOrderItems().stream().forEach(o->o.getItem().getItemName());
//            orderItems = order.getOrderItems();
            orderItems = order.getOrderItems().stream()
                    .map(orderItem -> new OrderItemDto(orderItem))
                    .collect(Collectors.toList());
        }

        @Getter
        static class OrderItemDto {
            private String itemName; // 상품 명
            private int orderPrice; // 주문 가격
            private int count; // 주문 수량
            public OrderItemDto(OrderItem orderItem) {
                itemName = orderItem.getItem().getItemName();
                orderPrice = orderItem.getOrderPrice();
                count = orderItem.getCount();



            }
        }

    }


}
