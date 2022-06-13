package com.extrawest.core.service;

import com.extrawest.core.dto.auth.AuthResponseDTO;
import com.extrawest.core.dto.auth.LoginDTO;
import com.extrawest.core.dto.auth.SignUpDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.file.attribute.UserPrincipalNotFoundException;

public interface AuthService {
    void logout(HttpServletRequest request, HttpServletResponse response);
    AuthResponseDTO signInUser(LoginDTO loginDTO) throws UserPrincipalNotFoundException;
    void signUpUser(SignUpDTO signUpDTO);
    boolean isUserExistByEmail(String email);
}
