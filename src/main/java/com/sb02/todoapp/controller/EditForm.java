package com.sb02.todoapp.controller;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EditForm {

    @NotBlank(message = "항목 이름은 필수입니다.")
    private String name;
    private String description;
}
