package com.example.restshops.service.impl;

import com.example.restshops.repository.models.Shop;
import com.example.restshops.repository.ShopRepo;
import com.example.restshops.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ShopServiceImpl implements ShopService {
    @Autowired
    private ShopRepo shopRepo;
    @Override
    public Iterable<Shop> getAll() {
        return shopRepo.findAll();
    }

    @Override
    public ResponseEntity<?> getById(Long id) {
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(shopRepo.findById(id),HttpStatus.FOUND);
        }
    }

    @Override
    public Optional<Shop> getByIdObj(Long id) {
        if (id != null) {
            return shopRepo.findById(id);
        } else return Optional.empty();
    }

    @Override
    public ResponseEntity<?> save(Shop shop) {
        if (shop == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(shopRepo.save(shop),HttpStatus.CREATED);
        }
    }

    @Override
    public ResponseEntity<?> update(Shop shop) {
        Optional<Shop> shopOptional = shopRepo.findById(shop.getId());
        if (shopOptional.isPresent()) {
            Shop foundedShop = shopOptional.get();
            foundedShop.setCity(shop.getCity());
            foundedShop.setStreet(shop.getStreet());
            foundedShop.setTitle(shop.getTitle());
            foundedShop.setNumberOfWorkers(shop.getNumberOfWorkers());
            foundedShop.setSite(shop.isSite());
            return new ResponseEntity<>(shopRepo.save(foundedShop),HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<?> deleteShop(Long id) {
        if (shopRepo.findById(id).isPresent()) {
            shopRepo.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
