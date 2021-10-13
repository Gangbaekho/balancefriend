package com.example.balancefriend.controller;

import com.example.balancefriend.dto.UserCreateRequestDto;
import com.example.balancefriend.dto.UserCreateResponseDto;
import com.example.balancefriend.dto.UserSigninRequestDto;
import com.example.balancefriend.dto.UserSigninResponseDto;
import com.example.balancefriend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/users")
@RestController
public class UserController {

    private final UserService userService;

    @PostMapping("")
    public UserCreateResponseDto signupUser(@RequestBody UserCreateRequestDto requestDto){

        return userService.signupUser(requestDto);
    }

    @PostMapping("")
    public UserSigninResponseDto signinUser(@RequestBody UserSigninRequestDto requestDto){

        return userService.signinUser(requestDto);
    }

}
