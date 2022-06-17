package com.extrawest.core.repository;

import com.extrawest.core.model.Status;
import com.extrawest.core.model.Ticker;
import com.extrawest.core.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface TickerRepository extends MongoRepository<Ticker, String> {
    Optional<Ticker> getTickerById(int id);
    Optional<Collection<Ticker>> getTickersByOwner(User user);
    List<Ticker> getTickersByStatus (Status status);
    List<Ticker> findAllByStatus(Status status);
    List<Ticker> findAllByOwner(User owner);
}
