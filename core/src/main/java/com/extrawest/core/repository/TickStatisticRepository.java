package com.extrawest.core.repository;

import com.extrawest.core.model.TickStatistic;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TickStatisticRepository extends MongoRepository<TickStatistic, String> {
}
