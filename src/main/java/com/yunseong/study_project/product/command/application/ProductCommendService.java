package com.yunseong.study_project.product.command.application;

import com.yunseong.study_project.category.domain.command.domain.domain.CategoryRepository;
import com.yunseong.study_project.product.command.application.dto.ProductCreateRequest;
import com.yunseong.study_project.product.command.domain.Product;
import com.yunseong.study_project.product.command.domain.ProductRepository;
import com.yunseong.study_project.product.command.domain.Type;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductCommendService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public Long registerProduct(ProductCreateRequest productCreateRequest) {
        List<Type> list = productCreateRequest.getCategoryIdList().stream().map(id -> this.categoryRepository.findById(id))
                .filter(category -> category.isPresent())
                .map(category -> new Type(category.get()))
                .collect(Collectors.toList());
        Product product = new Product(productCreateRequest.getProductName(), productCreateRequest.getContent(),
                productCreateRequest.getAmount(), productCreateRequest.getPrice(), list);
        this.productRepository.save(product);
        return product.getId();
    }
}
