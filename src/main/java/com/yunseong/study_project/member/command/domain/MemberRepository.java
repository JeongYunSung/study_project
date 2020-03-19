package com.yunseong.study_project.member.command.domain;

import com.yunseong.study_project.member.query.repository.MemberQueryRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberQueryRepository {

    @Query("select m from Member m where m.username = :username")
    Optional<Member> findMemberByUsername(String username);
}
