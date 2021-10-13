package com.example.balancefriend.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TodoCreateRequestDto {

    private String name;

    @Builder
    public TodoCreateRequestDto(String name) {
        this.name = name;
    }
}
