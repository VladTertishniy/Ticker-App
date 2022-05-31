package com.extrawest.core.repository;

import com.extrawest.core.model.Role;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RoleRepository extends MongoRepository<Role, ObjectId> {

    Optional<Role> findByName(String name);

}
