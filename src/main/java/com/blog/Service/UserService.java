package com.blog.Service;

import java.util.List;

import com.blog.Dto.UserDto;

public interface UserService {

    public UserDto createUser(UserDto userDto);
    public UserDto updateUser(UserDto userDto , Integer userId);
    public UserDto getUserById(Integer userId);
    public List<UserDto> getAllUser();
    public void deleteUserId(Integer userId);

    //for Registering new User
    public UserDto RegisterNewUser(UserDto userDto);
}
