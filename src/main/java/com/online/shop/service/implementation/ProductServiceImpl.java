package com.online.shop.service.implementation;

import com.online.shop.dao.ProductDAO;
import com.online.shop.domain.Bucket;
import com.online.shop.domain.Product;
import com.online.shop.domain.User;
import com.online.shop.dto.ProductDTO;
import com.online.shop.mapper.ProductMapper;
import com.online.shop.service.BucketService;
import com.online.shop.service.ProductService;
import com.online.shop.service.UserService;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductMapper mapper;
    private final ProductDAO productDAO;
    private final BucketService bucketService;
    private final UserService userService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    public ProductServiceImpl(ProductMapper mapper, ProductDAO productDAO, BucketService bucketService,
                              UserService userService, SimpMessagingTemplate simpMessagingTemplate) {
        this.mapper = mapper;
        this.productDAO = productDAO;
        this.bucketService = bucketService;
        this.userService = userService;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    public List<ProductDTO> getAll() {
        return mapper.fromProductList(productDAO.findAll());
    }

    @Transactional
    public void addToUserBucket(Long productId, String username) {
        User user = userService.findByName(username);
        if(user == null){
            throw new RuntimeException("User not found - " + username);
        }

        Bucket bucket = user.getBucket();
        if(bucket == null){
            Bucket newBucket = bucketService.createBucket(user, Collections.singletonList(productId));
            user.setBucket(newBucket);
            userService.save(user);
        }else{
            bucketService.addProducts(bucket, Collections.singletonList(productId));
        }
    }

    @Override
    @Transactional
    public void addProduct(ProductDTO productDTO) {
        Product product = mapper.toProduct(productDTO);
        Product savedProduct = productDAO.save(product);
        simpMessagingTemplate.convertAndSend("/topic/products", ProductMapper.MAPPER.fromProduct(savedProduct));
    }




}
