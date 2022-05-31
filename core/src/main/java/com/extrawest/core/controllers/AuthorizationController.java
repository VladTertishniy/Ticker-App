package com.extrawest.core.controllers;

import com.extrawest.core.dto.LoginDTO;
import com.extrawest.core.dto.SignUpDTO;
import com.extrawest.core.model.Role;
import com.extrawest.core.model.User;
import com.extrawest.core.repository.RoleRepository;
import com.extrawest.core.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthorizationController {

    private MongoRepository<User, ObjectId> mongoRepository;
    private AuthenticationManager authenticationManager;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;

    @PostMapping("/signin")
    public ResponseEntity<String> authenticateUser(@RequestBody LoginDTO loginDto){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getEmail(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new ResponseEntity<>("User signed-in successfully!.", HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpDTO signUpDto){

        if(userRepository.existsUserByEmail(signUpDto.getEmail())){
            return new ResponseEntity<>("Email is already taken!", HttpStatus.BAD_REQUEST);
        }

        // create user object
        User user = new User();
        user.setName(signUpDto.getName());
        user.setSurname(signUpDto.getSurname());
        user.setEmail(signUpDto.getEmail());
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));

        //тестовый говнокод
        Role role1 = new Role();
        role1.setName("ADMIN");
        roleRepository.save(role1);
        //конец тестового говна

        Role role = roleRepository.findByName("ADMIN").get();
        user.setRole(role);

        userRepository.save(user);

        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);

    }

    /*@RequestMapping(path = "/createUser", method = RequestMethod.POST)
    public void createUser () {
        User user = new User();
        user.setEmail("aaaa");
        user.setPassword("fffff");
        user.setTickers(null);
        mongoRepository.insert(user);
    }

    @RequestMapping(path = "/authorizeUser", method = RequestMethod.POST)
    public void registerUser () {

    }*/

}
