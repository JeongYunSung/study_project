package com.yunseong.study_project.member.command.application;

import com.yunseong.study_project.common.errors.NoSuchIdentityException;
import com.yunseong.study_project.common.errors.NoSuchUsernameException;
import com.yunseong.study_project.member.command.application.dto.MemberCreateRequest;
import com.yunseong.study_project.member.command.application.dto.MemberUpdateRequest;
import com.yunseong.study_project.member.command.domain.Member;
import com.yunseong.study_project.member.command.domain.MemberRepository;
import com.yunseong.study_project.member.command.domain.MyItem;
import com.yunseong.study_project.product.command.domain.Product;
import com.yunseong.study_project.product.command.domain.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberCommandService {

    private final PasswordEncoder passwordEncoder;

    private final MemberRepository memberRepository;

    private final ProductRepository productRepository;

    public Long registerMember(MemberCreateRequest memberRequest) {
        Member entity = new Member(memberRequest.getUsername(), memberRequest.getPassword(), memberRequest.getNickname());
        entity.encode(this.passwordEncoder);
        return this.memberRepository.save(entity).getId();
    }

    public Long updateMember(String username, MemberUpdateRequest memberRequest) {
        Member member = getMember(username);
        member.changeInfo(memberRequest.getNickname(), member.getNickname());

        return member.getId();
    }

    public void deleteMemberById(String username) {
        getMember(username).delete();
    }

    public Long addMyItem(String username, Long productId) {
        MyItem myItem = new MyItem(this.productRepository.findById(productId).orElseThrow(() -> new NoSuchIdentityException("product", productId)));
        getMember(username).addMyItem(myItem);
        return myItem.getId();
    }

    private Member getMember(String username) {
        return this.memberRepository.findMemberByUsername(username).orElseThrow(() -> new NoSuchUsernameException("member", username));
    }
}
