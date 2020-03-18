package com.yunseong.study_project.member.query.application;

import com.yunseong.study_project.common.errors.NoSuchIdentityException;
import com.yunseong.study_project.member.ui.validator.NoSuchMyItemException;
import com.yunseong.study_project.common.errors.NoSuchUsernameException;
import com.yunseong.study_project.member.command.domain.Member;
import com.yunseong.study_project.member.command.domain.MemberRepository;
import com.yunseong.study_project.member.query.application.dto.MyItemResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberQueryService {

    private final MemberRepository memberRepository;

    public Member findMemberById(Long id) {
        return this.memberRepository.findFetchById(id).orElseThrow(() -> new NoSuchIdentityException("member", id));
    }

    public Member findMemberByUsername(String username) {
        return this.memberRepository.findFetchByUsername(username).orElseThrow(() -> new NoSuchUsernameException("member", username));
    }


    public Page<Member> findAllByPage(Pageable pageable) {
        return this.memberRepository.findFetchAll(pageable);
    }

    public MyItemResponse findMyItem(String username, Long id) {
        return this.memberRepository.findFetchMyItem(username, id).orElseThrow(() -> new NoSuchMyItemException(username, id));
    }

    public Page<MyItemResponse> findMemberItemByPage(String username, Pageable pageable) {
        return this.memberRepository.findFetchMyItemByPage(username, pageable);
    }
}