package com.example.restshops.service;

import com.example.restshops.repository.models.Shop;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface ShopService {
    Iterable<Shop> getAll();
    Optional<Shop> getById(Long id);
    Shop save(Shop shop);
    Optional<Shop> update(Shop shop);
    void deleteShop(Long id);

}
