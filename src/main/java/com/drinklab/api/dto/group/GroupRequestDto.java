package com.drinklab.api.dto.group;


import jakarta.persistence.Id;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GroupRequestDto {

    private Long id;
    private String name;

}
