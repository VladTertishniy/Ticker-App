package com.extrawest.tickerService.service;

import com.extrawest.tickerService.dto.TickerRequestDTO;
import com.extrawest.tickerService.scheduler.TickScheduler;
import com.extrawest.tickerService.storage.ScheduleTasksStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

import java.util.concurrent.ScheduledFuture;

@Service
@RequiredArgsConstructor
@EnableScheduling
public class TickerService {
    private final TickScheduler tickScheduler;
    private final ThreadPoolTaskScheduler taskScheduler;

    public void start(TickerRequestDTO request) {
//        checkKey(secretKey);
        runTick(request);
    }

    public void stop(TickerRequestDTO request) {
//        checkKey(secretKey);
        stopTick(request);
    }


    private void runTick(TickerRequestDTO request) {
        ScheduledFuture<?> scheduledFuture = taskScheduler.scheduleWithFixedDelay(tickScheduler.doTask(request),
                request.getInterval());
        ScheduleTasksStorage.scheduleStorageMap.put(request.getTickerId(), scheduledFuture);
//        return new ResponseEntity<>("Ticker running was scheduled: " + scheduledFuture.toString(), HttpStatus.OK);
    }

    private void stopTick(TickerRequestDTO request) {
        String tickerId = request.getTickerId();
        ScheduledFuture<?> scheduledFuture = ScheduleTasksStorage.scheduleStorageMap.get(tickerId);
        scheduledFuture.cancel(true);
//        return new ResponseEntity<>("Ticker with id: " + tickerId + " was stopped", HttpStatus.OK);
    }
}
