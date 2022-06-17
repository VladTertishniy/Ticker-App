package com.extrawest.core.service;

import com.extrawest.core.model.TickStatistic;
import com.extrawest.core.model.TicksHistory;

public interface TicksHistoryService {

    void save(TicksHistory ticksHistory);

    void save(TickStatistic tickStatistic);
}
