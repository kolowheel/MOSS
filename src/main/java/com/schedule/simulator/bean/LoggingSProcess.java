package com.schedule.simulator.bean;

import com.schedule.simulator.logger.Logger;

public class LoggingSProcess extends SProcess {
    Logger logger;

    public LoggingSProcess() {
    }

    public void setLogger(Logger logger) {
        System.out.println(logger == null ? "sad" : "good");
        this.logger = logger;
    }

    @Override
    public void setState(int afterState) {
        int beforeChange = this.getState();
        super.setState(afterState);
        if (logger != null)
            logger.writeProcessEvent(beforeChange, afterState, this);
    }
}
