package com.online.shop.dao;

import com.online.shop.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductDAO extends JpaRepository<Product, Long> {
}
