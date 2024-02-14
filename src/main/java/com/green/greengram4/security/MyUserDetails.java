package com.green.greengram4.security;

import com.green.greengram4.user.model.UserModel;
import lombok.Builder;
import lombok.Data;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@Builder //userdetails 로컬 로그인, oauth2user 소셜 로그인
public class MyUserDetails implements UserDetails, OAuth2User {

    private MyPrincipal myPrincipal;
    private Map<String, Object> attributes;
    private UserModel userModel;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(myPrincipal == null) {
            return null;
        }
        return this.myPrincipal.getRoles().stream()
                //.map(SimpleGrantedAuthority::new) 가공없다면 이렇게 써도됨
                //.map(() -> new SimpleGrantedAuthority())
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return userModel == null ? null : userModel.getUid();
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

    @Override
    public String getName() {
        return null;
    }
}
