package com.extrawest.core.dto.mapper;

import com.extrawest.core.dto.auth.SignUpDTO;
import com.extrawest.core.model.Role;
import com.extrawest.core.model.User;
import com.extrawest.core.repository.RoleRepository;
import com.extrawest.core.repository.UserRepository;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
@Data
public class UserMapper {

    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

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

    @PostConstruct
    public void init() {
        if (roleRepository.findByName("ROLE_ADMIN").isEmpty()) {
            Role role1 = new Role();
            role1.setName("ROLE_ADMIN");
            roleRepository.save(role1);
        }

        if (!userRepository.existsUserByEmail("admin@extrawest.com")) {
            User user = new User();
            user.setEmail("admin@extrawest.com");
            user.setPassword(passwordEncoder.encode("extrawest"));
            user.setName("Admin");
            user.setSurname("Admin");
            user.setRoles(Set.of(roleRepository.findByName("ROLE_ADMIN").orElseThrow()));
            userRepository.save(user);
        }
    }
}
