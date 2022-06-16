package com.extrawest.core.repository;

import com.extrawest.core.model.Ticker;
import com.extrawest.core.model.TicksHistory;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Data
@AllArgsConstructor
public class TickerStatisticRepository {

    private final ObjectMapper objectMapper;
    private final MongoTemplate mongoTemplate;

    public Map<Ticker, Integer> getTicksCountForPeriod(List<Ticker> tickers, Date from, Date to) {
        Criteria criteria = Criteria.where("ticker").in(tickers).and("timestamp").gt(from).lt(to);
        MatchOperation matchOperation = Aggregation.match(criteria);
        GroupOperation groupOperation = Aggregation.group("ticker").count().as("count");
        ProjectionOperation projectionOperation = Aggregation.project("count").and("ticker").previousOperation();
        Aggregation aggregation = Aggregation.newAggregation(matchOperation, groupOperation, projectionOperation);
        AggregationResults<Map> results = mongoTemplate.aggregate(aggregation, TicksHistory.class, Map.class);
        Map<Ticker, Integer> response = new HashMap<>();
        results.getMappedResults().forEach(
                r -> {
                    Ticker ticker = objectMapper.convertValue(r.get("ticker"), Ticker.class);
                    Integer count = (Integer) r.get("count");
                    response.put(ticker, count);
                }
        );
        return response;
    }

    public Map<Ticker, Double> getAverageTickTimeOut (List<Ticker> tickers) {

        Criteria criteria = Criteria.where("ticker").in(tickers);
        MatchOperation matchOperation = Aggregation.match(criteria);
        GroupOperation groupOperation = Aggregation.group("ticker").avg("currentInterval").as("avgCurrentInterval");
        ProjectionOperation projectionOperation = Aggregation.project("avgCurrentInterval").and("ticker").previousOperation();
        Aggregation aggregation = Aggregation.newAggregation(matchOperation, groupOperation, projectionOperation);
        AggregationResults<Map> results = mongoTemplate.aggregate(aggregation, TicksHistory.class, Map.class);
        Map<Ticker, Double> response = new HashMap<>();
        results.getMappedResults().forEach(
                r -> {
                    Ticker ticker = objectMapper.convertValue(r.get("ticker"), Ticker.class);
                    Double avgCurrentInterval = (Double) r.get("avgCurrentInterval");
                    response.put(ticker, avgCurrentInterval);
                }
        );
        return response;
    }

}
