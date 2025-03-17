package com.sb02.todoapp.controller;

import com.sb02.todoapp.domain.Todo;
import com.sb02.todoapp.dto.CreateTodoRequest;
import com.sb02.todoapp.dto.CreateTodoResponse;
import com.sb02.todoapp.dto.CreateTodosResponse;
import com.sb02.todoapp.dto.DeleteTodoResponse;
import com.sb02.todoapp.dto.UpdateTodoRequest;
import com.sb02.todoapp.dto.UpdateTodoResponse;
import com.sb02.todoapp.exception.TodoNotFound;
import com.sb02.todoapp.service.TodoService;
import jakarta.validation.constraints.NotEmpty;
import java.net.URI;
import java.util.List;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<CreateTodosResponse> getTodosPage(){
        List<Todo> todos = todoService.findAll();
        CreateTodosResponse response = new CreateTodosResponse(
            todoService.findAll().stream()
                .map(TodoRestController::convertTodoItemDto)
                .toList()
        );

        return ResponseEntity.ok(response);
    }

    // 보여줄 부분만 CreateTodoResponse 로 바꿈 .
    private static CreateTodoResponse convertTodoItemDto(Todo todo) {
        return new CreateTodoResponse(todo.getName(), todo.getDescription());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Todo> retrieveTodo(@PathVariable Long id){

        Todo todo = todoService.findById(id);
        if(todo == null){
            throw new TodoNotFound("todo not found");
        }

        return ResponseEntity.ok(todo);
    }

    @PostMapping
    public ResponseEntity<CreateTodoResponse> saveTodo(@Validated @RequestBody TodoForm form, BindingResult bindingResult){

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
        return ResponseEntity.created(location).build(); // build -> body없이 응답 생성함 .
        // location -> 새로 생성된 리소스의 위치를 전달함 .
    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdateTodoResponse> updateTodo(@PathVariable("id") Long id, @RequestBody UpdateTodoRequest request){

        todoService.update(id, request.getName(), request.getDescription());
        Todo todo = todoService.findById(id);
        if(todo == null){
            throw new TodoNotFound("todo not found");
        }
        UpdateTodoResponse response = new UpdateTodoResponse(todo.getName(), todo.getDescription());

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteTodoResponse> deleteTodo(@PathVariable("id") Long id){

        Todo todo = todoService.findById(id);
        if(todo == null){
            throw new TodoNotFound("todo not found");
        }

        todoService.delete(id);

        // 204 no content 에러 전송 .. -> 삭제 됐으니까 no content
        return ResponseEntity.noContent().build();
    }
}
