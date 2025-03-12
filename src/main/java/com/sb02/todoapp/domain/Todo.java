package com.sb02.todoapp.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class Todo {

    private Long id;
    private String name;
    private String description;
}
