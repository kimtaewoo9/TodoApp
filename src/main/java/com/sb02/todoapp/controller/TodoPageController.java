package com.sb02.todoapp.controller;

import com.sb02.todoapp.domain.Todo;
import com.sb02.todoapp.service.TodoService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class TodoPageController {

    private final TodoService todoService;
    //
    @GetMapping("/todos")
    public String getTodosPage(Model model) {
        List<Todo> todoList = todoService.findAll();
        model.addAttribute("todoList", todoList);
        return "todos";  // templates/todos.html 파일을 렌더링
    }


//    @PostMapping("/todos")
//    public String create(@Validated @ModelAttribute TodoForm form, BindingResult bindingResult){
//        if(bindingResult.hasErrors()){
//            return "todos";
//        }
//
//        Todo todo = new Todo();
//        todo.setName(form.getName());
//        todo.setDescription(form.getDescription());
//
//        todoService.save(todo);
//
//        return "redirect:/";
//    }
}
