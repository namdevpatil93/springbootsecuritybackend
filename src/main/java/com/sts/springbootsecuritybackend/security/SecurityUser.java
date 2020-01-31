package com.sts.springbootsecuritybackend.security;

import org.springframework.security.core.GrantedAuthority;

import com.sts.springbootsecuritybackend.domain.Role;
import com.sts.springbootsecuritybackend.domain.User;

import java.util.Collection;

public class SecurityUser extends org.springframework.security.core.userdetails.User {

    private static final long serialVersionUID = 6230244711955127017L;

    public SecurityUser(String username, String password, Role role, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, true, true, true, true, authorities);
        this.role = role;
    }

    private User user;


    private Role role;

    private Long roleId;

    private Long parentRoleId;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getParentRoleId() {
        return parentRoleId;
    }

    public void setParentRoleId(Long parentRoleId) {
        this.parentRoleId = parentRoleId;
    }
}
