package com.extrawest.core.service.impl;

import com.extrawest.core.model.TickStatistic;
import com.extrawest.core.model.TicksHistory;
import com.extrawest.core.repository.TickStatisticRepository;
import com.extrawest.core.repository.TicksHistoryRepository;
import com.extrawest.core.service.TicksHistoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TicksHistoryServiceImpl implements TicksHistoryService {
    private final TicksHistoryRepository ticksHistoryRepository;
    private final TickStatisticRepository tickStatisticRepository;

    @Override
    public void save(TicksHistory ticksHistory) {
        ticksHistoryRepository.save(ticksHistory);
    }

    @Override
    public void save(TickStatistic tickStatistic) {
        tickStatisticRepository.save(tickStatistic);
    }
}
