package com.schedule.simulator.bean;

public class SProcess implements Cloneable {
    public static final int PROCESS_READY = 1;
    public static final int PROCESS_IO_BLOCKING = 2;
    public static final int PROCESS_WORKING = 3;
    public static final int PROCESS_ENDED = 4;

    private int id;
    private int ioBurst;
    private int cpuBurst;
    private int cpuTotalBurstTime;
    private int cpuBurstDoneTime;
    private int currentIOBurst;
    private int currentCPUBurst;
    private int state = PROCESS_READY;


    public int getId() {
        return id;
    }

    public void setState(int state) {
        if (this.state == PROCESS_ENDED)
            throw new IllegalStateException("process ended");
        this.state = state;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIoBurst() {
        return ioBurst;
    }

    public void setIoBurst(int ioBurst) {
        this.ioBurst = ioBurst;
    }


    public int getCpuBurst() {
        return cpuBurst;
    }

    public void setCpuBurst(int cpuBurst) {
        this.cpuBurst = cpuBurst;
    }

    public int getCpuTotalBurstTime() {
        return cpuTotalBurstTime;
    }

    public void setCpuTotalBurstTime(int cpuTotalBurstTime) {
        this.cpuTotalBurstTime = cpuTotalBurstTime;
    }

    public int getCpuBurstDoneTime() {
        return cpuBurstDoneTime;
    }

    public void setCpuBurstDoneTime(int cpuBurstDoneTime) {
        this.cpuBurstDoneTime = cpuBurstDoneTime;
    }

    public int getCurrentIOBurst() {
        return currentIOBurst;
    }

    public void setCurrentIOBurst(int currentIOBurst) {
        this.currentIOBurst = currentIOBurst;
    }

    public int getCurrentCPUBurst() {
        return currentCPUBurst;
    }

    public void setCurrentCPUBurst(int currentCPUBurst) {
        this.currentCPUBurst = currentCPUBurst;
    }

    public int getState() {
        return state;
    }

    public void doPreemptive() {
        if (state == PROCESS_WORKING)
            this.setState(PROCESS_READY);
        else
            throw new IllegalStateException("This process isn't working ");
    }

    public void timerTick() {
        if (this.getState() == PROCESS_IO_BLOCKING) {
            currentIOBurst++;
            if (ioBurst == currentIOBurst) {
                this.setState(PROCESS_READY);
                currentIOBurst = 0;
            }
        }
        if (this.getState() == PROCESS_WORKING) {
            currentCPUBurst++;
            cpuBurstDoneTime++;
            if (cpuBurstDoneTime == this.cpuTotalBurstTime) {
                this.setState(PROCESS_ENDED);
                return;
            }
            if (currentCPUBurst == cpuBurst) {
                this.setState(PROCESS_IO_BLOCKING);
                currentCPUBurst = 0;
            }
        }
    }

    @Override
    public String toString() {
        return "SProcess{" +
                "id=" + id +
                ", ioBurst=" + ioBurst +
                ", cpuBurst=" + cpuBurst +
                ", cpuTotalBurstTime=" + cpuTotalBurstTime +
                ", cpuBurstDoneTime=" + cpuBurstDoneTime +
                ", currentIOBurst=" + currentIOBurst +
                ", currentCPUBurst=" + currentCPUBurst +
                ", state=" + state +
                '}';
    }

}
