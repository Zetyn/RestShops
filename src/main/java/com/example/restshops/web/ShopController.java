package com.example.restshops.web;

import com.example.restshops.dto.ShopDTO;
import com.example.restshops.repository.models.Shop;
import com.example.restshops.service.ShopService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public ResponseEntity<?> getShopById(@PathVariable("id") Long id) {
        if (id != null) {
            return new ResponseEntity<>(shopService.getById(id),HttpStatus.FOUND);
        } else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/shops/objMapper/{id}")
    public void getShopByIdWithObjMapp(@PathVariable("id") long id, HttpServletResponse response) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Optional<Shop> OptionalShop = shopService.getById(id);
        OptionalShop.ifPresentOrElse(shop -> {
            ShopDTO shopDTO = ShopDTO.builder().title(shop.getTitle()).city(shop.getCity()).street(shop.getStreet()).site(shop.isSite()).id(shop.getId()).build();
            try {
                String shopDTOJson = objectMapper.writeValueAsString(shopDTO);
                response.getWriter().write(shopDTOJson);
                response.setStatus(HttpStatus.FOUND.value());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }, () -> {
            response.setStatus(HttpStatus.NOT_FOUND.value());
        });
    }

    //--------------Post----------------------------------

    @PostMapping("/createShop/objMapper")
    public void createShopWithObjMapp(HttpServletRequest request, HttpServletResponse response) throws IOException {
        BufferedReader reader = request.getReader();
        String shopJson = reader.lines().collect(Collectors.joining());
        ObjectMapper objectMapper = new ObjectMapper();
        Shop shop = objectMapper.readValue(shopJson, Shop.class);
        shopService.save(shop);

        ShopDTO shopDTO = ShopDTO.builder().title(shop.getTitle()).city(shop.getCity()).street(shop.getStreet()).site(shop.isSite()).id(shop.getId()).build();
        String shopDTOJson = objectMapper.writeValueAsString(shopDTO);

        response.getWriter().write(shopDTOJson);
        response.setStatus(HttpStatus.CREATED.value());
    }

    @PostMapping("/createShop")
    public ResponseEntity<?> createShop(@RequestBody Shop shop) {
        if (shop != null) {
            return new ResponseEntity<>(shopService.save(shop),HttpStatus.CREATED);
        } else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    //--------------Put----------------------------------

    @PutMapping("/updateShop")
    public ResponseEntity<?> updateShop(@RequestBody Shop shop) {
        if (shop != null) {
            return new ResponseEntity<>(shopService.update(shop),HttpStatus.OK);
        } else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    //--------------Delete----------------------------------

    @DeleteMapping("/deleteShop/{id}")
    public ResponseEntity<?> deleteShop(@PathVariable("id") Long id) {
        if (id != null) {
            shopService.deleteShop(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
