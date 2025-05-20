package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.User;
import org.example.model.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @Test
    public void testGetAllUsers() throws Exception {
        List<User> mockUsers = List.of(new User("Alice", 20), new User("Bob", 25));
        Mockito.when(userRepository.findAll()).thenReturn(mockUsers);

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].name").value("Alice"))
                .andExpect(jsonPath("$[1].age").value(25));
    }

//    @Test
//    void shouldReturnUsersButActuallyEmpty() throws Exception {
//        // 模擬一個空的 userRepository 回傳
//        Mockito.when(userRepository.findAll()).thenReturn(List.of());
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/api/users"))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Alice")); // 錯誤！因為是空陣列
//    }

    @Test
    void shouldReturnUsersAsObjectList() throws Exception {
        List<User> mockUsers = List.of(
                new User("Alice", 20),
                new User("Bob", 25)
        );
        Mockito.when(userRepository.findAll()).thenReturn(mockUsers);

        // 執行 GET 請求
        MvcResult result = mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andReturn();

        // 取得回傳 JSON 並轉為 List<User>
        String json = result.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        List<User> users = List.of(mapper.readValue(json, User[].class));

        // 一般 JUnit 斷言方式驗證內容
        Assertions.assertEquals(2, users.size());
        Assertions.assertEquals("Alice", users.get(0).getName());
        Assertions.assertEquals(25, users.get(1).getAge());
    }
}
