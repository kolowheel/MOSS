package com.schedule.simulator.logger;

import com.schedule.simulator.bean.SProcess;
import com.schedule.simulator.bean.Simulation;
import com.schedule.simulator.util.ProcessUtil;

import java.util.List;


public class DefaultLoggerImpl implements Logger {

    @Override
    public void beginOfSimulation(Simulation simulation, List<SProcess> processList) {

    }

    @Override
    public void writeProcessEvent(int beforeState, int afterState, SProcess process) {
        System.out.println(process.getId() + " change from " + ProcessUtil.stringValueOfState(beforeState) + " on " + ProcessUtil.stringValueOfState(afterState));
    }

    @Override
    public void writeSimulationEvent(Simulation simulation) {

    }

    @Override
    public void endOfSimulation(Simulation simulation, List<SProcess> processList) {
        for (SProcess process : processList) {
            System.out.println("Process id :" + process.getId() + ", cpu burst done time " + process.getCpuBurstDoneTime() + "" +
                    ", total cpu burst time " + process.getCpuTotalBurstTime() + ", state " + ProcessUtil.stringValueOfState(process.getState()));
        }
    }
}
