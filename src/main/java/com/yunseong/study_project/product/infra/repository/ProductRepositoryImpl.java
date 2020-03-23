package com.yunseong.study_project.product.infra.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yunseong.study_project.product.query.application.dto.ProductResponse;
import com.yunseong.study_project.product.query.application.dto.ProductSearchCondition;
import com.yunseong.study_project.product.query.application.dto.QProductResponse;
import com.yunseong.study_project.product.query.repository.ProductQueryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.List;

import static com.yunseong.study_project.category.command.domain.QCategory.category;
import static com.yunseong.study_project.product.command.domain.QProduct.product;
import static com.yunseong.study_project.product.command.domain.QType.type1;

@Repository
public class ProductRepositoryImpl implements ProductQueryRepository {

    private JPAQueryFactory queryFactory;

    public ProductRepositoryImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    public Page<ProductResponse> findProductResponseByPage(ProductSearchCondition condition, Pageable pageable) {
        List<ProductResponse> result = this.queryFactory
                .select(new QProductResponse(product))
                .from(product)
                .innerJoin(product.types, type1)
                .innerJoin(type1.type, category)
                .where(productNameEq(condition.getProductName()), contentEq(condition.getContent()), categoryNameEq(condition.getCategoryName()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return PageableExecutionUtils.getPage(result, pageable,
                this.queryFactory
                        .select(new QProductResponse(product))
                        .from(product)
                        .innerJoin(product.types, type1)
                        .innerJoin(type1.type, category)
                        .where(productNameEq(condition.getProductName()), contentEq(condition.getContent()), categoryNameEq(condition.getCategoryName()))::fetchCount);
    }

    private BooleanExpression categoryNameEq(String categoryName) {
        return StringUtils.hasText(categoryName) ? category.categoryName.lower().contains(categoryName.toLowerCase()) : null;
    }

    private BooleanExpression contentEq(String content) {
        return StringUtils.hasText(content) ? product.content.lower().contains(content.toLowerCase()) : null;
    }

    private BooleanExpression productNameEq(String productName) {
        return StringUtils.hasText(productName) ?  product.productName.lower().contains(productName.toLowerCase()) : null;
    }
}
