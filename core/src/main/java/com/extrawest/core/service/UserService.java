package com.extrawest.core.service;

import com.extrawest.core.dto.auth.SignUpDTO;
import com.extrawest.core.dto.auth.mapper.UserMapper;
import com.extrawest.core.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserMapper userMapper;

    public UserService(@Autowired UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public User mapUser(SignUpDTO signUpDTO) {
        return userMapper.userToModel(signUpDTO);
    }
}
