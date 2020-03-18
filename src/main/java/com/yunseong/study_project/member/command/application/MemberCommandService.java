package com.yunseong.study_project.member.command.application;

import com.yunseong.study_project.common.errors.NoSuchUsernameException;
import com.yunseong.study_project.member.command.application.dto.MemberCreateRequest;
import com.yunseong.study_project.member.command.application.dto.MemberUpdateRequest;
import com.yunseong.study_project.member.command.domain.Member;
import com.yunseong.study_project.member.command.domain.MemberRepository;
import com.yunseong.study_project.member.command.domain.MyItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberCommandService {

    private final MemberRepository memberRepository;
/*    private final ProductRepository productRepository;*/

    public Long registerMember(MemberCreateRequest memberRequest) {
        return this.memberRepository.save(new Member(memberRequest.getUsername(), memberRequest.getPassword(), memberRequest.getNickname())).getId();
    }

    public Long updateMember(String username, MemberUpdateRequest memberRequest) {
        Member member = getMember(username);
        member.changeInfo(memberRequest.getNickname(), member.getNickname());

        return member.getId();
    }

    public void deleteMemberById(String username) {
        getMember(username).delete();
    }

    public Long addMyItem(String username, Long id) {
        Member member = getMember(username);
/*        member.addMyItem(new MyItem(this.productRepository.findById(id)));*/
        return id;
    }

    private Member getMember(String username) {
        return this.memberRepository.findFetchByUsername(username).orElseThrow(() -> new NoSuchUsernameException("member", username));
    }
}
