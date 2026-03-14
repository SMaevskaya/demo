package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

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
    List<EntityModel<UserDto>> getAllUsers(){
        List<EntityModel<UserDto>> collect = userService.getAllUsers()
                .stream()
                .map(UserController::toHateoasEntityModel)
                .collect(Collectors.toList());
        return collect;
    }



    @GetMapping("/{id}")
    EntityModel<UserDto> getUserById(@PathVariable int id){
        return toHateoasEntityModel(userService.getUserById(id));
    }

    @PostMapping()
    EntityModel<UserDto> saveUser(@RequestBody UserDto userDto) {
        UserDto  userDto1=userService.saveUser(userDto);
        kafkaMessagingService.sendMessage("save-user","save "+userDto1.getEmail());
        return toHateoasEntityModel(userDto1);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteUserById(@PathVariable int id) {
        UserDto userDto =userService.getUserById(id);
        userService.deleteUserById(id);
        kafkaMessagingService.sendMessage("delete-user","delete "+userDto.getEmail());
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    EntityModel<UserDto> updateUser(@PathVariable int id,@RequestBody UserDto userDto) {
       return toHateoasEntityModel(userService.updateUser(userDto));
    }

    private static EntityModel<UserDto> toHateoasEntityModel(UserDto userDto) {
        Link selfLink = linkTo(methodOn(UserController.class).getUserById(userDto.getId())).withSelfRel();
        Link allUsersLink = linkTo(methodOn(UserController.class).getAllUsers()).withRel("all-users");
        return EntityModel.of(userDto, selfLink, allUsersLink);
    }

}
