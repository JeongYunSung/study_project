package com.yunseong.study_project.member.command.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query("select m, mi from Member m join fetch m.myItems mi on m.id = :id")
    Optional<Member> findFetchById(Long id);

    @Query("select m, mi from Member m join fetch m.myItems mi on m.username = :ussername")
    Optional<Member> findFetchByUsername(String username);

    @Query(value = "select m, mi from Member m join fetch m.myItems mi", countQuery = "select count(m) from Member m")
    Page<Member> findFetchAll(Pageable pageable);
}
