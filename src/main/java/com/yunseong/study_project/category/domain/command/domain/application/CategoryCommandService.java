package com.yunseong.study_project.category.domain.command.domain.application;

import com.yunseong.study_project.category.domain.command.domain.domain.Category;
import com.yunseong.study_project.category.domain.command.domain.domain.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryCommandService {

    private final CategoryRepository categoryRepository;

    public Long createCategory(String categoryName) {
        Category category = new Category(categoryName);
        this.categoryRepository.save(category);
        return category.getId();
    }
}
