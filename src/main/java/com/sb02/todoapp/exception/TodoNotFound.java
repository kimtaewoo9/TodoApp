package com.sb02.todoapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// 해당 id의 Todo가 없으면 .. NOT_FOUND 오류를 발생시킨다.
@ResponseStatus(HttpStatus.NOT_FOUND)
public class TodoNotFound extends RuntimeException {

    public TodoNotFound(String message) {
        super(message);
    }
}
