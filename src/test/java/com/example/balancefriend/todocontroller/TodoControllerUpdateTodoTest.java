package com.example.balancefriend.todocontroller;

import com.example.balancefriend.domain.todo.Todo;
import com.example.balancefriend.domain.todo.TodoRepository;
import com.example.balancefriend.domain.user.User;
import com.example.balancefriend.domain.user.UserRepository;
import com.example.balancefriend.dto.TodoDeleteResponseDto;
import com.example.balancefriend.dto.TodoUpdateRequestDto;
import com.example.balancefriend.dto.TodoUpdateResponseDto;
import com.example.balancefriend.security.JwtTokenProvider;
import com.example.balancefriend.security.UserPrincipal;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TodoControllerUpdateTodoTest {

    @LocalServerPort
    private int port;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private JwtTokenProvider tokenProvider;

    private String tokenOne;
    private String tokenTwo;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setUp(){

        // already exists user
        String name = "tester";
        String password = "tester";
        int age = 15;

        User user = User.builder()
                .name(name)
                .password(password)
                .age(age)
                .build();

        userRepository.save(user);

        UserPrincipal userPrincipal = UserPrincipal.create(user);
        tokenOne = tokenProvider.generateTokenForTest(userPrincipal);

        String nameTwo = "tester2";
        String passwordTwo = "tester2";
        int ageTwo = 15;

        User userTwo = User.builder()
                .name(nameTwo)
                .password(passwordTwo)
                .age(ageTwo)
                .build();

        userRepository.save(userTwo);

        UserPrincipal userPrincipalTwo = UserPrincipal.create(userTwo);
        tokenTwo = tokenProvider.generateTokenForTest(userPrincipalTwo);

        String todoName = "todo";

        Todo todo = Todo.builder()
                .name(todoName)
                .build();

        todo.updateUser(user);

        todoRepository.save(todo);
    }

    @Test
    public void 투두_이름_업데이트_성공케이스(){

        String updated = "updated";

        Todo todo = todoRepository.findAll().get(0);

        TodoUpdateRequestDto requestDto = TodoUpdateRequestDto.builder()
                .name(updated)
                .todoId(todo.getId())
                .build();

        String updateUrl = "http://localhost:" + port + "/todos";

        HttpHeaders updateHeaders = new HttpHeaders();
        updateHeaders.setContentType(MediaType.APPLICATION_JSON);
        updateHeaders.set("Authorization","Bearer "+tokenOne);
        HttpEntity<TodoUpdateRequestDto> updateRequestEntity = new HttpEntity<>(requestDto,updateHeaders);

        ResponseEntity<TodoUpdateResponseDto> updateResponseEntity = restTemplate.exchange(updateUrl, HttpMethod.PUT,updateRequestEntity,TodoUpdateResponseDto.class);
        assertThat(updateResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        List<Todo> todos = todoRepository.findAll();
        assertThat(todos.get(0).getName()).isEqualTo(updated);
    }

    @After
    public void cleanup(){

        todoRepository.deleteAll();
        userRepository.deleteAll();
    }
}
