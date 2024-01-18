package jpabook.jpashop.domain.item;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QAlbum is a Querydsl query type for Album
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAlbum extends EntityPathBase<Album> {

    private static final long serialVersionUID = 521107351L;

    public static final QAlbum album = new QAlbum("album");

    public final QItems _super = new QItems(this);

    public final StringPath artist = createString("artist");

    //inherited
    public final ListPath<jpabook.jpashop.domain.Category, jpabook.jpashop.domain.QCategory> categoryList = _super.categoryList;

    public final StringPath etc = createString("etc");

    //inherited
    public final NumberPath<Long> itemId = _super.itemId;

    //inherited
    public final StringPath itemName = _super.itemName;

    //inherited
    public final NumberPath<Integer> price = _super.price;

    //inherited
    public final NumberPath<Integer> stockQuantity = _super.stockQuantity;

    public QAlbum(String variable) {
        super(Album.class, forVariable(variable));
    }

    public QAlbum(Path<? extends Album> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAlbum(PathMetadata metadata) {
        super(Album.class, metadata);
    }

}

