package com.yunseong.study_project.category.query;

import com.yunseong.study_project.category.command.domain.Category;
import com.yunseong.study_project.category.command.domain.CategoryRepository;
import com.yunseong.study_project.common.errors.NoSuchIdentityException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryQueryService {

    private final CategoryRepository categoryRepository;

    public CategoryResponse findCategoryResponseById(Long id) {
        return new CategoryResponse(getCategory(id));
    }

    public Page<CategoryResponse> findCategoryResponseByPage(Pageable pageable) {
        return this.categoryRepository.findCategoryResponseByPage(pageable);
    }

    private Category getCategory(Long id) {
        return this.categoryRepository.findFetchCategoryById(id).orElseThrow(() -> new NoSuchIdentityException("category", id));
    }
}
