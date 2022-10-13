package com.online.shop.controllers;

import com.online.shop.dto.ProductDTO;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductControllerIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void checkGetProductById() {
        ResponseEntity<ProductDTO> entity = restTemplate.getForEntity("/products/" + 1, ProductDTO.class);
        Assertions.assertEquals(HttpStatus.OK, entity.getStatusCode());
    }
}
