package com.online.shop.controllers;

import com.online.shop.dto.ProductDTO;
import com.online.shop.service.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductController2IT {

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private ProductService productService;

    private ProductDTO expectedProduct = new ProductDTO(99L, "Test Product", 999.99);

    @BeforeEach
    void setUp() {
        given(productService.getById(expectedProduct.getId()))
                .willReturn(expectedProduct);
    }

    @Test
    void getById() {
        //execute
        ResponseEntity<ProductDTO> entity =
                restTemplate
                        .getForEntity("/products/" + expectedProduct.getId(), ProductDTO.class);
        //check
        Assertions.assertEquals(HttpStatus.OK, entity.getStatusCode());

        ProductDTO actualProduct = entity.getBody();
        Assertions.assertEquals(expectedProduct, actualProduct);
    }

    @Test
    void addProduct() {
        //execute
        ResponseEntity<Void> response =
                restTemplate.postForEntity("/api/v1/products/", expectedProduct, Void.class);
        //check
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        verify(productService).addProduct(Mockito.eq(expectedProduct));

        ArgumentCaptor<ProductDTO> captor = ArgumentCaptor.forClass(ProductDTO.class);
        verify(productService).addProduct(captor.capture());

        Assertions.assertEquals(expectedProduct, captor.getValue());
    }
}
