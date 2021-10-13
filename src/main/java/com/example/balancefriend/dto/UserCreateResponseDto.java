package com.example.balancefriend.dto;

import com.example.balancefriend.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserCreateResponseDto {

    private Long id;
    private String name;
    private int age;

    public UserCreateResponseDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.age = user.getAge();
    }
}
