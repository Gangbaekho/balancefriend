package com.example.balancefriend.service;

import com.example.balancefriend.domain.user.User;
import com.example.balancefriend.domain.user.UserRepository;
import com.example.balancefriend.dto.UserCreateRequestDto;
import com.example.balancefriend.dto.UserCreateResponseDto;
import com.example.balancefriend.exception.CUserAlreadyExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public UserCreateResponseDto registerUser(UserCreateRequestDto requestDto){

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

}
