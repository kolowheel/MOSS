package com.schedule.simulator.logger;

import com.schedule.simulator.bean.SProcess;
import com.schedule.simulator.bean.Simulation;

import java.util.List;


public interface Logger {
    void beginOfSimulation(Simulation simulation, List<SProcess> processList);

    void writeProcessEvent(int beforeState, int afterState, SProcess process);

    void writeSimulationEvent(Simulation simulation);

    void endOfSimulation(Simulation simulation, List<SProcess> processList);
}