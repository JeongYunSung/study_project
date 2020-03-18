package com.yunseong.study_project.member.command.domain;

import com.yunseong.study_project.member.query.repository.MemberQueryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberQueryRepository {

    @Query("select m, mi from Member m join fetch m.myItems mi where m.id = :id")
    Optional<Member> findFetchById(Long id);

    @Query("select m, mi from Member m join fetch m.myItems mi where m.username = :username")
    Optional<Member> findFetchByUsername(String username);

    @Query(value = "select m from Member m join fetch m.myItems mi", countQuery = "select count(m) from Member m")
    Page<Member> findFetchAll(Pageable pageable);
}
