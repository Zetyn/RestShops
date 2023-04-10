package com.example.restshops.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ShopDTO {
    private long id;
    private String title;
    private String city;
    private String street;
    private boolean site;
}
