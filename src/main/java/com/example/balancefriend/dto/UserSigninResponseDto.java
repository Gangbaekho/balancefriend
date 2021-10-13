package com.example.balancefriend.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserSigninResponseDto {

    private String accessToken;

    @Builder
    public UserSigninResponseDto(String accessToken) {
        this.accessToken = accessToken;
    }
}
