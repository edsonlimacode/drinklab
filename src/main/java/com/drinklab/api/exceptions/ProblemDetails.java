package com.drinklab.api.exceptions;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProblemDetails {

    private Integer status;
    private String type;
    private String title;
    private String detail;
    private String userMessage;
    private String date;
    private List<Field> fields;

    @Getter
    @Setter
    @Builder
    public static class Field {
        private String field;
        private String message;
    }
}
