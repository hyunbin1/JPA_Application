package jpabook.jpashop.domain;


import jakarta.persistence.*;
import jpabook.jpashop.domain.item.Items;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name ="order_item")
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {

    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long orderItemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Items item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Orders order;

    private int orderPrice; // 주문 가격
    private int count; // 주문 수량


    //== 생성 메서드 ==//
    public static OrderItem createOrderitem(Items item, int orderPrice, int count) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);
        item.removeStock(count);

        return orderItem;
    }


    //== 비즈니스 로직 ==//
    /* 주문 취소 */
    public void cancel() {
        getItem().addStock(count);

    }

    //== 조회 로직 ==//
    /* 주문상품 전체 가격 조회 */
    public int getTotalPrice() {
        return getOrderPrice() * getCount();
    }

}
