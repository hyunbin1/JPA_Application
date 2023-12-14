package jpabook.jpashop.service;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.Orders;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Items;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.OrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired EntityManager entityManager;
    @Autowired OrderService orderService;
    @Autowired OrderRepository orderRepository;

    @Test
    public void 상품주문() throws Exception{
        // given
        Member member = createMember();
        Items book = createBook("새책", 10000, 10);

        // when
        int orderCount = 2;
        Long orderId = orderService.order((member.getId()), book.getItemId(), orderCount);

        // then
        Orders getOrder = orderRepository.findOneOrder(orderId);

        assertEquals(OrderStatus.ORDER, getOrder.getOrderStatus(), "상품 주문시 상태는 ORDER");
        assertEquals(1, getOrder.getOrderItems().size(), "주문한 상품 종류 수가 정확해야한다.");
        assertEquals(10000*orderCount, getOrder.getTotalPrice(), "주문한 가격은 가격 * 수량이다.");
        assertEquals(8, book.getStockQuantity(), "주문 수량만큼 재고가 줄어야한다.");


    }


    @Test
    public void 상품주문_재고수량초과() throws Exception {
        // given
        Member member = createMember();
        Items book = createBook("새책", 10000, 10);

        int orderCount = 11;

        // then
        assertThrows(NotEnoughStockException.class, () -> {
            orderService.order(member.getId(), book.getItemId(), orderCount);// 예외 발생
        }, "재고 수량 예외가 발생해야 한다.");
    }


    @Test
    public void 주문취소() throws Exception{
        // given
        Member member = createMember();
        Items book = createBook("새책", 10000, 10);


        int orderCount = 2;
        Long orderId = orderService.order((member.getId()), book.getItemId(), orderCount);

        // when
        orderService.cancelOrder(orderId);

        // then
         Orders getOrder = orderRepository.findOneOrder(orderId);
         assertEquals(OrderStatus.CANCEL, getOrder.getOrderStatus(), "주문 취소 상태는 cancle이다.");
         assertEquals(10, book.getStockQuantity(), "주문 취소시 재고 증가.");

    }


    private Items createBook(String name, int price, int count) {
        Items book = new Book();
        book.setItemName(name);
        book.setPrice(price);
        book.setStockQuantity(count);
        entityManager.persist(book);
        return book;
    }

    private Member createMember() {
        Member member = new Member();
        member.setUsername("회원1");
        member.setAddress(new Address("서울", "강남", "07039"));
        entityManager.persist(member);
        return member;
    }
}
