package com.myshop.users.controllers;

import com.myshop.users.dtos.ResUserDto;
import com.myshop.users.exceptions.wrapper.UserNotFoundException;
import com.myshop.users.services.IUsersService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static com.myshop.users.entities.Role.ADMIN;
import static com.myshop.users.entities.Role.MANAGER;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@ExtendWith(MockitoExtension.class)
@WebMvcTest(UserController.class)
class UserControllerTest {

    @Mock
    private IUsersService userService;

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        userService = Mockito.mock(IUsersService.class);
        userController = new UserController(userService);
    }



    @Test
    void addUser() {
    }

    @Test
    void authenticate() {
    }
    @Test
    void findById_whenUserExists_returnsUser() throws Exception {

        // Given
        Integer userId = 1;
        ResUserDto user1 = new ResUserDto(1, "John", "Doe" ,"zigeni", "<EMAIL>",ADMIN );

        // Mocking behavior for the service method
        when(userService.findUserById(userId)).thenReturn(user1);

        // When and Then
        mockMvc.perform(get("/api/v1/auth/user/{userId}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.property", equalTo("expectedValue"))); // Adjust based on your response structure

        // Verify that the service method is called with the correct parameter
        verify(userService).findUserById(userId);
    }




    @Test
    void findById_whenUserDoesNotExist_throwsException() {

        // Arrange
        int id = 2;
        when(userService.findUserById(id)).thenThrow(new UserNotFoundException());

        // Assert
        assertThrows(UserNotFoundException.class, () -> {
            userController.findById(String.valueOf(id));
        });

    }


    @Test
    void update() {
    }

    @Test
    void deleteById() {
    }

    @Test
    void getUsers() {
        ResUserDto user1 = new ResUserDto(1, "John", "Doe" ,"zigeni", "<EMAIL>",ADMIN );
        ResUserDto user2 = new ResUserDto(2, "Jane", "Doe" ,"zigeni", "<EMAIL>",MANAGER);

        List<ResUserDto> expectedUsers = Arrays.asList(user1, user2);

        when(userService.listUsers()).thenReturn(expectedUsers);

        List<ResUserDto> actualUsers = userController.getUsers();

        assertNotNull(actualUsers);
        assertEquals(expectedUsers, actualUsers);
    }

    @Test
    void refreshToken() {
    }
}