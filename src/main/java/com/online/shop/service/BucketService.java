package com.online.shop.service;

import com.online.shop.domain.Bucket;
import com.online.shop.domain.User;
import com.online.shop.dto.BucketDTO;

import java.util.List;

public interface BucketService {

    Bucket createBucket(User user, List<Long> productIds);
    void addProducts(Bucket bucket, List<Long> productIds);
    BucketDTO getBucketByUser(String name);
    void commitBucketToOrder(String username);
}
