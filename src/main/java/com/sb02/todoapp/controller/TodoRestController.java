package com.sb02.todoapp.controller;

import com.sb02.todoapp.domain.Todo;
import com.sb02.todoapp.service.TodoService;
import java.util.List;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    public CreateTodoResponse saveTodo(@RequestBody TodoForm form, BindingResult bindingResult){

        Todo todo  = new Todo();
        todo.setName(form.getName());
        todo.setDescription(form.getDescription());

        todoService.save(todo);

        return new CreateTodoResponse(todo.getName(), todo.getDescription());
    }

    @PutMapping("/{id}")
    public UpdateTodoResponse updateTodo(@PathVariable("id") Long id, @RequestBody EditForm form){

        todoService.update(id, form.getName(), form.getDescription());
        Todo todo = todoService.findById(id);
        return new UpdateTodoResponse(todo.getId(),todo.getName(), todo.getDescription());
    }

    @DeleteMapping("/{id}")
    public DeleteTodoResponse deleteTodo(@PathVariable("id") Long id){
        todoService.delete(id);
        return new DeleteTodoResponse(id);
    }

    @Data
    static class DeleteTodoResponse{
        private Long id;

        public DeleteTodoResponse(Long id){
            this.id = id;
        }
    }

    @Data
    static class UpdateTodoResponse{
        private Long id;
        private String newName;
        private String newDescription;

        public UpdateTodoResponse(Long id, String newName, String newDescription){
            this. id = id;
            this.newName = newName;
            this.newDescription = newDescription;
        }
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
