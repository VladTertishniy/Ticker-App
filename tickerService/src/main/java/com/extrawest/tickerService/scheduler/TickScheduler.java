package com.extrawest.tickerService.scheduler;

import com.extrawest.tickerService.dto.TickerRequestDTO;
import com.extrawest.tickerService.dto.TickResponseDTO;
import com.extrawest.tickerService.dto.TickerTimeSideDTO;
import com.extrawest.tickerService.model.Side;
import com.extrawest.tickerService.service.KafkaProducerService;
import com.extrawest.tickerService.storage.TickStorage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
@AllArgsConstructor
@EnableScheduling
@Slf4j
public class TickScheduler {
    private final KafkaProducerService kafkaProducerService;

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
//            TickRequest currentTick = createTick(request);
//            TickResponse response = tickerMapper.toResponse(currentTick);
//            response.setCurrentInterval(getCurrentInterval(currentTick.getId()));
//            TickStorage.lastTickMap.put(request.getId(),
//                    new TickerTimeSideDto(request.getSide(), request.getTimestamp()));
//            kafkaProducerService.send(response);
//            TickSender sender = getContent(response);
//            webSocketService.sendToPublic(sender);
//            webSocketService.sendToUser(response.getUserEmail(), sender);
        };

    }

    private Duration getCurrentInterval(String id) {
        if (TickStorage.lastTickMap.get(id) == null) {
            return Duration.ZERO;
        }
        return Duration.of((new Date(System. currentTimeMillis()).getTime() - TickStorage.lastTickMap.get(id).getTimestamp().getTime()), ChronoUnit.MILLIS);
    }
}
