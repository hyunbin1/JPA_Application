package jpabook.jpashop.service;


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


}
