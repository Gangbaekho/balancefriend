package com.example.balancefriend.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserSigninRequestDto {

    private String name;
    private String password;

    @Builder
    public UserSigninRequestDto(String name, String password) {
        this.name = name;
        this.password = password;
    }
}
