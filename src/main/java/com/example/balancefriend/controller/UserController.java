package com.example.balancefriend.controller;

import com.example.balancefriend.dto.UserCreateRequestDto;
import com.example.balancefriend.dto.UserCreateResponseDto;
import com.example.balancefriend.dto.UserSigninRequestDto;
import com.example.balancefriend.dto.UserSigninResponseDto;
import com.example.balancefriend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/users")
@RestController
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public UserCreateResponseDto signupUser(@RequestBody UserCreateRequestDto requestDto){

        return userService.signupUser(requestDto);
    }

    @PostMapping("/signin")
    public UserSigninResponseDto signinUser(@RequestBody UserSigninRequestDto requestDto){

        return userService.signinUser(requestDto);
    }
}
