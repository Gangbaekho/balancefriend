package com.example.balancefriend.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TodoUpdateRequestDto {

    private Long todoId;
    private String name;

    @Builder
    public TodoUpdateRequestDto(Long todoId, String name) {
        this.todoId = todoId;
        this.name = name;
    }
}
