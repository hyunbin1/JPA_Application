package jpabook.jpashop.forms;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BookForm {

    private Long bookId;
    private String name;
    private int bookPrice;
    private int stockQuantity;

    private String author;
    private String isbn;

}
