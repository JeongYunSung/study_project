package com.yunseong.study_project.member.command.application;

import com.yunseong.study_project.common.domain.CustomUser;
import com.yunseong.study_project.member.command.domain.Member;
import com.yunseong.study_project.member.command.domain.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class MemberDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = this.memberRepository.findMemberByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username + "라는 유저는 존재하지 않습니다."));

        CustomUser user = new CustomUser(member.getUsername(), member.getPassword(), Arrays.asList(new SimpleGrantedAuthority("ROLE_" + member.getGrade().getTag())),member.getId());
/*        return CustomUser.builder()
                .username(member.getUsername())
                .password(member.getPassword())
                .roles(member.getGrade().getTag())
                .build();*/
        return user;
    }
}
