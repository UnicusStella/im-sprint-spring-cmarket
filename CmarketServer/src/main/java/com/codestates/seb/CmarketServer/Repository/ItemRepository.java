package com.codestates.seb.CmarketServer.Repository;

import com.codestates.seb.CmarketServer.Entity.Items;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ItemRepository {

    private final EntityManager entityManager;

    @Autowired
    public ItemRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public List<Items> FindItemList() {
        // Item 전체를 리턴합니다
        // TODO :

        List<Items> list = entityManager
                .createQuery("SELECT item FROM Items as item ", Items.class)
                .getResultList();
        entityManager.close();
        return list;
    }

}
