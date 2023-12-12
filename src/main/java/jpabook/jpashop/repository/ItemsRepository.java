package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.item.Items;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemsRepository {
    private final EntityManager entityManager;

    public void save(Items item) {
        if(item.getItemId() == null) {
            entityManager.persist(item);
        } else {
            entityManager.merge(item);
        }
    }

    public Items findOne(Long itmeId) {
        return entityManager.find(Items.class, itmeId);
    }

    public List<Items> findItemsList() {
        return entityManager.createQuery("select i from Items i", Items.class)
                .getResultList();
    }
}
