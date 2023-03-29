package com.online.shop.service;

import com.online.shop.dto.ProductDTO;

import java.util.List;

public interface ProductService {
    List<ProductDTO> getAll();
    void addToUserBucket(Long productId, String username);
    void addProduct(ProductDTO productDTO);
    ProductDTO getById(Long id);

}
