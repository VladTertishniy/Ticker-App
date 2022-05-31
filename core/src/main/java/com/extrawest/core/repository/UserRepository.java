package com.extrawest.core.repository;

import com.extrawest.core.model.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
public interface UserRepository extends MongoRepository<User, ObjectId> {

    Optional<User> getUserByEmail(String email);
    Optional<User> getUserById(ObjectId id);
    boolean existsUserByEmail(String email);
    boolean existsUserById(ObjectId id);
}
