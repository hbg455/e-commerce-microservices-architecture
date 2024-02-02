package com.myshop.users.services;

import com.myshop.users.dtos.AuthenticationRequest;
import com.myshop.users.dtos.AuthenticationResponse;
import com.myshop.users.dtos.GetUserDto;
import com.myshop.users.dtos.UserDto;
import com.myshop.users.entities.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface IUsersService {

    GetUserDto addUser(UserDto userDto);

    Optional<User> loadUserByUsername(String username);

    List<GetUserDto> listUsers();

    GetUserDto findUserById(Integer id);

    GetUserDto update(final UserDto userDto);

    GetUserDto update(final Integer userID, final UserDto userDto);

    void deleteById(final Integer productId);

    AuthenticationResponse authenticate(AuthenticationRequest request);

    void refreshToken(HttpServletRequest request,
                      HttpServletResponse response) throws IOException;


}
