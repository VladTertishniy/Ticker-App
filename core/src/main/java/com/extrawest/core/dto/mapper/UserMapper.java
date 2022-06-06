package com.extrawest.core.dto.mapper;

import com.extrawest.core.dto.SignUpDTO;
import com.extrawest.core.model.Role;
import com.extrawest.core.model.User;
import com.extrawest.core.repository.RoleRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
public class UserMapper {

    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public UserMapper(PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    public User signUpDTOToUser(SignUpDTO signUpDTO) {
        User user = new User();
        user.setName(signUpDTO.getName());
        user.setSurname(signUpDTO.getSurname());
        user.setEmail(signUpDTO.getEmail());
        user.setPassword(passwordEncoder.encode(signUpDTO.getPassword()));

        Set<Role> roles = new HashSet<>();
        Optional<Role> role = roleRepository.findByName("ROLE_ADMIN");
        role.ifPresent(roles::add);
        user.setRoles(roles);
        return user;
    }
}
