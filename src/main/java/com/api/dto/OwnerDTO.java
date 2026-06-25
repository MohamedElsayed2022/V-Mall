package com.api.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class OwnerDTO {
    private Long id;
    private String firstName;
    private  String lastName;
    private String email;
}
