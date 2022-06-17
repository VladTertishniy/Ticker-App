package com.extrawest.core.repository;

import com.extrawest.core.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> getUserByEmail(String email);
    Optional<User> getUserById(int id);
    boolean existsUserByEmail(String email);
    boolean existsUserById(int id);
}
