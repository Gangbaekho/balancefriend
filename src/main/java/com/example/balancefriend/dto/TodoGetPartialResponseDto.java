package com.example.balancefriend.dto;

import com.example.balancefriend.domain.todo.Todo;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TodoGetPartialResponseDto {

    private String name;
    private boolean completed;

    public TodoGetPartialResponseDto(Todo todo) {
        this.name = todo.getName();
        this.completed = todo.getCompleted();
    }
}
