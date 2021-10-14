package com.example.balancefriend.todocontroller;

import com.example.balancefriend.domain.todo.Todo;
import com.example.balancefriend.domain.todo.TodoRepository;
import com.example.balancefriend.domain.user.User;
import com.example.balancefriend.domain.user.UserRepository;
import com.example.balancefriend.dto.TodoGetFullResponseDto;
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
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TodoControllerGetTodoTest {

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

        String todoNameTwo = "todoTwo";

        Todo todoTwo = Todo.builder()
                .name(todoNameTwo)
                .build();

        todoTwo.updateUser(user);

        todoRepository.save(todoOne);
        todoRepository.save(todoTwo);
    }

    @Test
    public void 투두_싱글_모든필드_패치_성공케이스(){

        Todo todo = todoRepository.findAll().get(0);

        String getUrl = "http://localhost:" + port + "/todos/" + todo.getId() + "/full";

        HttpHeaders getHeaders = new HttpHeaders();
        getHeaders.setContentType(MediaType.APPLICATION_JSON);
        getHeaders.set("Authorization","Bearer "+tokenOne);
        HttpEntity<Object> getRequestEntity = new HttpEntity<>(null,getHeaders);

        ResponseEntity<TodoGetFullResponseDto> getResponseEntity = restTemplate.exchange(getUrl, HttpMethod.GET,getRequestEntity,TodoGetFullResponseDto.class);
        assertThat(getResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        TodoGetFullResponseDto response = getResponseEntity.getBody();
        assertThat(response.getId()).isEqualTo(todo.getId());
        assertThat(response.getCompleted()).isEqualTo(false);
        assertThat(response.getName()).isEqualTo("todoOne");
        assertThat(response.getCreatedAt()).isNotNull();
        assertThat(response.getUpdatedAt()).isNotNull();
    }

    @Test
    public void 투두_리스트_모든필드_패치_성공케이스(){

        String getUrl = "http://localhost:" + port + "/todos/list/full";

        HttpHeaders getHeaders = new HttpHeaders();
        getHeaders.setContentType(MediaType.APPLICATION_JSON);
        getHeaders.set("Authorization","Bearer "+tokenOne);
        HttpEntity<Object> getRequestEntity = new HttpEntity<>(null,getHeaders);

        ResponseEntity<List> getResponseEntity = restTemplate.exchange(getUrl, HttpMethod.GET,getRequestEntity,List.class);
        assertThat(getResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponseEntity.getBody().size()).isEqualTo(2);

        Map<String,Object> element = (Map<String,Object>) getResponseEntity.getBody().get(0);
        assertThat(element.get("createdAt")).isNotNull();
    }

    @Test
    public void 투두_리스트_모든필드_패치_스킵_성공케이스(){

        String getUrl = "http://localhost:" + port + "/todos/list/full?skip=1";

        HttpHeaders getHeaders = new HttpHeaders();
        getHeaders.setContentType(MediaType.APPLICATION_JSON);
        getHeaders.set("Authorization","Bearer "+tokenOne);
        HttpEntity<Object> getRequestEntity = new HttpEntity<>(null,getHeaders);

        ResponseEntity<List> getResponseEntity = restTemplate.exchange(getUrl, HttpMethod.GET,getRequestEntity,List.class);
        assertThat(getResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponseEntity.getBody().size()).isEqualTo(1);

        Map<String,Object> element = (Map<String,Object>) getResponseEntity.getBody().get(0);
        assertThat(element.get("createdAt")).isNotNull();
    }

    @Test
    public void 투두_리스트_모든필드_패치_리밋_성공케이스(){

        String getUrl = "http://localhost:" + port + "/todos/list/full?limit=1";

        HttpHeaders getHeaders = new HttpHeaders();
        getHeaders.setContentType(MediaType.APPLICATION_JSON);
        getHeaders.set("Authorization","Bearer "+tokenOne);
        HttpEntity<Object> getRequestEntity = new HttpEntity<>(null,getHeaders);

        ResponseEntity<List> getResponseEntity = restTemplate.exchange(getUrl, HttpMethod.GET,getRequestEntity,List.class);
        assertThat(getResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponseEntity.getBody().size()).isEqualTo(1);

        Map<String,Object> element = (Map<String,Object>) getResponseEntity.getBody().get(0);
        assertThat(element.get("createdAt")).isNotNull();
    }

    @Test
    public void 투두_싱글_일부필드_패치_성공케이스(){

        Todo todo = todoRepository.findAll().get(0);

        String getUrl = "http://localhost:" + port + "/todos/" + todo.getId() + "/partial";

        HttpHeaders getHeaders = new HttpHeaders();
        getHeaders.setContentType(MediaType.APPLICATION_JSON);
        getHeaders.set("Authorization","Bearer "+tokenOne);
        HttpEntity<Object> getRequestEntity = new HttpEntity<>(null,getHeaders);

        ResponseEntity<Map> getResponseEntity = restTemplate.exchange(getUrl, HttpMethod.GET,getRequestEntity,Map.class);
        assertThat(getResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        Map<String,Object> response = getResponseEntity.getBody();
        assertThat(response.get("id")).isNull();
        assertThat(response.get("completed")).isEqualTo(false);
        assertThat(response.get("name")).isEqualTo("todoOne");
        assertThat(response.get("createdAt")).isNull();
        assertThat(response.get("updatedAt")).isNull();
    }

    @Test
    public void 투두_리스트_일부필드_패치_성공케이스(){

        String getUrl = "http://localhost:" + port + "/todos/list/partial";

        HttpHeaders getHeaders = new HttpHeaders();
        getHeaders.setContentType(MediaType.APPLICATION_JSON);
        getHeaders.set("Authorization","Bearer "+tokenOne);
        HttpEntity<Object> getRequestEntity = new HttpEntity<>(null,getHeaders);

        ResponseEntity<List> getResponseEntity = restTemplate.exchange(getUrl, HttpMethod.GET,getRequestEntity,List.class);
        assertThat(getResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponseEntity.getBody().size()).isEqualTo(2);

        Map<String,Object> element = (Map<String,Object>) getResponseEntity.getBody().get(0);
        assertThat(element.get("createdAt")).isNull();
    }

    @Test
    public void 투두_리스트_일부필드_패치_리밋_성공케이스(){

        String getUrl = "http://localhost:" + port + "/todos/list/partial?limit=1";

        HttpHeaders getHeaders = new HttpHeaders();
        getHeaders.setContentType(MediaType.APPLICATION_JSON);
        getHeaders.set("Authorization","Bearer "+tokenOne);
        HttpEntity<Object> getRequestEntity = new HttpEntity<>(null,getHeaders);

        ResponseEntity<List> getResponseEntity = restTemplate.exchange(getUrl, HttpMethod.GET,getRequestEntity,List.class);
        assertThat(getResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponseEntity.getBody().size()).isEqualTo(1);

        Map<String,Object> element = (Map<String,Object>) getResponseEntity.getBody().get(0);
        assertThat(element.get("createdAt")).isNull();
    }

    @Test
    public void 투두_리스트_일부필드_패치_스킵_성공케이스(){

        String getUrl = "http://localhost:" + port + "/todos/list/partial?skip=1";

        HttpHeaders getHeaders = new HttpHeaders();
        getHeaders.setContentType(MediaType.APPLICATION_JSON);
        getHeaders.set("Authorization","Bearer "+tokenOne);
        HttpEntity<Object> getRequestEntity = new HttpEntity<>(null,getHeaders);

        ResponseEntity<List> getResponseEntity = restTemplate.exchange(getUrl, HttpMethod.GET,getRequestEntity,List.class);
        assertThat(getResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponseEntity.getBody().size()).isEqualTo(1);

        Map<String,Object> element = (Map<String,Object>) getResponseEntity.getBody().get(0);
        assertThat(element.get("createdAt")).isNull();
    }

    @After
    public void cleanup(){

        todoRepository.deleteAll();
        userRepository.deleteAll();
    }
}
