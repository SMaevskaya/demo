package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    //private final UserRepository userRepository;
    //private final UserMapper userMapper;
    private final UserService userService;

//    @Autowired
//    public UserController(UserRepository userRepository, UserMapper userMapper) {
//        this.userRepository = userRepository;
//        this.userMapper = userMapper;
//    }

    @Autowired
    public UserController(UserService userService){
        this.userService=userService;
    }



//    @GetMapping
//    List<UserDto> getAllUsers() {
//        return userRepository.findAll().stream()
//                .map(userMapper::mapToUserDto)
//                .collect(Collectors.toList());
//    }

    @GetMapping
    List<UserDto> getAllUsers(){
        return userService.getAllUsers();
    }



    @GetMapping("/{id}")
    UserDto getUserById(@PathVariable int id){
        return userService.getUserById(id);
    }

    @PostMapping()
    UserDto saveUser(@RequestBody UserDto userDto) {
        return userService.saveUser(userDto);
    }

    @DeleteMapping("/{id}")
    void deleteUserById(@PathVariable int id) {
        userService.deleteUserById(id);
    }

    @PutMapping("/{id}")
    UserDto updateUser(@PathVariable int id,@RequestBody UserDto userDto) {
       return userService.updateUser(userDto);
    }

}
