package com.sedia.resume.entity;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
public class UserEntity extends GenericEntity implements UserDetails {

    /** 帳號 */
    private String account;

    private String password;

    private String name;

    private String email;

    private String phone;

    private LocalDate birthday;

    private String sex;

    private String militaryService;

    private String militaryDate;

    private String address;

    private String driverLicense;

    private String specialIdentity;

    private String feature;

    private String introduction;

    private String bioEng;

    private String bioChn;

    private String imgPath;

    private List<LinkEntity> links;

    @Hidden
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>();
    }

    @Hidden
    @Override
    public String getUsername() {
        return this.account;
    }

    @Hidden
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Hidden
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Hidden
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Hidden
    @Override
    public boolean isEnabled() {
        return true;
    }
}
