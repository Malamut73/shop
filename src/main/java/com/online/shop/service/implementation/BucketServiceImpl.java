package com.online.shop.service.implementation;

import com.online.shop.dao.BucketDAO;
import com.online.shop.dao.ProductDAO;
import com.online.shop.domain.Bucket;
import com.online.shop.domain.Product;
import com.online.shop.domain.User;
import com.online.shop.dto.BucketDTO;
import com.online.shop.dto.BucketDetailDTO;
import com.online.shop.service.BucketService;
import com.online.shop.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BucketServiceImpl implements BucketService {

    private final BucketDAO bucketDAO;
    private final ProductDAO productDAO;
    private final UserService userService;

    public BucketServiceImpl(BucketDAO bucketDAO, ProductDAO productDAO, UserService userService) {
        this.bucketDAO = bucketDAO;
        this.productDAO = productDAO;
        this.userService = userService;
    }

    @Transactional
    public Bucket createBucket(User user, List<Long> productIds) {
        Bucket bucket = new Bucket();
        bucket.setUser(user);
        List<Product> productList = getCollectRefProductsByIds(productIds);
        bucket.setProducts(productList);
        return bucketDAO.save(bucket);
    }

    public void addProducts(Bucket bucket, List<Long> productIds) {
        List<Product> products = bucket.getProducts();
        List<Product> newProductList = products == null ? new ArrayList<>() : new ArrayList<>(products);
        newProductList.addAll(getCollectRefProductsByIds(productIds));
        bucket.setProducts(newProductList);
        bucketDAO.save(bucket);
    }

    public BucketDTO getBucketByUser(String name) {
        User user = userService.findByName(name);
        if(user == null || user.getBucket() == null){
            return new BucketDTO();
        }

        BucketDTO bucketDTO = new BucketDTO();
        Map<Long, BucketDetailDTO> mapByProductId = new HashMap<>();

        List<Product> products = user.getBucket().getProducts();
        for (Product product :
                products) {
            BucketDetailDTO detail = mapByProductId.get(product.getId());
            if(detail == null){
                mapByProductId.put(product.getId(), new BucketDetailDTO(product));
            }else{
                detail.setAmount(detail.getAmount().add(new BigDecimal(1.0)));
                detail.setSum(detail.getSum() + Double.valueOf(product.getPrice().toString()));
            }
        }
        bucketDTO.setBucketDetails(new ArrayList<>(mapByProductId.values()));
        bucketDTO.aggregate();

        return bucketDTO;
    }

    private List<Product> getCollectRefProductsByIds(List<Long> productIds) {
        return productIds.stream()
                .map(productDAO::getOne)
                .collect(Collectors.toList());
    }

}
