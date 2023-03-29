package com.online.shop.controllers;

import com.online.shop.dto.ProductDTO;
import com.online.shop.service.ProductService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
public class ProductRestController {

    private final ProductService productService;


    public ProductRestController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public ProductDTO getById(@PathVariable Long id){

        System.out.println("restcontroller get method");
        return productService.getById(id);
    }

    @PostMapping
    public void addProduct(@RequestBody ProductDTO dto){
        System.out.println("restcontroller get method");
        productService.addProduct(dto);
    }

}
