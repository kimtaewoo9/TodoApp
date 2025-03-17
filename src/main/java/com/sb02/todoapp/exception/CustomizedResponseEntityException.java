package com.sb02.todoapp.exception;

import java.util.Date;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestController
@ControllerAdvice
public class CustomizedResponseEntityException extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class) // Exception.class가 발생되면 handlerExceptions 메서드를 실행하겠다.
    public final ResponseEntity<Object> handlerExceptions(Exception ex, WebRequest request){
        ExceptionResponse exceptionResponse
            = new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));

        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(TodoNotFound.class) // TodoNotFound가 발생 했을때 실행되는 예외 .
    public final ResponseEntity<Object> handlerTodoNotFound(Exception ex, WebRequest request){
        ExceptionResponse exceptionResponse
            = new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));

        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }
}
