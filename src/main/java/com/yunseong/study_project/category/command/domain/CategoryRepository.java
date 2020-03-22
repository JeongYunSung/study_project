package com.yunseong.study_project.category.command.domain;

import com.yunseong.study_project.category.query.CategoryQueryRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long>, CategoryQueryRepository {

    @Query("select c from Category c left join fetch c.parent where c.id = :id")
    Optional<Category> findFetchCategoryById(Long id);
}
