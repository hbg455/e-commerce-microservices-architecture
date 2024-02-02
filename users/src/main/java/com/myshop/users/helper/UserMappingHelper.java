package com.myshop.users.helper;

import com.myshop.users.dtos.GetUserDto;
import com.myshop.users.dtos.UserDto;
import com.myshop.users.entities.User;

public interface UserMappingHelper {

    public static GetUserDto mapToDto(final User user) {

        return GetUserDto.builder()
                .UserId(user.getUserId())
                .username(user.getUsername())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .email(user.getEmail())
                .role(user.getRole())
                .build();

    }

    public static User mapToUser(final UserDto userDto) {

        return User.builder()
                .username(userDto.username())
                .firstname(userDto.firstname())
                .lastname(userDto.lastname())
                .email(userDto.email())
                .role(userDto.role())
                .build();

    }
}
