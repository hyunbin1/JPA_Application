package jpabook.jpashop.api;

import jpabook.jpashop.domain.Orders;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.service.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


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
}
