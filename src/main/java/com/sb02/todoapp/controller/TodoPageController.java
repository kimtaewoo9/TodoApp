package com.sb02.todoapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TodoPageController {

    @GetMapping("/todos")
    public String getTodosPage() {
        return "todos";  // templates/todos.html 파일을 렌더링
    }
}
