package com.drinklab.api.mapper;

import com.drinklab.api.dto.user.UserRequestDto;
import com.drinklab.api.dto.user.UserResponseDto;
import com.drinklab.domain.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "groupId", target = "group.id")
    User toEntity(UserRequestDto userRequestDto);

    UserResponseDto toDto(User user);

    List<UserResponseDto> toListDto(List<User> users);
}
