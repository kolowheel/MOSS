package com.schedule.simulator;

import com.schedule.simulator.bean.LoggingSProcess;
import com.schedule.simulator.bean.LoggingSimulation;
import com.schedule.simulator.bean.SProcess;
import com.schedule.simulator.bean.Simulation;
import com.schedule.simulator.logger.DefaultLoggerImpl;
import com.schedule.simulator.logger.Logger;
import com.schedule.simulator.parse.DOMParser;
import com.schedule.simulator.scheduleAlghoritm.ScheduleAlgorithm;
import com.schedule.simulator.util.ProcessUtil;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;

public class Scheduler {
    List<SProcess> processes;
    Simulation simulation;
    ScheduleAlgorithm algorithm;
    Logger logger;

    public void loadConfiguration(String xmlFileLocation, ScheduleAlgorithm algorithm, Logger logger) throws IOException, SAXException, ParserConfigurationException {
        this.algorithm = algorithm;
        if (logger == null)
            this.logger = new DefaultLoggerImpl();
        else
            this.logger = logger;
        DOMParser domParser = new DOMParser();
        domParser.parse(xmlFileLocation);
        processes = domParser.getProcesses();
        simulation = domParser.getSimulation();
        for (SProcess process : processes) {
            ((LoggingSProcess) process).setLogger(this.logger);
        }
        ((LoggingSimulation) simulation).setLogger(this.logger);
    }

    public void schedule() {
        algorithm.init(processes, simulation);
        logger.beginOfSimulation(simulation, processes);
        while (simulation.getCurrentTimeOfSimulation() < simulation.getDurationOfSimulation() ||
                !ProcessUtil.checkIfAllEnd(processes)) {
            algorithm.doSchedule(processes, simulation);
            simulation.incTime();
            ProcessUtil.timerTick(processes);
        }
        logger.endOfSimulation(simulation, processes);
    }
}
