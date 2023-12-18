package jpabook.jpashop.service;


import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Items;
import jpabook.jpashop.repository.ItemsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService {
    private final ItemsRepository itemsRepository;

    @Transactional
    public void saveItem(Items item) {
        itemsRepository.save(item);
    }

    public Items findItem(Long itemId) {
        return itemsRepository.findOne(itemId);
    }

    public List<Items> findAllItem() {
        return itemsRepository.findItemsList();
    }

    @Transactional
    public void updateItem(Long itemId, String bookName, int price, int stockQuantity) {
        // 파라미터가 많으면 dto 만들어서 사용하면 편하다.
        Items findItem = itemsRepository.findOne(itemId);
        // findItem.change(price, name, stockQuantity); 와같이 의미있는 메서드를 사용할 것. change 메서드는 엔티티에서 작성
        findItem.setItemName(bookName);
        findItem.setPrice(price);
        findItem.setStockQuantity(stockQuantity);
    }

}
