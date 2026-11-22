package com.qst.medical.model;

import com.qst.medical.domain.Account;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * 前端数据交互实体类，实现 UserDetails 供 Spring Security 认证使用
 */
public class AccountModel extends Account implements UserDetails {

    private static final long serialVersionUID = 1L;

    /** 用户真实姓名 */
    private String urealName;

    /** 认证（权限）集合 */
    private Collection<? extends GrantedAuthority> authorities;

    public AccountModel() {
    }

    public AccountModel(Long id, String uname, String realname, String role, String pwd,
                        Collection<? extends GrantedAuthority> authorities) {
        this.setId(id);
        this.setUname(uname);
        this.setUtype(role);
        this.setUrealName(realname);
        this.setPwd(pwd);
        this.authorities = authorities;
    }

    public String getUrealName() {
        return urealName;
    }

    public void setUrealName(String urealName) {
        this.urealName = urealName;
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.getPwd();
    }

    @Override
    public String getUsername() {
        return this.getUname();
    }

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
        return true;
    }
}
