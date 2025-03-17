package com.sb02.todoapp.controller;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TodoForm {

    @NotBlank(message = "항목 이름은 필수입니다.")
    private String name;
    private String description;
}
