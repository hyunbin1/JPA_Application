package jpabook.jpashop.domain.item;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QItems is a Querydsl query type for Items
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QItems extends EntityPathBase<Items> {

    private static final long serialVersionUID = 528736488L;

    public static final QItems items = new QItems("items");

    public final ListPath<jpabook.jpashop.domain.Category, jpabook.jpashop.domain.QCategory> categoryList = this.<jpabook.jpashop.domain.Category, jpabook.jpashop.domain.QCategory>createList("categoryList", jpabook.jpashop.domain.Category.class, jpabook.jpashop.domain.QCategory.class, PathInits.DIRECT2);

    public final NumberPath<Long> itemId = createNumber("itemId", Long.class);

    public final StringPath itemName = createString("itemName");

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public final NumberPath<Integer> stockQuantity = createNumber("stockQuantity", Integer.class);

    public QItems(String variable) {
        super(Items.class, forVariable(variable));
    }

    public QItems(Path<? extends Items> path) {
        super(path.getType(), path.getMetadata());
    }

    public QItems(PathMetadata metadata) {
        super(Items.class, metadata);
    }

}

