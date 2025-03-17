package com.sb02.todoapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateTodoResponse {
    private String name;
    private String description;
}
