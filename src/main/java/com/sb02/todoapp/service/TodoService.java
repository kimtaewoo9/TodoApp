package com.sb02.todoapp.service;

import com.sb02.todoapp.domain.Todo;
import com.sb02.todoapp.repository.TodoRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;

    public Long save(Todo todo){
        todoRepository.save(todo);

        return todo.getId();
    }

    public void update(Long id, String name, String description){
        todoRepository.update(id,name,description);
    }

    public void delete(Long id){
        todoRepository.delete(id);
    }

    public Todo findById(Long id){
        Todo todo = todoRepository.findById(id);

        return todo;
    }

    public List<Todo> findAll(){
        return todoRepository.findAll();
    }
}
