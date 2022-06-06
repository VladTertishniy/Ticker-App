package com.extrawest.core.repository;

import com.extrawest.core.model.Ticker;
import com.extrawest.core.model.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Collection;
import java.util.Optional;

public interface TickerRepository extends MongoRepository<Ticker, ObjectId> {
    Optional<Ticker> getTickerById(ObjectId id);
    Optional<Collection<Ticker>> getTickersByOwner(User user);
}
