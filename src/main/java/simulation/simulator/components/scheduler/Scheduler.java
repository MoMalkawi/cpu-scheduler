package simulation.simulator.components.scheduler;

import simulation.simulator.Simulator;
import simulation.simulator.components.processor.Processor;
import simulation.simulator.components.processor.task.Task;

import java.util.Comparator;
import java.util.Observable;
import java.util.Observer;
import java.util.PriorityQueue;

public abstract class Scheduler implements Observer {

    protected final PriorityQueue<Task> taskQueue;

    private final Simulator simulator;

    protected Scheduler(Simulator simulator, Comparator<Task> queueComparator) {
        this.simulator = simulator;
        this.taskQueue = new PriorityQueue<>(queueComparator);
    }

    @Override
    public void update(Observable o, Object arg) {
        while(!taskQueue.isEmpty()) {
            Processor processor = getFirstEmptyProcessor();
            if (processor == null)
                break;
            executeTask(processor, (int) arg);
        }
    }

    protected abstract void executeTask(Processor processor, int cycle);

    private Processor getFirstEmptyProcessor() {
        for(Processor p : simulator.getProcessors()) {
            if(p.isEmpty())
                return p;
        }
        return null;
    }

    public void addTask(Task task) {
        taskQueue.offer(task);
    }

}
