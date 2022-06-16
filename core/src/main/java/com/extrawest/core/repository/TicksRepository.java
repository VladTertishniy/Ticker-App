package com.extrawest.core.repository;

import com.extrawest.core.model.Tick;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TicksRepository extends MongoRepository<Tick, String> {
}
