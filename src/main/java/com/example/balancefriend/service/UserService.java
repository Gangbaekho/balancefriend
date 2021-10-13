package com.example.balancefriend.service;

import com.example.balancefriend.domain.user.User;
import com.example.balancefriend.domain.user.UserRepository;
import com.example.balancefriend.dto.UserCreateRequestDto;
import com.example.balancefriend.dto.UserCreateResponseDto;
import com.example.balancefriend.dto.UserSigninRequestDto;
import com.example.balancefriend.dto.UserSigninResponseDto;
import com.example.balancefriend.exception.CUserAlreadyExistException;
import com.example.balancefriend.payload.JwtAuthenticationResponse;
import com.example.balancefriend.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;

    @Transactional
    public UserCreateResponseDto signupUser(UserCreateRequestDto requestDto){

        Boolean exists = userRepository.existsByName(requestDto.getName());

        if(exists){
            throw new CUserAlreadyExistException("user already exists name : " + requestDto.getName());
        }

        User user = User.builder()
                .name(requestDto.getName())
                .age(requestDto.getAge())
                .build();

        userRepository.save(user);

        return new UserCreateResponseDto(user);
    }

    @Transactional
    public UserSigninResponseDto signinUser(UserSigninRequestDto requestDto){

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        requestDto.getName(),
                        requestDto.getName()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);

        UserSigninResponseDto responseDto = UserSigninResponseDto.builder()
                .accessToken(jwt)
                .build();

        return responseDto;
    }

}
