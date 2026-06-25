package com.api.model;

import com.api.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "roles")
public class Role extends BaseEntity {
    @Column(name = "roles")
    private String roleName;
    @ManyToMany(mappedBy = "roles" )
    private List<User> users = new ArrayList<>();
}
