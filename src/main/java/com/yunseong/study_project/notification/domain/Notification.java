package com.yunseong.study_project.notification.domain;

import com.yunseong.study_project.common.domain.BaseUserEntity;
import com.yunseong.study_project.member.command.domain.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AttributeOverride(name = "id", column = @Column(name = "notification_id"))
public class Notification extends BaseUserEntity {

    @ManyToOne(fetch = FetchType.LAZY) // 이렇게 중복된 엔티티에 관한 매핑에 대해선 헷갈리 수 있으니 하나의 쌍으로보자 (R, S) <-> N 이런식으로 관계를 생각하자
    @JoinColumn(name = "receiver_name")
    private Member receiver;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_name")
    private Member sender;

    private String title;

    private String message;

    public Notification(String title, String message) {
        this.title = title;
        this.message = message;
    }

    public void setReceiver(Member member) {
        this.receiver = receiver;
    }

    public void setSender(Member member) {
        this.sender = sender;
    }
}
