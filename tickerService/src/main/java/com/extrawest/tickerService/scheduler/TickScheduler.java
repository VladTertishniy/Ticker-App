package com.extrawest.tickerService.scheduler;

import com.extrawest.tickerService.dto.TickerRequestDTO;
import com.extrawest.tickerService.dto.TickResponseDTO;
import com.extrawest.tickerService.dto.TickerTimeSideDTO;
import com.extrawest.tickerService.model.Side;
import com.extrawest.tickerService.service.impl.KafkaProducerServiceImpl;
import com.extrawest.tickerService.storage.TickStorage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@AllArgsConstructor
@EnableScheduling
@Slf4j
public class TickScheduler {
    private final KafkaProducerServiceImpl kafkaProducerService;

    public Runnable doTask(TickerRequestDTO tickerRequestDTO) {
        return () -> {
            TickerTimeSideDTO lastTickMap = TickStorage.lastTickMap.get(tickerRequestDTO.getTickerId());

            TickResponseDTO tickResponseDTO = new TickResponseDTO();
            tickResponseDTO.setTickerId(tickerRequestDTO.getTickerId());
            tickResponseDTO.setUserEmail(tickerRequestDTO.getUserEmail());
            tickResponseDTO.setCurrentInterval(getCurrentInterval(tickerRequestDTO.getTickerId()));
            tickResponseDTO.setTimestamp(new Date());
            if (lastTickMap != null) {
                if (lastTickMap.getSide() == null) {
                    tickResponseDTO.setSide(Side.LEFT);
                } else {
                    tickResponseDTO.setSide(Side.LEFT == lastTickMap.getSide() ? Side.RIGHT : Side.LEFT);
                }
            } else tickResponseDTO.setSide(Side.LEFT);

            TickStorage.lastTickMap.put(tickResponseDTO.getTickerId(),
                    new TickerTimeSideDTO(tickResponseDTO.getTimestamp(), tickResponseDTO.getSide()));
            log.info("=> consumed {}", tickResponseDTO);
            kafkaProducerService.send(tickResponseDTO);
        };

    }

    private double getCurrentInterval(String id) {
        if (TickStorage.lastTickMap.get(id) == null) {
            return 0;
        }

        return (double) (new Date().getTime() - TickStorage.lastTickMap.get(id).getTimestamp().getTime())
                / 1000L;
    }
}
