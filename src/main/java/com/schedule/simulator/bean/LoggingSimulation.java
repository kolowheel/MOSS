package com.schedule.simulator.bean;

import com.schedule.simulator.logger.Logger;

public class LoggingSimulation extends Simulation {
    private Logger logger;

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    public LoggingSimulation() {
    }

    @Override
    public boolean incTime() {
        if (logger != null)
            logger.writeSimulationEvent(this);

        super.incTime();
        return getCurrentTimeOfSimulation() != getDurationOfSimulation();
    }
}
