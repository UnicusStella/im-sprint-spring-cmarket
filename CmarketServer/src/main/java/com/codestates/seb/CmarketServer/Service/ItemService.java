package com.codestates.seb.CmarketServer.Service;

import com.codestates.seb.CmarketServer.Entity.Items;
import com.codestates.seb.CmarketServer.Entity.Users;
import com.codestates.seb.CmarketServer.Repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public List<Items> FindItem(){
        return itemRepository.FindItemList();
    }
}
