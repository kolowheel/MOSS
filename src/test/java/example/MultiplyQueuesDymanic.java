package example;


import com.schedule.simulator.bean.SProcess;
import com.schedule.simulator.bean.Simulation;
import com.schedule.simulator.scheduleAlghoritm.ScheduleAlgorithm;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


/**
 * Created with IntelliJ IDEA.
 * User: wheel
 * Date: 16.12.13
 * Time: 19:26
 * simulator 1
 */
public class MultiplyQueuesDymanic implements ScheduleAlgorithm {

    public static final int PRIORITY_COUNT = 3;
    private final int quant;
    private final int multiplier;

    private Map<Integer, LinkedList<SProcess>> queues = new HashMap<Integer, LinkedList<SProcess>>();

    private Map<Integer, Integer> quants = new HashMap<Integer, Integer>();

    private Map<SProcess, ProcessInformation> quantUsing = new HashMap<SProcess, ProcessInformation>();

    private SProcess currentProcess = null;
    private int currentQuantSize;
    private int currentQuantTick;

    private SProcess potentionalProcess = null;
    private int potentionalQuantSize;

    MultiplyQueuesDymanic(int quant, int multiplier) {
        this.quant = quant;
        this.multiplier = multiplier;
    }

    @Override
    public void doSchedule(List<SProcess> processes, Simulation simulation) {
        if (currentProcess == null) {
            findFirstReadyProcess(true);

        }
        if (currentProcess != null) {
            findFirstReadyProcess(false);
            if (potentionalProcess != null && potentionalQuantSize < currentQuantSize) {
                if (currentProcess.getState() == SProcess.PROCESS_WORKING)
                    currentProcess.doPreemptive();
                currentQuantTick = 0;

                currentProcess = potentionalProcess;
                currentProcess.setState(SProcess.PROCESS_WORKING);

            } else if (currentProcess.getState() != SProcess.PROCESS_WORKING) {
                if (currentProcess.getState() != SProcess.PROCESS_READY)
                    incPriority(currentProcess);
                currentProcess = null;
                currentQuantTick = 0;
                findFirstReadyProcess(true);
                if (currentProcess != null)
                    currentProcess.setState(SProcess.PROCESS_WORKING);
            } else {
                ProcessInformation inf = quantUsing.get(currentProcess);
                if (inf.getQuant() == inf.getQuantUsing()) {
                    decPriority(currentProcess);
                    currentProcess.doPreemptive();
                    currentProcess = null;
                    currentQuantTick = 0;
                    findFirstReadyProcess(true);
                    if (currentProcess != null)
                        currentProcess.setState(SProcess.PROCESS_WORKING);
                }
            }
        }

        if (currentProcess != null)
            quantUsing.get(currentProcess).inc();
    }

    private void decPriority(SProcess process) {
        ProcessInformation inf = quantUsing.get(process);
        int currentPriority = inf.getPriority();
        inf.setQuantUsing(0);
        if (!(--currentPriority < 1)) {

            System.out.println(process.getId() + " change priority from " + inf.getPriority() + " on " + currentPriority);

            inf.setPriority(currentPriority);
            inf.setQuant(quants.get(currentPriority));
        }

    }

    private void incPriority(SProcess process) {
        ProcessInformation inf = quantUsing.get(process);
        int currentPriority = inf.getPriority();
        inf.setQuantUsing(0);

        if (++currentPriority <= MultiplyQueuesDymanic.PRIORITY_COUNT) {

            System.out.println(process.getId() + "change priority from " + inf.getPriority() + " on " + currentPriority);

            inf.setPriority(currentPriority);
            inf.setQuant(quants.get(currentPriority));
        }

    }

    @Override
    public void init(List<SProcess> processes, Simulation simulation) {

        queues.put(1, new LinkedList<SProcess>()); // долгий квант
        queues.put(2, new LinkedList<SProcess>()); // короткий квант
        queues.put(3, new LinkedList<SProcess>()); // ввод-вывод

        int p3 = quant * multiplier;
        int p2 = quant * multiplier * multiplier;
        int p1 = quant * multiplier * multiplier * multiplier;
        quants.put(1, p1);
        quants.put(2, p2);
        quants.put(3, p3);
        System.out.println(quants);

        List<SProcess> queue = queues.get(3);

        for (int i = 0; i < processes.size(); i++) {
            queue.add(processes.get(i));
            ProcessInformation information = new ProcessInformation();
            information.setPriority(3);
            information.setQuant(quants.get(3));
            quantUsing.put(processes.get(i), information);
        }
    }

    private void findFirstReadyProcess(boolean current) {
        for (int i = PRIORITY_COUNT; i > 0; i--) {
            LinkedList<SProcess> list = queues.get(i);
            for (int j = 0; j < list.size(); j++) {
                if (list.get(j).getState() == SProcess.PROCESS_READY) {
                    if (current) {
                        currentProcess = list.remove(j);
                        currentQuantSize = quants.get(i);
                        list.add(currentProcess);
                    } else {
                        potentionalProcess = list.get(j);
                        potentionalQuantSize = quants.get(i);
                    }
                    return;
                }
            }
        }
        if (current) {
            currentProcess = null;
        } else
            potentionalProcess = null;

    }

}

class ProcessInformation {
    int priority;
    int quant = 0;
    int quantUsing = 0;

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        if (priority > MultiplyQueuesDymanic.PRIORITY_COUNT)
            throw new IllegalStateException("priority higher then " + MultiplyQueuesDymanic.PRIORITY_COUNT);
        if (priority <= 0)
            throw new IllegalStateException("priority lower then 0");
        this.priority = priority;
    }

    public int getQuant() {
        return quant;
    }

    public void setQuant(int quant) {
        this.quant = quant;
    }

    public int getQuantUsing() {
        return quantUsing;
    }

    public void setQuantUsing(int quantUsing) {
        this.quantUsing = quantUsing;
    }

    public void inc() {
        if (quantUsing > quant)
            throw new IllegalStateException("Quant using higher then quant time");
        quantUsing++;
    }
}
