package com.sb02.todoapp.controller;

import com.sb02.todoapp.domain.Todo;
import com.sb02.todoapp.exception.TodoNotFound;
import com.sb02.todoapp.service.TodoService;
import jakarta.validation.constraints.NotEmpty;
import java.net.URI;
import java.util.List;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.RequestEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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

    @GetMapping("/{id}")
    public Todo retrieveTodo(@PathVariable Long id){

        Todo todo = todoService.findById(id);
        if(todo == null){
            throw new TodoNotFound("todo not found");
        }

        return todo;
    }

    @PostMapping
    public CreateTodoResponse saveTodo(@Validated @RequestBody TodoForm form, BindingResult bindingResult){

        Todo todo  = new Todo();
        todo.setName(form.getName());
        todo.setDescription(form.getDescription());

        todoService.save(todo);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(todo.getId()) // 동적으로 생성된 리소스 아이디를 {id}에 삽입함 .
            .toUri();

        // 200번대 상태코드 -> 클라이언트 요청을 성공한 경우 .
        // 상태 코드를 201 create로 바꾸고 생성 위치(location) 반환 .
        return new CreateTodoResponse(todo.getName(), todo.getDescription());
    }

    @PutMapping("/{id}")
    public UpdateTodoResponse updateTodo(@PathVariable("id") Long id, @Validated @RequestBody EditForm form){

        todoService.update(id, form.getName(), form.getDescription());
        Todo todo = todoService.findById(id);
        return new UpdateTodoResponse(todo.getId(),todo.getName(), todo.getDescription());
    }

    @DeleteMapping("/{id}")
    public DeleteTodoResponse deleteTodo(@PathVariable("id") Long id){

        Todo todo = todoService.findById(id);
        if(todo == null){
            throw new TodoNotFound("todo not found");
        }

        todoService.delete(id);

        // 204 no content 에러 전송 .. -> 삭제 됐으니까 no content
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
        @NotEmpty
        private String newName;
        @NotEmpty
        private String newDescription;

        public UpdateTodoResponse(Long id, String newName, String newDescription){
            this. id = id;
            this.newName = newName;
            this.newDescription = newDescription;
        }
    }

    @Data
    static class CreateTodoResponse{

        @NotEmpty(message = "항목 이름을 입력하세요.")
        private String name;
        @NotEmpty(message = "항목 내용을 입력하세요.")
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
