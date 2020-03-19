package com.yunseong.study_project.member.command.domain;

import com.yunseong.study_project.common.domain.BaseEntity;
import com.yunseong.study_project.order.domain.Order;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "username", "nickname" }))
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AttributeOverride(name = "id", column = @Column(name = "member_id"))
public class Member extends BaseEntity {

    private String username;

    private String password;

    private String nickname;

    private long money;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<MyItem> myItems = new ArrayList<>();

    @OneToMany(mappedBy = "orderer")
    private List<Order> orders = new ArrayList<>();

    @Enumerated(value = EnumType.STRING)
    private Grade grade;

    public Member(String username, String password, String nickname) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.grade = Grade.BRONZE;
    }

    public void changeInfo(String nickname, String password) {
        this.nickname = nickname;
        this.password = password;
    }

    public void addMoney(long money) {
        this.money += money;
    }

    public void subMoney(long money) {
        this.money -= money;
    }

    public boolean nextGrade() {
        Grade nextGrade =  this.getGrade().cofirmGrade(this.getMyItems().size());
        return this.getGrade() != nextGrade;
    }

    public void addMyItem(MyItem myItem) {
        this.getMyItems().add(myItem);
        myItem.setMember(this);
    }

    public void encode(PasswordEncoder encoder) {
        this.password = encoder.encode(this.getPassword());
    }

    @Override
    public void delete() {
        super.delete();
        this.getOrders().forEach(Order::delete);
        this.getMyItems().forEach(MyItem::delete);
    }
}
