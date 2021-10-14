package com.example.balancefriend.todocontroller;

import com.example.balancefriend.domain.todo.Todo;
import com.example.balancefriend.domain.todo.TodoRepository;
import com.example.balancefriend.domain.user.User;
import com.example.balancefriend.domain.user.UserRepository;
import com.example.balancefriend.dto.TodoCompleteResponseDto;
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

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TodoControllerCompleteTodoTest {

    @LocalServerPort
    private int port;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private JwtTokenProvider tokenProvider;

    private String tokenOne;

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

        String todoNameOne = "todoOne";

        Todo todoOne = Todo.builder()
                .name(todoNameOne)
                .build();

        todoOne.updateUser(user);

        todoRepository.save(todoOne);
    }

    @Test
    public void 투두_싱글_모든필드_패치_성공케이스(){

        Todo todo = todoRepository.findAll().get(0);

        String postUrl = "http://localhost:" + port + "/todos/" + todo.getId() + "/complete";

        HttpHeaders postHeaders = new HttpHeaders();
        postHeaders.setContentType(MediaType.APPLICATION_JSON);
        postHeaders.set("Authorization","Bearer "+tokenOne);
        HttpEntity<Object> getRequestEntity = new HttpEntity<>(null,postHeaders);

        ResponseEntity<TodoCompleteResponseDto> postResponseEntity = restTemplate.exchange(postUrl, HttpMethod.POST,getRequestEntity,TodoCompleteResponseDto.class);
        assertThat(postResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(postResponseEntity.getBody().getCompleted()).isEqualTo(true);
    }

    @After
    public void cleanup(){

        todoRepository.deleteAll();
        userRepository.deleteAll();
    }
}
