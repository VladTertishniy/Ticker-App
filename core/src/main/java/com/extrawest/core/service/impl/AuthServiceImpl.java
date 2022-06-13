package com.extrawest.core.service.impl;

import com.extrawest.core.dto.auth.AuthResponseDTO;
import com.extrawest.core.dto.auth.LoginDTO;
import com.extrawest.core.dto.auth.SignUpDTO;
import com.extrawest.core.model.User;
import com.extrawest.core.security.jwt.JWTTokenProvider;
import com.extrawest.core.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.file.attribute.UserPrincipalNotFoundException;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserServiceImpl userService;
    private final JWTTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request, response, null);
    }

    @Override
    public AuthResponseDTO signInUser(LoginDTO loginDTO) throws UserPrincipalNotFoundException {
        String userEmail = loginDTO.getEmail();
        if (userService.isUserExistByEmail(userEmail)) {
            User user = userService.getUserByEmail(userEmail).get();
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginDTO.getEmail(), loginDTO.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return new AuthResponseDTO(jwtTokenProvider.createToken(loginDTO.getEmail(), user.getRoles()));
        } else {
            throw new UserPrincipalNotFoundException(userEmail);
        }
    }

    @Override
    public void signUpUser(SignUpDTO signUpDTO) {
        String userEmail = signUpDTO.getEmail();
        userService.saveUser(signUpDTO);
    }

    @Override
    public boolean isUserExistByEmail(String email) {
        return userService.isUserExistByEmail(email);
    }
}
