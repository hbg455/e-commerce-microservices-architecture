package com.myshop.users.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myshop.users.dtos.UserDto;
import com.myshop.users.repositories.UserRepository;
import com.myshop.users.security.JwtService;
import com.myshop.users.services.servicesImpl.IUserServiceImpl;
import com.myshop.users.token.TokenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static com.myshop.users.entities.Role.ADMIN;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = UserController.class)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IUserServiceImpl userService;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private UserRepository userRepository;
    @MockBean
    private PasswordEncoder passwordEncoder;
    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private TokenRepository tokenRepository;

    @InjectMocks
    private UserController userController;

    @Autowired
    private ObjectMapper objectMapper;


    private UserDto userDto;

    @BeforeEach
    void setUp() {
        userDto = UserDto.builder()
                .username("Admin")
                .firstname("Admin")
                .lastname("Admin")
                .email("admin@mail.com")
                .password("password")
                .role(ADMIN)
                .build();
        MockitoAnnotations.openMocks(this);
        userService = new IUserServiceImpl(userRepository, passwordEncoder, authenticationManager, jwtService, tokenRepository);
        userController = new UserController(userService);
    }


    @Test
    void addUser() throws Exception {
        given(userService.addUser(userDto)).willAnswer(invocation -> invocation.getArgument(0));

        ResultActions resultActions = mockMvc.perform(post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)));

        resultActions.andExpect(status().isOk());
    }

    @Test
    void authenticate() {
    }

    @Test
    void findById_whenUserExists_returnsUser() throws Exception {


        mockMvc.perform(get("/api/users/{userId}", 123))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(123))
                .andExpect(jsonPath("$.name").value("John"));


    }


    @Test
    void findById_whenUserDoesNotExist_throwsException() {


    }


    @Test
    void update() {
    }

    @Test
    void deleteById() {
    }

    @Test
    void getUsers() {

    }

    @Test
    void refreshToken() {
    }
}