package com.yunseong.study_project.common.domain;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
public class CustomUser extends User {

    private Long id;

    public CustomUser(String username, String password, Collection<? extends GrantedAuthority> authorities, Long id) {
        super(username, password, authorities);
        this.id = id;
    }


/*    @Builder
    public CustomUser(Long id, String username, String password, String...roles) {
        super(username, password, Stream.of(roles).map(role -> new SimpleGrantedAuthority("ROLE_" + role)).collect(Collectors.toList()));
        this.id = id;
    }*/
}
