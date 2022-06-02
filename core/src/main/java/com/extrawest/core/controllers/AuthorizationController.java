package com.extrawest.core.controllers;

import com.extrawest.core.dto.auth.LoginDTO;
import com.extrawest.core.dto.auth.SignUpDTO;
import com.extrawest.core.repository.UserRepository;
import com.extrawest.core.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthorizationController {

    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private UserService userService;

    @PostMapping("/signin")
    public ResponseEntity<String> authenticateUser(@RequestBody LoginDTO loginDto){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getEmail(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new ResponseEntity<>("User signed-in successfully!.", HttpStatus.OK);
    }

    @PostMapping("/signout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request, response, null);
    }


    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpDTO signUpDto){
        if(userRepository.existsUserByEmail(signUpDto.getEmail())){
            return new ResponseEntity<>("Email is already taken!", HttpStatus.BAD_REQUEST);
        }
        userRepository.save(userService.mapUser(signUpDto));
        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
    }

    @RequestMapping(path = "/test/IsUserAuthorize", method = RequestMethod.POST)
    public ResponseEntity<?> registerUser () {
        return new ResponseEntity<>("test", HttpStatus.OK);
    }
}
