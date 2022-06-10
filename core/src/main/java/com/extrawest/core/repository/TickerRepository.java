package com.extrawest.core.repository;

import com.extrawest.core.model.Status;
import com.extrawest.core.model.Ticker;
import com.extrawest.core.model.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Collection;
import java.util.Optional;

public interface TickerRepository extends MongoRepository<Ticker, String> {
    Optional<Ticker> getTickerById(String id);
    Optional<Collection<Ticker>> getTickersByOwner(User user);
    Collection<Ticker> getTickersByStatus (Status status);
    Collection<Ticker> findAllByStatus(Status status);
}
