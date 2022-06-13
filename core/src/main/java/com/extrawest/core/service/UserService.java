package com.extrawest.core.service;

import com.extrawest.core.dto.auth.SignUpDTO;
import com.extrawest.core.model.User;

import java.util.Optional;

public interface UserService {
    User mapUser(SignUpDTO signUpDTO);
    boolean isUserExistByEmail(String email);
    Optional<User> getUserByEmail(String email);
    Optional<User> getUserById(String id);
    void saveUser (SignUpDTO signUpDto);
}
