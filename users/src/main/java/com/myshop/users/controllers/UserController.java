package com.myshop.users.controllers;

import com.myshop.users.dtos.AuthenticationRequest;
import com.myshop.users.dtos.AuthenticationResponse;
import com.myshop.users.dtos.GetUserDto;
import com.myshop.users.dtos.UserDto;
import com.myshop.users.services.IUsersService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class UserController {

    private final IUsersService service;


    @PostMapping("/register")
    public GetUserDto addUser(
            @RequestBody UserDto request ) {
        return service.addUser(request);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    )  {
        return ResponseEntity.ok(service.authenticate(request));
    }

    @GetMapping("/users")
    public List<GetUserDto> getUsers( ) {
        return service.listUsers();
    }

    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response

    ) throws IOException {
        service.refreshToken(request,response);
    }





}
