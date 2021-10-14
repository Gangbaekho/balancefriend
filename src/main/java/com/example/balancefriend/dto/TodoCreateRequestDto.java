package com.example.balancefriend.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TodoCreateRequestDto {

    private String name;
    private boolean completed;

    @Builder
    public TodoCreateRequestDto(String name, boolean completed) {
        this.name = name;
        this.completed = completed;
    }
}
