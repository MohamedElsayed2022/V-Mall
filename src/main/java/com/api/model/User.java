package com.api.model;

import com.api.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class User extends BaseEntity implements UserDetails {

    @Column(name = "firstname")
    private String firstName;
    @Column(name = "lastName")
    private String lastName;
    @Column(name = "fullName")
    private String fullName;
    @Column(name = "email")
    private String email;
    @JsonIgnore
    @Column(name = "password")
    private String password;
    @Column(name = "active")
    private int active;
    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdDate;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime lastModifiedDate;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role" ,
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")}

    )
    private List<Role>  roles = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authoritiesList = new ArrayList<>();

        roles.forEach((authority) -> {
            GrantedAuthority grantedAuthority =
                    new SimpleGrantedAuthority(authority.getRoleName());

            authoritiesList.add(grantedAuthority);
        });

        return authoritiesList;    }

    @Override
    public String getPassword() {
        return password;
    }
    @Override
    public String getUsername() {
        return email;
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
        return active == 1;
    }

    public String getFullName(){
        return getFirstName() + " " + getLastName();
    }

    @OneToMany(mappedBy = "user" ,  cascade = CascadeType.ALL)
    private List<Token> token = new ArrayList<>();





}
