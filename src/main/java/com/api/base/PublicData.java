package com.api.base;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class PublicData extends AuditableEntity  {

    @Column(name = "firstname")
    private String firstName;
    @Column(name = "lastName")
    private String lastName;
}
