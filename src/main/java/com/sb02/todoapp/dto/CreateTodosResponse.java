package com.sb02.todoapp.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateTodosResponse {
    List<CreateTodoResponse> todos;
}
