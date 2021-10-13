package com.example.balancefriend.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Getter
@NoArgsConstructor
public class UserCreateRequestDto {

    private String name;
    private String password;

    @Min(0)
    @Max(150)
    private int age;

    @Builder
    public UserCreateRequestDto(String name,String password, int age) {
        this.name = name;
        this.password = password;
        this.age = age;
    }
}
