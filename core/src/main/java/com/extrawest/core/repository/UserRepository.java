package com.extrawest.core.repository;

import com.extrawest.core.model.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;
public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> getUserByEmail(String email);
    Optional<User> getUserById(String id);
    boolean existsUserByEmail(String email);
    boolean existsUserById(String id);
}
