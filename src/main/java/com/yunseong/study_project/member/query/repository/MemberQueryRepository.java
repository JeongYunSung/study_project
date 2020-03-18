package com.yunseong.study_project.member.query.repository;

import com.yunseong.study_project.member.query.application.dto.MyItemResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface MemberQueryRepository {

    Optional<MyItemResponse> findFetchMyItem(String username, Long id);

    Page<MyItemResponse> findFetchMyItemByPage(String username, Pageable pageable);

}
