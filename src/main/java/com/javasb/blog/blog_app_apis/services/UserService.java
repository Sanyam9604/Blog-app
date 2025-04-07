package com.javasb.blog.blog_app_apis.services;

import com.javasb.blog.blog_app_apis.payloads.UserDto;
import com.javasb.blog.blog_app_apis.payloads.UserResponse;

import java.util.List;

public interface UserService {

    UserDto registerNewUser(UserDto user);

    UserDto createUser(UserDto user);

    UserDto updateUser(UserDto user, Integer userId);

    UserDto getUserById(Integer userId);

    UserResponse getAllUsers(Integer pageSize, Integer pageNumber, String sortBy, String direction);

    void deleteUser(Integer userId);
}
