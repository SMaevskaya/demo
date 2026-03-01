package com.example.demo;

import org.springframework.stereotype.Service;

import java.sql.Date;

@Service
public class UserMapper {
    public UserDto mapToUserDto(User user){
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setAge(user.getAge());
        return dto;
    }

    public User mapToUser(UserDto dto){
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setAge(dto.getAge());
        user.setCreated_at(new Date(2025,12,11));
        return user;
    }
}
