package com.extrawest.core.dto.auth.mapper;

import com.extrawest.core.dto.auth.LoginDTO;
import com.extrawest.core.dto.auth.SignUpDTO;
import com.extrawest.core.model.Role;
import com.extrawest.core.model.User;
import com.extrawest.core.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
public class UserMapper {

    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public UserMapper(@Autowired PasswordEncoder passwordEncoder, @Autowired RoleRepository roleRepository) {
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    public User userToModel(SignUpDTO signUpDto) {
        User user = new User();
        user.setName(signUpDto.getName());
        user.setSurname(signUpDto.getSurname());
        user.setEmail(signUpDto.getEmail());
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));

        Set<Role> roles = new HashSet<>();
        Optional<Role> role = roleRepository.findByName("ROLE_ADMIN");
        role.ifPresent(roles::add);
        user.setRoles(roles);
        return user;
    }

    public User userToModel(LoginDTO loginDTO) {
        return null;
    }
}
