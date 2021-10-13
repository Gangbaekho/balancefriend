package com.example.balancefriend.domain.todo;

import com.example.balancefriend.domain.BaseTimeEntity;
import com.example.balancefriend.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
public class Todo extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private boolean completed;
    private LocalDateTime completedAt;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @Builder
    public Todo(String name, boolean completed, LocalDateTime completedAt) {
        this.name = name;
        this.completed = completed;
        this.completedAt = completedAt;
    }

    public void updateUser(User user){

        this.user = user;
    }

}
