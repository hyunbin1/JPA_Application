package jpabook.jpashop.domain.item;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMovie is a Querydsl query type for Movie
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMovie extends EntityPathBase<Movie> {

    private static final long serialVersionUID = 532297816L;

    public static final QMovie movie = new QMovie("movie");

    public final QItems _super = new QItems(this);

    public final StringPath actor = createString("actor");

    //inherited
    public final ListPath<jpabook.jpashop.domain.Category, jpabook.jpashop.domain.QCategory> categoryList = _super.categoryList;

    public final StringPath director = createString("director");

    //inherited
    public final NumberPath<Long> itemId = _super.itemId;

    //inherited
    public final StringPath itemName = _super.itemName;

    //inherited
    public final NumberPath<Integer> price = _super.price;

    //inherited
    public final NumberPath<Integer> stockQuantity = _super.stockQuantity;

    public QMovie(String variable) {
        super(Movie.class, forVariable(variable));
    }

    public QMovie(Path<? extends Movie> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMovie(PathMetadata metadata) {
        super(Movie.class, metadata);
    }

}

