package com.yunseong.study_project.category.command.application;

import com.yunseong.study_project.category.command.domain.Category;
import com.yunseong.study_project.category.command.domain.CategoryRepository;
import com.yunseong.study_project.common.errors.NoSuchIdentityException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryCommandService {

    private final CategoryRepository categoryRepository;

    public Long createCategory(String categoryName) {
        Category category = new Category(categoryName);
        this.categoryRepository.save(category);
        return category.getId();
    }

    public Long updateChildCategory(Long parentId, Long childId) {
        Category c1 = getCategory(parentId);
        Category c2 = getCategory(childId);
        c1.addSubCategory(c2);
        return childId;
    }

    public Long updateCategoryName(Long id, String categoryName) {
        getCategory(id).changeCategoryName(categoryName);
        return id;
    }

    private Category getCategory(Long id) {
        return this.categoryRepository.findById(id).orElseThrow(() -> new NoSuchIdentityException("category", id));
    }
}
