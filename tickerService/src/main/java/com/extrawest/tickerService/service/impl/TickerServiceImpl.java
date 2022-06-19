package com.extrawest.tickerService.service.impl;

import com.extrawest.tickerService.dto.TickerRequestDTO;
import com.extrawest.tickerService.scheduler.TickScheduler;
import com.extrawest.tickerService.service.TickerService;
import com.extrawest.tickerService.storage.TasksStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.ScheduledFuture;

@Service
@RequiredArgsConstructor
@EnableScheduling
public class TickerServiceImpl implements TickerService {
    @Value("${remote.secret.key}")
    private String remoteKey;
    private final TickScheduler tickScheduler;
    private final ThreadPoolTaskScheduler taskScheduler;

    @Override
    public void start(TickerRequestDTO request, String secretKey) {
        checkKey(secretKey);
        runTick(request);
    }

    @Override
    public void stop(TickerRequestDTO request, String secretKey) {
        checkKey(secretKey);
        stopTick(request);
    }

    private void checkKey(String secretKey) {
        if (!remoteKey.equals(secretKey)) {
            throw new AccessDeniedException("Secret keys don`t match");
        }
    }

    private void runTick(TickerRequestDTO request) {
        ScheduledFuture<?> scheduledFuture = taskScheduler.scheduleWithFixedDelay(tickScheduler.doTask(request),
                Duration.of((long) request.getInterval(), ChronoUnit.MILLIS));
        TasksStorage.scheduleStorageMap.put(request.getTickerId(), scheduledFuture);
    }

    private void stopTick(TickerRequestDTO request) {
        int tickerId = request.getTickerId();
        ScheduledFuture<?> scheduledFuture = TasksStorage.scheduleStorageMap.get(tickerId);
        scheduledFuture.cancel(true);
    }
}
