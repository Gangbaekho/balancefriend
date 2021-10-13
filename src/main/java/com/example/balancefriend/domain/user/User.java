package com.example.balancefriend.domain.user;

import com.example.balancefriend.domain.BaseTimeEntity;
import com.example.balancefriend.domain.todo.Todo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String name;

    @Size(min = 0, max = 150)
    private int age;

    @OneToMany(mappedBy = "user")
    private List<Todo> todos = new ArrayList<>();

    @Builder
    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
