package com.online.shop.service.implementation;

import com.online.shop.dao.BucketDAO;
import com.online.shop.dao.ProductDAO;
import com.online.shop.domain.*;
import com.online.shop.domain.enums.OrderStatus;
import com.online.shop.dto.BucketDTO;
import com.online.shop.dto.BucketDetailDTO;
import com.online.shop.service.BucketService;
import com.online.shop.service.OrderService;
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
    private final OrderService orderService;

    public BucketServiceImpl(BucketDAO bucketDAO, ProductDAO productDAO, UserService userService, OrderService orderService) {
        this.bucketDAO = bucketDAO;
        this.productDAO = productDAO;
        this.userService = userService;
        this.orderService = orderService;
    }

    @Transactional
    @javax.transaction.Transactional
    public Bucket createBucket(User user, List<Long> productIds) {
        Bucket bucket = new Bucket();
        bucket.setUser(user);
        List<Product> productList = getCollectRefProductsByIds(productIds);
        bucket.setProducts(productList);
        return bucketDAO.save(bucket);
    }

    private List<Product> getCollectRefProductsByIds(List<Long> productIds) {
        return productIds.stream()
                .map(productDAO::getOne)
                .collect(Collectors.toList());
    }

    @Override
    @javax.transaction.Transactional
    public void addProducts(Bucket bucket, List<Long> productIds) {
        List<Product> products = bucket.getProducts();
        List<Product> newProductList = products == null ? new ArrayList<>() : new ArrayList<>(products);
        newProductList.addAll(getCollectRefProductsByIds(productIds));
        bucket.setProducts(newProductList);
        bucketDAO.save(bucket);
    }

    @Override
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

    @Override
    @Transactional
    public void commitBucketToOrder(String username) {
        User user = userService.findByName(username);
        if(user == null){
            throw new RuntimeException("User is not found");
        }
        Bucket bucket = user.getBucket();
        if(bucket == null || bucket.getProducts().isEmpty()){
            return;
        }

        Order order = new Order();
        order.setStatus(OrderStatus.NEW);
        order.setUser(user);

        Map<Product, Long> productWithAmount = bucket.getProducts().stream()
                .collect(Collectors.groupingBy(product -> product, Collectors.counting()));

        List<OrderDetails> orderDetails = productWithAmount.entrySet().stream()
                .map(pair -> new OrderDetails(order, pair.getKey(), pair.getValue()))
                .collect(Collectors.toList());

        BigDecimal total = new BigDecimal(orderDetails.stream()
                .map(detail -> detail.getPrice().multiply(detail.getAmount()))
                .mapToDouble(BigDecimal::doubleValue).sum());

        order.setDetails(orderDetails);
        order.setSum(total);
        order.setAddress("none");

        orderService.saveOrder(order);
        bucket.getProducts().clear();
        bucketDAO.save(bucket);
    }



}
