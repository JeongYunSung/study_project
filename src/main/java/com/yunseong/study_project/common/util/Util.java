package com.yunseong.study_project.common.util;

import com.yunseong.study_project.common.domain.CustomUser;
import com.yunseong.study_project.member.command.domain.Grade;
import com.yunseong.study_project.member.command.domain.Member;
import com.yunseong.study_project.member.command.domain.MemberRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;

public class Util {

    public static final String profileURL = "http://localhost:8080/api/docs";

    public static boolean isMemberEqOwner(Long id, MemberRepository memberRepository) {
        Long memberId = ((CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        Member member = memberRepository.findById(memberId).get();
        return memberId == id || isAdmin(member.getGrade());
    }

    public static String pageableQuery(Pageable pageable) {
        String query = "";
        if (pageable != null) {
            query += "?";
            if (pageable.getPageNumber() > 0)
                query += "page=" + pageable.getPageNumber() + "&";
            if (pageable.getPageSize() > 0 && pageable.getPageSize() != 10)
                query += "size=" + pageable.getPageSize() + "&";
            if (pageable.getSort() != null && !pageable.getSort().isUnsorted())
                query += "sort=" + pageable.getSort() + "&";
            query = query.substring(0, query.length()-1);
        }
        return query;
    }

    public static boolean isAdmin(Grade grade) {
        return (grade == Grade.ADMIN || grade == Grade.MANAGER);
    }
}
