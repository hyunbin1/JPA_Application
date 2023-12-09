package jpabook.jpashop.domain.item;

import jakarta.persistence.*;
import jpabook.jpashop.domain.Category;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
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

    // Order을 가져와서 사용하지 않기 때문에 orderItem에서 내용을 가져오지 않는다.

}

