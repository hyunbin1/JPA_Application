package jpabook.jpashop.api;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.Orders;
import jpabook.jpashop.repository.order.simplequery.OrderQuerySimpleDto;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.order.simplequery.OrderSimpleQueryRepository;
import jpabook.jpashop.service.OrderSearch;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


/*
* xToMany 성능 최적화 연구(ManyToOne, OneToOne)
* Order
* Order -> Member
* Order -> Delivery
*/
@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {
    private final OrderRepository orderRepository;
    private final OrderSimpleQueryRepository orderSimpleQueryRepository;


    // postman aborted 오류는 무한루프로 인한 것이다. 양방향 연관관계에서는 dto를 만들어서 한 쪽은 필수적으로 jsonIgnore 해주어야한다.
    // lazy상태의 객체들로 인해 proxy 문제가 생긴다면, hibernate5 모듈을 설치 후 사용하지 않게 만들어주어야한다.
        // build.gradle, application.java에도 빈 등록해주기 강제로 레이지 포스하는 건 성능에 좋지 않다.
    // 근본적인 문제를 해결하기 위해서는 불필요한 엔티티를 노출시키지 않는 dto를 만들면 해결이 된다.

    @GetMapping("/api/v1/simple-orders")
    public List<Orders> ordersV1() {
        List<Orders> all = orderRepository.findAllOrders(new OrderSearch());
        for(Orders order : all) {
            order.getMember().getUsername(); // Lazy 강제 초기화
            order.getDelivery().getAddress(); // Lazy 강제 초기화
        }

        return all;
    }


    @GetMapping("/api/v2/simple-orders")
    public List<SimpleOrderDto> ordersV2() {
        // N+1문제 발생함.
        List<SimpleOrderDto> result = orderRepository.findAllOrders((new OrderSearch())).stream().map(SimpleOrderDto::new).toList();

        return result;
    }


    @GetMapping("/api/v3/simple-orders")
    public List<SimpleOrderDto> ordersV3() {
        List<Orders> orders = orderRepository.findAllWithMemberDelivery();
        List<SimpleOrderDto> result = orders.stream()
                .map(o -> new SimpleOrderDto(o))
                .collect(Collectors.toList());
        return result;
    }

    // api 스팩에 맞춰서 쿼리를 직접 짠 것이다. repository는 엔티티 객체 그래프들을 조회하는데 사용하는 곳이다. 따라서 v3가 좀 더 확장 가능하고,
    // v4 경우에는 논리적으로는 계층이 파괴된 것과 같다는 단점이 있다. api 스팩이 repository에 들어와 았는 것과 같다.
    // 하지만 성능은 v3보다야 좋지만 성능은 미미하다. v3 사용할것.
    // 리파지토리에서 sql 혹은 api 스팩을 직접적으로 사용하지 않아, 계층을 파괴하지 않기 위해서 따로 패키지를 만들어준다.
    @GetMapping("/api/v4/simple-orders")
    public List<OrderQuerySimpleDto> ordersV4() {
        return orderSimpleQueryRepository.findOrderDtos();
    }




    @Data
    static class SimpleOrderDto {
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;

        public SimpleOrderDto(Orders orders) {
            orderId = orders.getOrderId();
            name = orders.getMember().getUsername();
            orderDate = orders.getOrderDateTime();
            orderStatus = orders.getOrderStatus();
            address = orders.getDelivery().getAddress();
        }
    }


}
