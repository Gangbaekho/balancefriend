package com.example.balancefriend.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
public class UserCreateRequestDto {

    private String name;

    @Size(min = 0, max = 150)
    private int age;

    @Builder
    public UserCreateRequestDto(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
