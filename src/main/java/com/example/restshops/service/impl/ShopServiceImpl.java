package com.example.restshops.service.impl;

import com.example.restshops.repository.models.Shop;
import com.example.restshops.repository.ShopRepo;
import com.example.restshops.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Optional<Shop> getById(Long id) {
        if (id != null) {
            return shopRepo.findById(id);
        } else return Optional.empty();
    }

//    @Override
//    public Optional<Shop> getByIdObj(Long id) {
//        if (id != null) {
//            return shopRepo.findById(id);
//        } else return Optional.empty();
//    }

    @Override
    public Shop save(Shop shop) {
        return shopRepo.save(shop);
    }

    @Override
    public Optional<Shop> update(Shop shop) {
        Optional<Shop> shopOptional = shopRepo.findById(shop.getId());
        if (shopOptional.isPresent()) {
            Shop foundedShop = shopOptional.get();
            foundedShop.setCity(shop.getCity());
            foundedShop.setStreet(shop.getStreet());
            foundedShop.setTitle(shop.getTitle());
            foundedShop.setNumberOfWorkers(shop.getNumberOfWorkers());
            foundedShop.setSite(shop.isSite());
            Shop updatedShop = shopRepo.save(foundedShop);
            return Optional.of(updatedShop);
        }
        return Optional.empty();
    }

    @Override
    public void deleteShop(Long id) {
        if (getById(id).isPresent()) {
            shopRepo.deleteById(id);
        }
    }
}
