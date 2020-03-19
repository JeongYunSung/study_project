package com.yunseong.study_project.category.domain.command.domain.domain;

import com.yunseong.study_project.category.domain.command.domain.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
