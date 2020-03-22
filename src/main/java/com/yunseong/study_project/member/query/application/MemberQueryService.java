package com.yunseong.study_project.member.query.application;

import com.yunseong.study_project.common.errors.NoSuchUsernameException;
import com.yunseong.study_project.common.util.Util;
import com.yunseong.study_project.member.command.domain.Member;
import com.yunseong.study_project.member.command.domain.MemberRepository;
import com.yunseong.study_project.member.query.application.dto.MyItemResponse;
import com.yunseong.study_project.member.ui.validator.NoSuchMyItemException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberQueryService {

    private final MemberRepository memberRepository;

    public Member findMemberByUsername(String username) {
        return this.memberRepository.findMemberByUsername(username).orElseThrow(() -> new NoSuchUsernameException("member", username));
    }

    public MyItemResponse findMyItem(String username, Long id) {
        return this.memberRepository.findMyItemResponse(username, id).orElseThrow(() -> new NoSuchMyItemException(username, id));
    }

    public Page<MyItemResponse> findMemberItemByPage(String username, Pageable pageable) {
        return this.memberRepository.findMyItemResponseByPage(username, pageable);
    }

    public boolean isOwner(Long id, Errors errors) {
        boolean check = Util.isMemberEqOwner(id, this.memberRepository);
        if (check) {
            errors.reject("Authentication failure", "You is Authentication failure");
        }
        return check;
    }
}