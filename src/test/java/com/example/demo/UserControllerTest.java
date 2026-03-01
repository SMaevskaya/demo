package com.example.demo;



import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.http.MediaType;
import tools.jackson.databind.ObjectMapper;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@WebMvcTest(UserController.class)
//@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetUserById() throws Exception {
        UserDto userDto = new UserDto(1, "Alise","alice@mail.ru",20);
        Mockito.when(userService.getUserById(1)).thenReturn(userDto);

        mockMvc.perform(get("/users/{id}", userDto.getId()))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(userDto.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(userDto.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(userDto.getEmail()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(userDto.getAge()));
    }

    @Test
    void testGetAllUsers() throws Exception {
        UserDto userDto1 = new UserDto(1, "Alise","alice@mail.ru",20);
        UserDto userDto2 = new UserDto(2, "Alik","alik@mail.ru",20);
        List<UserDto> userDtoList = new ArrayList<UserDto>();
        userDtoList.add(userDto1);
        userDtoList.add(userDto2);
        Mockito.when(userService.getAllUsers()).thenReturn(userDtoList);

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2));
    }



    @Test
    void deleteUserById() throws Exception {
        UserDto userDto = new UserDto(1, "Alise","alice@mail.ru",20);
        doNothing().when(userService).deleteUserById(userDto.getId());

        mockMvc.perform(delete("/users/{id}", userDto.getId()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }


    @Test
    void saveUser() throws Exception {
        UserDto userDto = new UserDto(1, "Alise","alice@mail.ru",20);
        Mockito.when(userService.saveUser(userDto)).thenReturn(userDto);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(userDto.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(userDto.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(userDto.getEmail()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(userDto.getAge()));
    }

    @Test
    void updateUser() throws Exception {
        UserDto userDto = new UserDto(1, "Alise","alice@mail.ru",20);
        Mockito.when(userService.updateUser(userDto)).thenReturn(userDto);

        mockMvc.perform(put("/users/{id}",userDto.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(userDto.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(userDto.getEmail()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(userDto.getAge()));
    }
}
