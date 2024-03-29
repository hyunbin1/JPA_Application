package jpabook.jpashop.domain.item;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QBook is a Querydsl query type for Book
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBook extends EntityPathBase<Book> {

    private static final long serialVersionUID = -1091535679L;

    public static final QBook book = new QBook("book");

    public final QItems _super = new QItems(this);

    public final StringPath author = createString("author");

    //inherited
    public final ListPath<jpabook.jpashop.domain.Category, jpabook.jpashop.domain.QCategory> categoryList = _super.categoryList;

    public final StringPath isbn = createString("isbn");

    //inherited
    public final NumberPath<Long> itemId = _super.itemId;

    //inherited
    public final StringPath itemName = _super.itemName;

    //inherited
    public final NumberPath<Integer> price = _super.price;

    //inherited
    public final NumberPath<Integer> stockQuantity = _super.stockQuantity;

    public QBook(String variable) {
        super(Book.class, forVariable(variable));
    }

    public QBook(Path<? extends Book> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBook(PathMetadata metadata) {
        super(Book.class, metadata);
    }

}

