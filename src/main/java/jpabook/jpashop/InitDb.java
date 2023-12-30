package jpabook.jpashop;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.*;
import jpabook.jpashop.domain.item.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class InitDb {
    private final InitService initService;

    @PostConstruct
    public void init() { // application 로딩시점에 이 데이터들을 만들어줌.
        initService.dbInit1();
        initService.dbInit2();
    }



    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {
        private final EntityManager em;
        public void dbInit1() {
            Member member1 = createMember("member1", "서울", "동작구", "07039");
            em.persist(member1);

            Book book1 = createBook("JPA1 Book", 10000, 100);
            em.persist(book1);
            Book book2 = createBook("JPA2 Book", 20000, 100);
            em.persist(book2);

            OrderItem orderitem1 = OrderItem.createOrderitem(book1, 10000, 1);
            OrderItem orderitem2 = OrderItem.createOrderitem(book2, 20000, 1);

            Delivery delivery = createDelivery(member1);
            Orders order = Orders.createOrder(member1, delivery, orderitem1, orderitem2);

            em.persist(order);
        }


        public void dbInit2() {
            Member member2 = createMember("member2", "서울", "동작구", "07039");
            em.persist(member2);

            Book book1 = createBook("Spring1 Book", 20000, 100);
            em.persist(book1);
            Book book2 = createBook("Spring2 Book", 40000, 100);
            em.persist(book2);

            OrderItem orderitem1 = OrderItem.createOrderitem(book1, 20000, 3);
            OrderItem orderitem2 = OrderItem.createOrderitem(book2, 40000, 4);

            Delivery delivery = createDelivery(member2);
            Orders order = Orders.createOrder(member2, delivery, orderitem1, orderitem2);

            em.persist(order);
        }
        private static Delivery createDelivery(Member member) {
            Delivery delivery = new Delivery();
            delivery.setAddress(member.getAddress());
            return delivery;
        }
        private static Book createBook(String bookName, int price, int quantity) {
            Book book = new Book();
            book.setItemName(bookName);
            book.setPrice(price);
            book.setStockQuantity(quantity);
            return book;
        }

        private static Member createMember(String username, String city, String street, String zipcode ) {
            Member member = new Member();
            member.setUsername(username);
            member.setAddress(new Address(city, street, zipcode));
            return member;
        }

    }

}

