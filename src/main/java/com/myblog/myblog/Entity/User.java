package com.myblog.myblog.Entity;



import com.myblog.myblog.Dto.UserFormDTO;
import com.myblog.myblog.Enum.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements UserDetails {

    @Id
    @Column(name = "userid")
    private String email;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = true)
    private  String profileImgUrl;

    @Column(nullable = true)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private Role role;

    public static User createUser(UserFormDTO userFormDto,
                                  PasswordEncoder passwordEncoder){

        User user = new User();
        user.setEmail(userFormDto.getEmail());
        user.setUsername(userFormDto.getUsername());
        user.setPassword(passwordEncoder.encode(userFormDto.getPassword()));
        user.setProfileImgUrl(userFormDto.getProfileImgUrl());
        user.setPhoneNumber(userFormDto.getPhoneNumber());
        user.setRole(Role.ADMIN);


        return user;
    }

    public User(String userId, String email){
        this.email = email;
        this.username = userId;
        this.role = Role.ADMIN;
        this.password="chltest";

    }

    public String getRealUserName(){
        return username;
    }

    /*

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ADMIN"));
    }
    */


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add(new GrantedAuthority() {
            private User user;
            @Override
            public String getAuthority() {
                return user.getRole().toString();
            }
        });
        return null;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
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
