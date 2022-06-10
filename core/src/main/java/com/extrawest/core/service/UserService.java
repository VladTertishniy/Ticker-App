package com.extrawest.core.service;

import com.extrawest.core.dto.auth.SignUpDTO;
import com.extrawest.core.dto.mapper.UserMapper;
import com.extrawest.core.model.User;
import com.extrawest.core.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserMapper userMapper;
    private final UserRepository userRepository;

    public UserService(UserMapper userMapper, UserRepository userRepository) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
    }

    public User mapUser(SignUpDTO signUpDTO) {
        return userMapper.signUpDTOToUser(signUpDTO);
    }

    public boolean isUserExistByEmail(String email) {
        return userRepository.existsUserByEmail(email);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }

    public Optional<User> getUserById(String id) {
        return userRepository.getUserById(id);
    }

    public void saveUser (SignUpDTO signUpDto) {
        userRepository.save(userMapper.signUpDTOToUser(signUpDto));
    }
}
