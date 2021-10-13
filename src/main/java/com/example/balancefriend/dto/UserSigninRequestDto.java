package com.example.balancefriend.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserSigninRequestDto {

    private String name;

    @Builder
    public UserSigninRequestDto(String name) {
        this.name = name;
    }
}
