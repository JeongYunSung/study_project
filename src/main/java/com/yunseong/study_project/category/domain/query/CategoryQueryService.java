package com.yunseong.study_project.category.domain.query;

import com.yunseong.study_project.category.domain.command.domain.domain.Category;
import com.yunseong.study_project.category.domain.command.domain.domain.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryQueryService {

    private final CategoryRepository categoryRepository;

    public Category findCategoryById(Long id) {
        return this.findCategoryById(id);
    }
}
