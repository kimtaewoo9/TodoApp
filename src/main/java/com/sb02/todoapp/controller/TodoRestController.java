package com.sb02.todoapp.controller;

import com.sb02.todoapp.domain.Todo;
import com.sb02.todoapp.service.TodoService;
import java.util.List;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/todos")
@RequiredArgsConstructor
public class TodoRestController {

    private final TodoService todoService;

    @GetMapping
    public CreateTodosResponse getTodosPage(){
        List<Todo> todos = todoService.findAll();

        return new CreateTodosResponse(todos);
    }

    @PostMapping
    public CreateTodoResponse saveTodo(@RequestBody TodoForm form){

        Todo todo  = new Todo();
        todo.setName(form.getName());
        todo.setDescription(form.getDescription());

        todoService.save(todo);

        return new CreateTodoResponse(todo.getName(), todo.getDescription());
    }

    @Data
    static class CreateTodoResponse{
        private String name;
        private String description;

        public CreateTodoResponse(String name, String description){
            this.name = name;
            this.description = description;
        }
    }

    @Data
    static class CreateTodosResponse{
        private List<Todo> todos;

        public CreateTodosResponse(List<Todo> todos){
            this.todos = todos;
        }
    }
}
