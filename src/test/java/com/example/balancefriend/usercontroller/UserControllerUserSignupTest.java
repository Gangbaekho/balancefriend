package com.example.balancefriend.usercontroller;

import com.example.balancefriend.domain.user.User;
import com.example.balancefriend.domain.user.UserRepository;
import com.example.balancefriend.dto.UserCreateRequestDto;
import com.example.balancefriend.dto.UserCreateResponseDto;
import com.example.balancefriend.security.JwtTokenProvider;
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
public class UserControllerUserSignupTest {

    @LocalServerPort
    private int port;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenProvider tokenProvider;

    private String token;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setUp(){

        // already exists user
        String name = "exists";
        String password = "tester";
        int age = 15;

        User user = User.builder()
                .name(name)
                .password(password)
                .age(age)
                .build();

        userRepository.save(user);
    }

    @Test
    public void 유저_생성_성공케이스(){

        String name = "helloworld";
        String password = "tester";
        int age = 13;

        UserCreateRequestDto requestDto = UserCreateRequestDto.builder()
                .name(name)
                .password(password)
                .age(age)
                .build();

        String postUrl = "http://localhost:" + port + "/users/signup";

        HttpHeaders postHeaders = new HttpHeaders();
        postHeaders.setContentType(MediaType.APPLICATION_JSON);
        postHeaders.set("Authorization","Bearer "+token);
        HttpEntity<UserCreateRequestDto> postRequestEntity = new HttpEntity<>(requestDto,postHeaders);

        ResponseEntity<UserCreateResponseDto> postResponseEntity = restTemplate.exchange(postUrl, HttpMethod.POST,postRequestEntity,UserCreateResponseDto.class);
        assertThat(postResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        List<User> users = userRepository.findAll();
        assertThat(users.size()).isEqualTo(2);
    }

    @Test
    public void 유저_중복생성_경우(){

        String name = "exists";
        String password = "tester";
        int age = 13;

        UserCreateRequestDto requestDto = UserCreateRequestDto.builder()
                .name(name)
                .password(password)
                .age(age)
                .build();

        String postUrl = "http://localhost:" + port + "/users/signup";

        HttpHeaders postHeaders = new HttpHeaders();
        postHeaders.setContentType(MediaType.APPLICATION_JSON);
        postHeaders.set("Authorization","Bearer "+token);
        HttpEntity<UserCreateRequestDto> postRequestEntity = new HttpEntity<>(requestDto,postHeaders);

        ResponseEntity<UserCreateResponseDto> postResponseEntity = restTemplate.exchange(postUrl, HttpMethod.POST,postRequestEntity,UserCreateResponseDto.class);
        assertThat(postResponseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        List<User> users = userRepository.findAll();
        assertThat(users.size()).isEqualTo(1);
    }

    @Test
    public void 유저_나이_오류(){

        String name = "exists";
        String password = "tester";
        int age = 160;

        UserCreateRequestDto requestDto = UserCreateRequestDto.builder()
                .name(name)
                .password(password)
                .age(age)
                .build();

        String postUrl = "http://localhost:" + port + "/users/signup";

        HttpHeaders postHeaders = new HttpHeaders();
        postHeaders.setContentType(MediaType.APPLICATION_JSON);
        postHeaders.set("Authorization","Bearer "+token);
        HttpEntity<UserCreateRequestDto> postRequestEntity = new HttpEntity<>(requestDto,postHeaders);

        ResponseEntity<UserCreateResponseDto> postResponseEntity = restTemplate.exchange(postUrl, HttpMethod.POST,postRequestEntity,UserCreateResponseDto.class);
        assertThat(postResponseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        List<User> users = userRepository.findAll();
        assertThat(users.size()).isEqualTo(1);
    }


    @After
    public void cleanup(){

        userRepository.deleteAll();
    }
}
