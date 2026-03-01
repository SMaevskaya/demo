package com.example.demo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::mapToUserDto)
                .collect(Collectors.toList());
    }

    UserDto getUserById(int id) {
        return userMapper.mapToUserDto(userRepository.findById(id).orElse(new User()));

    }

    void deleteUserById(int id) {
        userRepository.deleteById(id);
    }

    UserDto saveUser(UserDto userDto) {
        User user = userMapper.mapToUser(userDto);
        return userMapper.mapToUserDto(userRepository.save(user));
    }

    UserDto updateUser(UserDto userDto) {
        User user=userMapper.mapToUser(userDto);
        User user1=userRepository.findById(userDto.getId()).orElse(new User());
        user1.setName(userDto.getName());
        user1.setEmail(userDto.getEmail());
        user1.setAge(userDto.getAge());
        user1.setEmail(userDto.getEmail());
       return userMapper.mapToUserDto(userRepository.save(user1));
    }


}
