package com.extrawest.core.controllers;

import com.extrawest.core.dto.auth.AuthResponseDTO;
import com.extrawest.core.dto.auth.LoginDTO;
import com.extrawest.core.dto.auth.SignUpDTO;
import com.extrawest.core.service.AuthService;
import com.extrawest.core.utility.PathConstants;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.nio.file.attribute.UserPrincipalNotFoundException;


@RestController
@RequestMapping(PathConstants.AUTH_PATH)
@AllArgsConstructor
public class AuthorizationController {

    private final AuthService authService;

    @PostMapping(PathConstants.SIGN_IN_PATH)
    public ResponseEntity<?> signInUser(@RequestBody @Valid LoginDTO loginDto){
        try{
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(authService.signInUser(loginDto));
        } catch (UserPrincipalNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }

    @PostMapping(PathConstants.SIGN_OUT_PATH)
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        authService.logout(request, response);
    }


    @PostMapping(PathConstants.SIGN_UP_PATH)
    public ResponseEntity<?> registerUser(@RequestBody @Valid SignUpDTO signUpDto){
        if (authService.isUserExistByEmail(signUpDto.getEmail())) {
            return new ResponseEntity<>("Email is already taken!", HttpStatus.BAD_REQUEST);
        }
        authService.signUpUser(signUpDto);
        return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
    }

    @RequestMapping(path = "/test/IsUserAuthorize", method = RequestMethod.POST)
    public ResponseEntity<?> registerUser () {
        return new ResponseEntity<>("test", HttpStatus.OK);
    }
}
