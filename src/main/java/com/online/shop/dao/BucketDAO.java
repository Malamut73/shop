package com.online.shop.dao;

import com.online.shop.domain.Bucket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BucketDAO extends JpaRepository<Bucket, Long> {
}
