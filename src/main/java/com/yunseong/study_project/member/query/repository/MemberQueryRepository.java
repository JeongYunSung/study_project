package com.yunseong.study_project.member.query.repository;

import com.yunseong.study_project.member.query.application.dto.MyItemResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface MemberQueryRepository {

    Optional<MyItemResponse> findMyItemResponse(String username, Long id);

    Page<MyItemResponse> findMyItemResponseByPage(String username, Pageable pageable);

}
