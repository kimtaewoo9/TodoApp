package com.sb02.todoapp.domain;

import java.time.ZonedDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class Todo {

    private Long id;
    private String name;
    private String description;
    private final ZonedDateTime createdAt;

    public Todo(){
        this.createdAt = ZonedDateTime.now();
    }
}
