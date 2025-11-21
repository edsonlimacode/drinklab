package com.drinklab.api.controller;


import com.drinklab.api.dto.user.UserRequestDto;
import com.drinklab.api.dto.user.UserResponseDto;
import com.drinklab.api.mapper.UserMapper;
import com.drinklab.domain.model.User;
import com.drinklab.domain.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@Valid @RequestBody UserRequestDto userRequestDto) {

        User user = this.userMapper.toEntity(userRequestDto);

        this.userService.create(user);
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> listAll() {

        List<User> users = this.userService.findAll();

        List<UserResponseDto> userListDto = this.userMapper.toListDto(users);

        return ResponseEntity.ok().body(userListDto);

    }
}
