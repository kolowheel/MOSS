package com.schedule.simulator.scheduleAlghoritm;

import com.schedule.simulator.bean.SProcess;
import com.schedule.simulator.bean.Simulation;

import java.util.List;

public interface ScheduleAlgorithm {
    void doSchedule(List<SProcess> processes, Simulation simulation);

    void init(List<SProcess> processes, Simulation simulation);
}
