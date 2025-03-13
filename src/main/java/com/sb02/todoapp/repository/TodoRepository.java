package com.sb02.todoapp.repository;

import com.sb02.todoapp.domain.Todo;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository
public class TodoRepository {

    private final Map<Long, Todo> store = new HashMap<>();

    private Long sequence = 0L;

    public void save(Todo todo){
        todo.setId(++sequence);
        store.put(sequence, todo);
    }

    public void update(Long id, String name, String description){
        Todo todo = findById(id);
        todo.setName(name);
        todo.setDescription(description);
        store.put(id, todo);
    }

    public Todo findById(Long id){
        return store.get(id);
    }

    public List<Todo> findAll(){
        return store.values().stream()
                .sorted(Comparator.comparing(Todo::getCreatedAt).reversed())
                .toList();
    }

    public void delete(Long id) {
        store.remove(id);
    }
}
