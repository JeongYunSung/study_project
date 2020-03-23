package com.yunseong.study_project.product.query.application;

import com.yunseong.study_project.common.errors.NoSuchIdentityException;
import com.yunseong.study_project.product.command.domain.Product;
import com.yunseong.study_project.product.command.domain.ProductRepository;
import com.yunseong.study_project.product.query.application.dto.ProductResponse;
import com.yunseong.study_project.product.query.application.dto.ProductSearchCondition;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class ProductQueryService {

    private final ProductRepository productRepository;

    public ProductResponse findProductResponseById(Long id) {
        return new ProductResponse(getProduct(id));
    }

    public Page<ProductResponse> findProductReponseByPage(ProductSearchCondition condition, Pageable pageable) {
        return this.productRepository.findProductResponseByPage(condition, pageable);
    }

    private Product getProduct(Long id) {
        return this.productRepository.findById(id).orElseThrow(() -> new NoSuchIdentityException("product", id));
    }
}
