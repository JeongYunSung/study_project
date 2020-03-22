package com.yunseong.study_project.category.infra.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yunseong.study_project.category.command.domain.QCategory;
import com.yunseong.study_project.category.query.CategoryQueryRepository;
import com.yunseong.study_project.category.query.CategoryResponse;
import com.yunseong.study_project.category.query.QCategoryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

import java.util.List;

import static com.yunseong.study_project.category.command.domain.QCategory.*;

@Repository
public class CategoryRepositoryImpl implements CategoryQueryRepository {

    private EntityManager entityManager;
    private JPAQueryFactory queryFactory;

    public CategoryRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    public Page<CategoryResponse> findCategoryResponseByPage(Pageable pageable) {
        QCategory pCategory = new QCategory("pCategory");
        QCategory cCategory = new QCategory("cCategory");
        List<CategoryResponse> content = this.queryFactory
                .select(new QCategoryResponse(category))
                .from(category)
                .leftJoin(category.parent, pCategory).fetchJoin()
                .leftJoin(category.childList, cCategory)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return PageableExecutionUtils.getPage(content, pageable,
                this.queryFactory.selectFrom(category)::fetchCount);
    }
}
