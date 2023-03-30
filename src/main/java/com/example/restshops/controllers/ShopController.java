package com.example.restshops.controllers;

import com.example.restshops.models.Shop;
import com.example.restshops.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ShopController {
    @Autowired
    private ShopService shopService;

    //--------------Get----------------------------------

    @GetMapping("/shops")
    public ResponseEntity<?> getShops() {
        return new ResponseEntity<>(shopService.getAll(), HttpStatus.FOUND);
    }

    @GetMapping("/shops/{id}")
    public ResponseEntity<?> getShopById(@PathVariable("id") long id) {
        if(shopService.getById(id).isPresent())
            return new ResponseEntity<>(shopService.getById(id), HttpStatus.FOUND);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //--------------Post----------------------------------

    @PostMapping("/createShop")
    public ResponseEntity<?> createShop(@RequestBody Shop shop) {
        if (shop == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        else
            return new ResponseEntity<>(shopService.save(shop),HttpStatus.CREATED);
    }

    //--------------Put----------------------------------

    @PutMapping("/updateShop")
    public ResponseEntity<?> updateShop(@RequestBody Shop shop) {
        if (shop == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        else
            return new ResponseEntity<>(shopService.update(shop),HttpStatus.OK);
    }

    //--------------Delete----------------------------------

    @DeleteMapping("/deleteShop/{id}")
    public ResponseEntity<?> deleteShop(@PathVariable("id") long id) {
        if (shopService.getById(id).isPresent()){
            shopService.deleteShop(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
