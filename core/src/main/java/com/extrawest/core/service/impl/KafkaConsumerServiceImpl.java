package com.extrawest.core.service.impl;

import com.extrawest.core.dto.TicksHistoryConsume;
import com.extrawest.core.model.Tick;
import com.extrawest.core.model.Ticker;
import com.extrawest.core.model.TicksHistory;
import com.extrawest.core.repository.TicksHistoryRepository;
import com.extrawest.core.repository.TicksRepository;
import com.extrawest.core.service.KafkaConsumerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class KafkaConsumerServiceImpl implements KafkaConsumerService {
    private final TicksHistoryRepository ticksHistoryRepository;
    private final TickerServiceImpl tickerService;
    private final TicksRepository ticksRepository;

    @KafkaListener(id = "ticks",
            topics = "ticks",
            containerFactory = "singleFactory")
    @Override
    public void consume(TicksHistoryConsume consume) {
        log.info("=> consumed {}", consume);
        saveTicksHistory(consume);
    }

    private void saveTicksHistory(TicksHistoryConsume consume) {

        TicksHistory ticksHistory = new TicksHistory();

        ticksHistory.setTimestamp( consume.getTimestamp() );
        ticksHistory.setSide( consume.getSide() );
        ticksHistory.setCurrentInterval( consume.getCurrentInterval() );

        Ticker ticker = tickerService.getTickerById(consume.getTickerId()).get();
        Tick tick = new Tick();
        tick.setSide(consume.getSide());
        tick.setCurrentInterval(consume.getCurrentInterval());
        ticker.getTicks().add(tick);
        ticksHistory.setTicker(ticker);
        ticksRepository.save(tick);
        tickerService.saveTicker(ticker);
        ticksHistoryRepository.save(ticksHistory);
    }
}
