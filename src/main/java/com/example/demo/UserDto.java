package com.example.demo;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * DTO for {@link User}
 */
@Data
public class UserDto {
    int id;
    String name;
    String email;
    int age;

    @Autowired
    public UserDto(int id, String name, String email, int age) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
    }

    public UserDto() {

    }

}