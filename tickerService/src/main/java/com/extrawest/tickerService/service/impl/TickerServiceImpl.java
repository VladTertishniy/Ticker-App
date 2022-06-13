package com.extrawest.tickerService.service.impl;

import com.extrawest.tickerService.dto.TickerRequestDTO;
import com.extrawest.tickerService.scheduler.TickScheduler;
import com.extrawest.tickerService.service.TickerService;
import com.extrawest.tickerService.storage.ScheduleTasksStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

import java.util.concurrent.ScheduledFuture;

@Service
@RequiredArgsConstructor
@EnableScheduling
public class TickerServiceImpl implements TickerService {
    private final TickScheduler tickScheduler;
    private final ThreadPoolTaskScheduler taskScheduler;

    @Override
    public void start(TickerRequestDTO request) {
//        checkKey(secretKey);
        runTick(request);
    }

    @Override
    public void stop(TickerRequestDTO request) {
//        checkKey(secretKey);
        stopTick(request);
    }


    private void runTick(TickerRequestDTO request) {
        ScheduledFuture<?> scheduledFuture = taskScheduler.scheduleWithFixedDelay(tickScheduler.doTask(request),
                request.getInterval());
        ScheduleTasksStorage.scheduleStorageMap.put(request.getTickerId(), scheduledFuture);
    }

    private void stopTick(TickerRequestDTO request) {
        String tickerId = request.getTickerId();
        ScheduledFuture<?> scheduledFuture = ScheduleTasksStorage.scheduleStorageMap.get(tickerId);
        scheduledFuture.cancel(true);
    }
}
