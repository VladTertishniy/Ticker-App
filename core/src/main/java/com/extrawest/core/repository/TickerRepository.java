package com.extrawest.core.repository;

import com.extrawest.core.model.Ticker;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Collection;
import java.util.Optional;

public interface TickerRepository extends MongoRepository<Ticker, ObjectId> {
    Optional<Ticker> getTickerById();
    Optional<Collection<Ticker>> getTickersByOwner();
}
