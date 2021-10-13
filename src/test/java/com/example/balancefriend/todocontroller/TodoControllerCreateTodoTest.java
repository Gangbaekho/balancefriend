package com.example.balancefriend.todocontroller;

import com.example.balancefriend.domain.todo.Todo;
import com.example.balancefriend.domain.todo.TodoRepository;
import com.example.balancefriend.domain.user.User;
import com.example.balancefriend.domain.user.UserRepository;
import com.example.balancefriend.dto.TodoCreateRequestDto;
import com.example.balancefriend.dto.TodoCreateResponseDto;
import com.example.balancefriend.dto.UserCreateRequestDto;
import com.example.balancefriend.dto.UserCreateResponseDto;
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
public class TodoControllerCreateTodoTest {

    @LocalServerPort
    private int port;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private JwtTokenProvider tokenProvider;

    private String token;

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
        token = tokenProvider.generateTokenForTest(userPrincipal);
    }

    @Test
    public void 투두_생성_성공케이스(){

        String name = "todo";

        TodoCreateRequestDto requestDto = TodoCreateRequestDto.builder()
                .name(name)
                .build();

        String postUrl = "http://localhost:" + port + "/todos";

        HttpHeaders postHeaders = new HttpHeaders();
        postHeaders.setContentType(MediaType.APPLICATION_JSON);
        postHeaders.set("Authorization","Bearer "+token);
        HttpEntity<TodoCreateRequestDto> postRequestEntity = new HttpEntity<>(requestDto,postHeaders);

        ResponseEntity<TodoCreateResponseDto> postResponseEntity = restTemplate.exchange(postUrl, HttpMethod.POST,postRequestEntity,TodoCreateResponseDto.class);
        assertThat(postResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        List<Todo> todos = todoRepository.findAll();
        assertThat(todos.size()).isEqualTo(1);
    }


    @After
    public void cleanup(){

        todoRepository.deleteAll();
        userRepository.deleteAll();
    }
}
