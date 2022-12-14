package com.extrawest.core.repository;

import com.extrawest.core.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import java.util.*;

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
                });
        return response;
    }

    public Map<Status, Integer> getStartStopTickers (String userEmail) {
        Criteria criteria = Criteria.where("userEmail").in(userEmail).and("status").in("ACTIVE", "PAUSED");
        MatchOperation matchOperation = Aggregation.match(criteria);
        GroupOperation groupOperation = Aggregation.group("status").count().as("statusCount");
        ProjectionOperation projectionOperation = Aggregation.project("statusCount").and("status").previousOperation();
        Aggregation aggregation = Aggregation.newAggregation(matchOperation, groupOperation, projectionOperation);
        AggregationResults<Map> resultList = mongoTemplate.aggregate(aggregation, TickStatistic.class, Map.class);
        Map<Status, Integer> resultMap = new EnumMap<>(Status.class);
        resultList.getMappedResults().forEach(
                r -> {
                    Status status = Status.valueOf((String) r.get("status"));
                    int statusCount = (Integer) r.get("statusCount");
                    resultMap.put(status, statusCount);
                });
        return resultMap;
    }

    public Map<Status, Integer> getActivePausedTickers(User user) {

        Criteria criteria = Criteria.where("owner").in(user).and("status").in("ACTIVE", "PAUSED");
        MatchOperation matchStage = Aggregation.match(criteria);
        GroupOperation group = Aggregation.group("status").count().as("statusCount");
        ProjectionOperation projectionOperation = Aggregation.project("statusCount").and("status").previousOperation();
        Aggregation aggregation = Aggregation.newAggregation(matchStage, group, projectionOperation);
        AggregationResults<Map> result = mongoTemplate.aggregate(aggregation, Ticker.class, Map.class);
        Map<Status, Integer> resultMap = new EnumMap<>(Status.class);
        result.getMappedResults().forEach(
                r -> {
                    Status status = Status.valueOf((String) r.get("status"));
                    Integer statusCount = (Integer) r.get("statusCount");
                    resultMap.put(status, statusCount);
                });
        return resultMap;
    }

    public Map<Ticker, Integer> getLostTicks(List<Ticker> tickers) {
        Criteria criteria = Criteria.where("ticker").in(tickers);
        MatchOperation matchStage = Aggregation.match(criteria);
        GroupOperation group = Aggregation.group("ticker", "side").count().as("sideCount");
        ProjectionOperation projectionOperation = Aggregation.project("sideCount").and("ticker").previousOperation();
        Aggregation aggregation = Aggregation.newAggregation(matchStage, group, projectionOperation);
        AggregationResults<Map> resultList = mongoTemplate.aggregate(aggregation, TicksHistory.class, Map.class);
        Map<Ticker, Integer> resultMap = new HashMap<>();
        resultList.getMappedResults().forEach(
                r -> {
                    Map tickerMap = (Map) r.get("ticker");
                    Ticker ticker = objectMapper.convertValue(tickerMap.get("ticker"), Ticker.class);
                    Integer sideCount = (Integer) r.get("sideCount");
                    resultMap.computeIfPresent(ticker, (key, value) -> Math.abs(value - sideCount));
                    resultMap.putIfAbsent(ticker, sideCount);
                });
        return resultMap;
    }

}
