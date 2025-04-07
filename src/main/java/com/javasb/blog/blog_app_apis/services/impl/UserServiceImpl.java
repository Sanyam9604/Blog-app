package com.javasb.blog.blog_app_apis.services.impl;

import com.javasb.blog.blog_app_apis.entities.Role;
import com.javasb.blog.blog_app_apis.entities.User;
import com.javasb.blog.blog_app_apis.exceptions.ResourceNotFoundException;
import com.javasb.blog.blog_app_apis.payloads.UserDto;
import com.javasb.blog.blog_app_apis.payloads.UserResponse;
import com.javasb.blog.blog_app_apis.repositories.RoleRepository;
import com.javasb.blog.blog_app_apis.repositories.UserRepository;
import com.javasb.blog.blog_app_apis.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.javasb.blog.blog_app_apis.config.AppConstants.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDto registerNewUser(UserDto userDto) {

        User user = this.modelMapper.map(userDto, User.class);

        // encoded password
        String encodedPassword = this.passwordEncoder.encode(userDto.getPassword());
        user.setPassword(encodedPassword);

        // Fetch existing role
        Role role = this.roleRepository.findById(NORMAL_USER)
                .orElseThrow(() -> new ResourceNotFoundException("Role", "id", NORMAL_USER));

        user.getRoles().add(role);
        User newUser = userRepo.save(user);

        return modelMapper.map(newUser, UserDto.class);
    }

    @Override
    public UserDto createUser(UserDto userDto) {

        User user = dtoToUser(userDto);
        System.out.println(user);
        User savedUser = this.userRepo.save(user);
        return this.userToDto(savedUser);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Integer userId) {

        User userEntity = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", " Id ", userId));

        if(userDto.getEmail() != null) {
            userEntity.setEmail(userDto.getEmail());
        }
        if(userDto.getName() != null) {
            userEntity.setName(userDto.getName());
        }
        if(userDto.getAbout() != null) {
            userEntity.setAbout(userDto.getAbout());
        }
        if(userDto.getPassword() != null) {
            userEntity.setPassword(userDto.getPassword());
        }

        return userToDto(this.userRepo.save(userEntity));
    }

    @Override
    public UserDto getUserById(Integer userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", " Id ", userId));
        return userToDto(user);
    }

    @Override
    public UserResponse getAllUsers(Integer pageSize, Integer pageNumber, String sortBy, String direction) {

        Sort sort = direction.equalsIgnoreCase("ASC") ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable p = PageRequest.of(pageNumber, pageSize, sort);

        Page<User> users = this.userRepo.findAll(p);

        List<User> userList = users.getContent();

        List<UserDto> userDtoList = userList.stream().map(user -> this.userToDto(user)).collect(Collectors.toList());

        UserResponse userResponse = new UserResponse();
        userResponse.setUserDtoList(userDtoList);
        userResponse.setPageSize(pageSize);
        userResponse.setPageNumber(pageNumber);
        userResponse.setLastPage(users.isLast());
        userResponse.setTotalElements(users.getTotalElements());
        userResponse.setTotalPages(users.getTotalPages());

        return userResponse;
    }

    @Override
    public void deleteUser(Integer userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", " Id ", userId));
        this.userRepo.delete(user);
    }


    public User dtoToUser(UserDto userDto) {
        User user = this.modelMapper.map(userDto, User.class);

    /*    user.setId(userDto.getId());
          user.setName(userDto.getName());
          user.setEmail(userDto.getEmail());
          user.setPassword(userDto.getPassword());
          user.setAbout(userDto.getAbout()); */

        return user;
    }

    public UserDto userToDto(User user) {
        UserDto userDto = this.modelMapper.map(user, UserDto.class);
        return userDto;
    }
}
