package com.extrawest.core.controllers;

import com.extrawest.core.dto.AuthResponseDTO;
import com.extrawest.core.dto.LoginDTO;
import com.extrawest.core.dto.SignUpDTO;
import com.extrawest.core.service.AuthService;
import com.extrawest.core.utility.PathConstants;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@RestController
@RequestMapping(PathConstants.AUTH_PATH)
@AllArgsConstructor
public class AuthorizationController {

    private final AuthService authService;

    @PostMapping(PathConstants.SIGN_IN_PATH)
    public ResponseEntity<AuthResponseDTO> signInUser(@RequestBody LoginDTO loginDto){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(authService.signInUser(loginDto));
    }

    @PostMapping(PathConstants.SIGN_OUT_PATH)
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        authService.logout(request, response);
    }


    @PostMapping(PathConstants.SIGN_UP_PATH)
    public ResponseEntity<?> registerUser(@RequestBody SignUpDTO signUpDto){
        if (authService.isUserExistByEmail(signUpDto.getEmail())) {
            return new ResponseEntity<>("Email is already taken!", HttpStatus.BAD_REQUEST);
        }
        authService.signUpUser(signUpDto);
        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
    }

    @RequestMapping(path = "/test/IsUserAuthorize", method = RequestMethod.POST)
    public ResponseEntity<?> registerUser () {
        return new ResponseEntity<>("test", HttpStatus.OK);
    }
}
