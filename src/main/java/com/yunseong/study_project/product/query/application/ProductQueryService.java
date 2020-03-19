package com.yunseong.study_project.product.query.application;

import com.yunseong.study_project.common.errors.NoSuchIdentityException;
import com.yunseong.study_project.product.command.domain.Product;
import com.yunseong.study_project.product.command.domain.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class ProductQueryService {

    private final ProductRepository productRepository;

    public Product findProductById(Long id) {
        return this.productRepository.findById(id).orElseThrow(() -> new NoSuchIdentityException("product", id));
    }
}
