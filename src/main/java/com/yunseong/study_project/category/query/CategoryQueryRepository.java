package com.yunseong.study_project.category.query;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryQueryRepository {

    Page<CategoryResponse> findCategoryResponseByPage(Pageable pageable);
}
