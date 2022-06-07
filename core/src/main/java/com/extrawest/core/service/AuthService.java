package com.extrawest.core.service;

import com.extrawest.core.dto.auth.AuthResponseDTO;
import com.extrawest.core.dto.auth.LoginDTO;
import com.extrawest.core.dto.auth.SignUpDTO;
import com.extrawest.core.model.User;
import com.extrawest.core.security.jwt.JWTTokenProvider;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
@AllArgsConstructor
public class AuthService {
    private final UserService userService;
    private final JWTTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    public void logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request, response, null);
    }

    public AuthResponseDTO signInUser(LoginDTO loginDTO) {
        String userEmail = loginDTO.getEmail();
        if (userService.isUserExistByEmail(userEmail)) {
            User user = userService.getUserByEmail(userEmail).get();
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginDTO.getEmail(), loginDTO.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return new AuthResponseDTO(jwtTokenProvider.createToken(loginDTO.getEmail(), user.getRoles()));
        } else {
            throw new RuntimeException(); //todo
        }
    }

    public void signUpUser(SignUpDTO signUpDTO) {
        String userEmail = signUpDTO.getEmail();
        if(userService.isUserExistByEmail(userEmail)){
            throw new RuntimeException(); //todo
        }
        userService.saveUser(signUpDTO);
    }

    public boolean isUserExistByEmail(String email) {
        return userService.isUserExistByEmail(email);
    }
}
