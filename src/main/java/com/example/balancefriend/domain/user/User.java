package com.example.balancefriend.domain.user;

import com.example.balancefriend.domain.BaseTimeEntity;
import com.example.balancefriend.domain.todo.Todo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
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

    private String password;

    @Min(0)
    @Max(150)
    private int age;

    @OneToMany(mappedBy = "user")
    private List<Todo> todos = new ArrayList<>();

    @Builder
    public User(String name,String password ,int age) {
        this.name = name;
        this.password = password;
        this.age = age;
    }
}
