package simulation.simulator.components.scheduler.impl;

import simulation.simulator.Simulator;
import simulation.simulator.components.processor.Processor;
import simulation.simulator.components.processor.task.Task;
import simulation.simulator.components.scheduler.Scheduler;

public class PriorityLJFScheduler extends Scheduler {

    public PriorityLJFScheduler(Simulator simulator) {
        super(simulator, (o1, o2) -> {
            if(o1.isPriority() != o2.isPriority())
                return o1.isPriority() ? -1 : 1;
            return Integer.compare(o2.getExecutionTime(), o1.getExecutionTime());
        });
    }

    @Override
    protected void executeTask(Processor processor, int cycle) {
        Task task = taskQueue.poll();
        assert task != null;
        processor.executeTask(task, cycle);
    }

}
