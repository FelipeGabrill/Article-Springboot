package com.topavnbanco.artigos.domain.role;

import org.springframework.security.core.GrantedAuthority;

public class Role implements GrantedAuthority {

    private Long id;

    private String authority;

    public Role() {
    }

    public Role(Long id, String authority) {
        this.id = id;
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return authority;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
