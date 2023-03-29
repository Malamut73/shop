package com.online.shop.controllers;

import com.online.shop.dao.ProductDAO;
import com.online.shop.dto.ProductDTO;
import com.online.shop.service.ProductService;
import com.online.shop.service.implementation.SessionObjectHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final SessionObjectHolder sessionObjectHolder;



    @GetMapping
    public String list(Model model){
        sessionObjectHolder.addClick();
        List<ProductDTO> list = productService.getAll();
        model.addAttribute("products", list);
        return "products";
    }

    @GetMapping("/{id}/bucket")
    public String addBucket(@PathVariable Long id,
                            Principal principal){
        sessionObjectHolder.addClick();
        if(principal == null){
            return "redirect:/products";
        }
        productService.addToUserBucket(id, principal.getName());
        return "redirect:/products";
    }

    @PostMapping
    public ResponseEntity<Void> addProduct(ProductDTO productDTO){
        System.out.println("ResponseEntity work");
        productService.addProduct(productDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

//    @PostMapping
//    public String test(ProductDTO productDTO){
//        System.out.println("standart method is working");
//        productService.addProduct(productDTO);
//        return "redirect:/products";
//    }

    @MessageMapping("/products")
    public void messageAddProduct(ProductDTO productDTO){
        System.out.println("controller've worked");
        productService.addProduct(productDTO);
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ProductDTO getById(@PathVariable Long id){

        return productService.getById(id);
    }

}
