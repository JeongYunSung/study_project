package com.yunseong.study_project.product.command.application;

import com.yunseong.study_project.category.command.domain.Category;
import com.yunseong.study_project.category.command.domain.CategoryRepository;
import com.yunseong.study_project.common.errors.NoSuchIdentityException;
import com.yunseong.study_project.product.command.domain.Product;
import com.yunseong.study_project.product.command.domain.ProductRepository;
import com.yunseong.study_project.product.command.domain.Type;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductCommendService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public Long registerProduct(ProductRequest productCreateRequest) {
        List<Type> list = productCreateRequest.getCategoryIdList().stream().map(id -> this.categoryRepository.findById(id))
                .filter(category -> category.isPresent())
                .map(category -> new Type(category.get()))
                .collect(Collectors.toList());
        Product product = new Product(productCreateRequest.getProductName(), productCreateRequest.getContent()
                , productCreateRequest.getPrice(), list);
        this.productRepository.save(product);
        return product.getId();
    }

    public Long updateProduct(Long id, ProductRequest productUpdateRequest) {
        Product product = this.productRepository.findById(id).orElseThrow(() -> new NoSuchIdentityException("product", id));
        product.changeName(productUpdateRequest.getProductName());
        product.changeContent(productUpdateRequest.getContent());
        product.changePrice(productUpdateRequest.getPrice());
        List<Category> categories = new ArrayList<>();
        for (Long idx : productUpdateRequest.getCategoryIdList()) {
            categories.add(this.categoryRepository.findFetchCategoryById(idx)
                    .orElseThrow(() -> new NoSuchIdentityException("category", idx)));
        }
        categories.stream().map(Type::new).forEach(product::addType);
        return id;
    }
}
