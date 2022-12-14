package com.extrawest.core.repository;

import com.extrawest.core.model.Ticker;
import com.extrawest.core.model.TicksHistory;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Collection;
import java.util.Optional;

public interface TicksHistoryRepository extends MongoRepository<TicksHistory, String> {
    Optional<Collection<TicksHistory>> getTicksHistoriesByTicker(Ticker ticker);
    Optional<TicksHistory> getTicksHistoryById(String id);
}
