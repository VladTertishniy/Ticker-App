package com.extrawest.core.service.impl;

import com.extrawest.core.dto.auth.SignUpDTO;
import com.extrawest.core.dto.mapper.UserMapper;
import com.extrawest.core.model.Role;
import com.extrawest.core.model.User;
import com.extrawest.core.repository.RoleRepository;
import com.extrawest.core.repository.UserRepository;
import com.extrawest.core.service.UserService;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Optional;
import java.util.Set;

@Service
@Scope(value = "singleton")
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final UserSequenceGeneratorService userSequenceGeneratorService;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserMapper userMapper, UserRepository userRepository, UserSequenceGeneratorService userSequenceGeneratorService, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.userSequenceGeneratorService = userSequenceGeneratorService;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User mapUser(SignUpDTO signUpDTO) {
        return userMapper.signUpDTOToUser(signUpDTO);
    }

    @Override
    public boolean isUserExistByEmail(String email) {
        return userRepository.existsUserByEmail(email);
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }

    @Override
    public Optional<User> getUserById(int id) {
        return userRepository.getUserById(id);
    }

    @Override
    public void saveUser (SignUpDTO signUpDto) {
        User user = userMapper.signUpDTOToUser(signUpDto);
        user.setId(userSequenceGeneratorService.getSequenceNumber(User.SEQUENCE));
        userRepository.save(user);
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
