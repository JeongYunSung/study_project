package com.yunseong.study_project.product.query.repository;

import com.yunseong.study_project.product.query.application.dto.ProductResponse;
import com.yunseong.study_project.product.query.application.dto.ProductSearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductQueryRepository {

    Page<ProductResponse> findProductResponseByPage(ProductSearchCondition condition, Pageable pageable);
}
