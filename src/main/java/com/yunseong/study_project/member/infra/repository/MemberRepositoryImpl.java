package com.yunseong.study_project.member.infra.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yunseong.study_project.member.query.application.dto.MyItemResponse;
import com.yunseong.study_project.member.query.application.dto.QMyItemResponse;
import com.yunseong.study_project.member.query.repository.MemberQueryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static com.yunseong.study_project.member.command.domain.QMember.member;
import static com.yunseong.study_project.member.command.domain.QMyItem.myItem;
import static org.springframework.util.StringUtils.hasText;

@Repository
@Transactional(readOnly = true)
public class MemberRepositoryImpl implements MemberQueryRepository {

    private EntityManager entityManager;
    private JPAQueryFactory queryFactory;

    public MemberRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    public Optional<MyItemResponse> findMyItemResponse(String username, Long id) {
        MyItemResponse myItemResponse = this.queryFactory
                .select(new QMyItemResponse(myItem))
                .from(myItem)
                .where(myItemByMemberUsernameEq(username), myItemByIdEq(id))
                .fetchOne();

        return Optional.of(myItemResponse);
    }

    public Page<MyItemResponse> findMyItemResponseByPage(String username, Pageable pageable) {
        List<MyItemResponse> results = this.queryFactory
                .select(new QMyItemResponse(myItem))
                .from(member)
                .leftJoin(member.myItems, myItem)
                .where(memberUsernameEq(username))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return PageableExecutionUtils.getPage(results, pageable,
                this.queryFactory
                        .select(new QMyItemResponse(myItem))
                        .from(member)
                        .leftJoin(member.myItems, myItem)
                        .on(memberUsernameEq(username))::fetchCount
        );
    }

    private BooleanExpression myItemByIdEq(Long id) {
        return id != null ? myItem.id.eq(id) : null;
    }

    private BooleanExpression myItemByMemberUsernameEq(String username) {
        return hasText(username) ? myItem.member.username.eq(username) : null;
    }

    private BooleanExpression memberUsernameEq(String username) {
        return hasText(username) ? member.username.eq(username) : null;
    }
}
