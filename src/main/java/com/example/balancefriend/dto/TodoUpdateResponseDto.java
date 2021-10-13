package com.example.balancefriend.dto;

import com.example.balancefriend.domain.todo.Todo;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class TodoUpdateResponseDto {

    private Long id;
    private String name;
    private Boolean completed;
    private LocalDateTime completedAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public TodoUpdateResponseDto(Todo todo) {
        this.id = todo.getId();
        this.name = todo.getName();
        this.completed = todo.getCompleted();
        this.completedAt = todo.getCompletedAt();
        this.createdAt = todo.getCreatedAt();
        this.updatedAt = todo.getUpdatedAt();
    }
}
