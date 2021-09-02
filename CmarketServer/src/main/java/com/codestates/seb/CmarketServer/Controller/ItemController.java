package com.codestates.seb.CmarketServer.Controller;

import com.codestates.seb.CmarketServer.Entity.Items;
import com.codestates.seb.CmarketServer.Service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin("*")
public class ItemController {

    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping(value = "/items")
    public List<Items> GetItemList(){
        return itemService.FindItem();
    }
}
