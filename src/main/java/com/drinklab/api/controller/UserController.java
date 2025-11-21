package com.drinklab.api.controller;


import com.drinklab.api.dto.user.UserRequestDto;
import com.drinklab.api.mapper.UserMapper;
import com.drinklab.domain.model.User;
import com.drinklab.domain.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @PostMapping
    public void create(@Valid @RequestBody UserRequestDto userRequestDto) {

        User user = this.userMapper.toEntity(userRequestDto);

        this.userService.create(user);
    }
}
