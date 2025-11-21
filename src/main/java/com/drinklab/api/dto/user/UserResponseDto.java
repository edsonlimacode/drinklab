package com.drinklab.api.dto.user;


import com.drinklab.api.dto.group.GroupResponseDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDto {

    private Long id;

    private String name;

    @JsonProperty("nick_name")
    private String nickName;

    private String email;

    private Boolean active = true;

    private GroupResponseDto group;
}
