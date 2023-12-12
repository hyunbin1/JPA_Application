package jpabook.jpashop.domain.item;

import jakarta.persistence.*;
import jpabook.jpashop.domain.Category;
import jpabook.jpashop.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@DiscriminatorColumn(name = "dtype")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "items")
public abstract class Items {

    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long itemId;

    private String itemName;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categoryList = new ArrayList<Category>();

    //== 비즈니스 로직 ==//
    /* 재고 증가 */
    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    /* 재고 감소 */
    public void removeStock(int quantity) {
        int restStock = this.stockQuantity - quantity;
        if( restStock < 0 ) throw new NotEnoughStockException("need more stock");
        this.stockQuantity = restStock;
    }



}

