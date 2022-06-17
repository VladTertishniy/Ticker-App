package com.extrawest.core.service.impl;

import com.extrawest.core.dto.auth.SignUpDTO;
import com.extrawest.core.dto.mapper.UserMapper;
import com.extrawest.core.model.User;
import com.extrawest.core.repository.UserRepository;
import com.extrawest.core.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final UserSequenceGeneratorService userSequenceGeneratorService;

    public UserServiceImpl(UserMapper userMapper, UserRepository userRepository, UserSequenceGeneratorService userSequenceGeneratorService) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.userSequenceGeneratorService = userSequenceGeneratorService;
    }

    @Override
    public User mapUser(SignUpDTO signUpDTO) {
        return userMapper.signUpDTOToUser(signUpDTO);
    }

    @Override
    public boolean isUserExistByEmail(String email) {
        return userRepository.existsUserByEmail(email);
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }

    @Override
    public Optional<User> getUserById(int id) {
        return userRepository.getUserById(id);
    }

    @Override
    public void saveUser (SignUpDTO signUpDto) {
        User user = userMapper.signUpDTOToUser(signUpDto);
        user.setId(userSequenceGeneratorService.getSequenceNumber(User.SEQUENCE));
        userRepository.save(user);
    }
}
