package com.kpi.diploma.smartroads.model.security;

import com.kpi.diploma.smartroads.model.document.user.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.social.security.SocialUserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class MongoUserDetails implements SocialUserDetails {

    private String userId;
    private String userName;
    private String password;
    private Boolean enable;
    private List<GrantedAuthority> roles;

    public MongoUserDetails(User user) {
        log.info("in user detail constructor");
        this.userId = user.getId();
        this.userName = user.getEmail();
        this.password = user.getPassword();
        this.enable = user.getEnable();
        this.roles = user.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getRole())).collect(Collectors.toList());
        log.info("user details created");
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public String getPassword() { return password; }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enable;
    }

    @Override
    public String getUserId() {
        return this.userId;
    }
}
