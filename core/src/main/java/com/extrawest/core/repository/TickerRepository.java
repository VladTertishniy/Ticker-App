package com.extrawest.core.repository;

import com.extrawest.core.model.Ticker;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TickerRepository extends MongoRepository<Ticker, ObjectId> {
}
