package com.schedule.simulator.util;

import com.schedule.simulator.bean.SProcess;

import java.util.List;

public class ProcessUtil {
    public static void timerTick(List<SProcess> processes) {
        for (SProcess process : processes) {
            process.timerTick();
        }
    }

    public static String stringValueOfState(int state) {
        switch (state) {
            case SProcess.PROCESS_ENDED:
                return "PROCESS_ENDED";
            case SProcess.PROCESS_IO_BLOCKING:
                return "PROCESS_IO_BLOCKING";
            case SProcess.PROCESS_READY:
                return "PROCESS_READY";
            case SProcess.PROCESS_WORKING:
                return "PROCESS_WORKING";
            default:
                return "unknown state";
        }
    }

    public static boolean checkIfAllEnd(List<SProcess> processes) {
        for (SProcess process : processes) {
            if (process.getState() != SProcess.PROCESS_ENDED) {
                return false;
            }

        }
        return true;
    }
}
