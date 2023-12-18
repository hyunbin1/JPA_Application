package jpabook.jpashop.controller;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Items;
import jpabook.jpashop.forms.BookForm;
import jpabook.jpashop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/items/new")
    public String readForm(Model model) {
        model.addAttribute("bookForm", new BookForm());
        return "items/createItemForm";
    }

    @PostMapping("/items/new")
    public String createForm(BookForm bookForm){
        Book book = new Book();
        book.setItemName(bookForm.getName());
        book.setPrice(bookForm.getBookPrice());
        book.setStockQuantity(bookForm.getStockQuantity());
        book.setAuthor(bookForm.getAuthor());
        book.setIsbn(bookForm.getIsbn());

        itemService.saveItem(book);
        return "redirect:/items";
    }

    @GetMapping("/items")
    public String list(Model model){
        List<Items> items = itemService.findAllItem();
        model.addAttribute("items", items);
        return "items/itemList";
    }

    // 상품 수정
    @GetMapping(value = "/items/{itemId}/edit")
    public String updateItemForm(@PathVariable("itemId") Long itemId, Model
            model) {
        Book item = (Book) itemService.findItem(itemId);
        BookForm form = new BookForm();
        form.setBookId(item.getItemId());
        form.setName(item.getItemName());
        form.setBookPrice(item.getPrice());
        form.setStockQuantity(item.getStockQuantity());
        form.setAuthor(item.getAuthor());
        form.setIsbn(item.getIsbn());
        model.addAttribute("form", form);
        return "items/updatedItemForm";
    }
    /**
     * 상품 수정
     */
    @PostMapping(value = "/items/{itemId}/edit")
    public String updateItem(@PathVariable Long itemId, @ModelAttribute("form") BookForm form ) {

//        어설프게 controller에서 entity를 만들지 말자.
//        Book book = new Book();
//        book.setItemId(form.getBookId());
//        book.setItemName(form.getName());
//        book.setPrice(form.getBookPrice());
//        book.setStockQuantity(form.getStockQuantity());
//        book.setAuthor(form.getAuthor());
//        book.setIsbn(form.getIsbn());
//        itemService.saveItem(book);

        itemService.updateItem(itemId, form.getName(), form.getBookPrice(), form.getStockQuantity());

        return "redirect:/items";
    }
}
