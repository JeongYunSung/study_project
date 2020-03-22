package com.yunseong.study_project.product.command.domain;

import com.yunseong.study_project.product.query.repository.ProductQueryRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long>, ProductQueryRepository {
}
