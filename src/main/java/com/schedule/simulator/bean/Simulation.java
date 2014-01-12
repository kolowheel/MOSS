package com.schedule.simulator.bean;

public class Simulation {
    private int durationOfSimulation;
    private int currentTimeOfSimulation;

    public void setDurationOfSimulation(int durationOfSimulation) {
        this.durationOfSimulation = durationOfSimulation;
    }


    public int getDurationOfSimulation() {
        return durationOfSimulation;
    }

    public int getCurrentTimeOfSimulation() {
        return currentTimeOfSimulation;
    }

    public boolean incTime() {
        currentTimeOfSimulation++;
        return true;
    }
}
