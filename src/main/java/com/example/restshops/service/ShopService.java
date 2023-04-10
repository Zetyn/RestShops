package com.example.restshops.service;

import com.example.restshops.repository.models.Shop;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface ShopService {
    Iterable<Shop> getAll();
    ResponseEntity<?> getById(Long id);
    Optional<Shop> getByIdObj(Long id);
    ResponseEntity<?> save(Shop shop);
    ResponseEntity<?> update(Shop shop);
    ResponseEntity<?> deleteShop(Long id);

}
