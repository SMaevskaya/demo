package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/users")
public class UserController {


    private final UserService userService;
    @Autowired
    private KafkaMessagingService kafkaMessagingService;



    @Autowired
    public UserController(UserService userService){
        this.userService=userService;
    }



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
        UserDto  userDto1=userService.saveUser(userDto);
        kafkaMessagingService.sendMessage("save-user","save "+userDto1.getEmail());
        return userDto1;
    }

    @DeleteMapping("/{id}")
    void deleteUserById(@PathVariable int id) {
        UserDto userDto =userService.getUserById(id);
        userService.deleteUserById(id);
        kafkaMessagingService.sendMessage("delete-user","delete "+userDto.getEmail());
    }

    @PutMapping("/{id}")
    UserDto updateUser(@PathVariable int id,@RequestBody UserDto userDto) {
       return userService.updateUser(userDto);
    }

}
