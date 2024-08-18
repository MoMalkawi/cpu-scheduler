package simulation.simulator;

import simulation.simulator.components.Clock;
import simulation.simulator.components.processor.Processor;
import simulation.simulator.components.scheduler.Scheduler;
import simulation.simulator.components.scheduler.impl.PriorityLJFScheduler;
import simulation.simulator.components.processor.task.Task;
import simulation.data.Configuration;
import simulation.utils.io.console.Console;
import simulation.utils.io.console.messages.Message;

import java.util.Arrays;
import java.util.Queue;

public class Simulator {

    private final Scheduler scheduler;

    private final Processor[] processors;

    private final Queue<Task> tasks;

    private final Clock clock;

    public Simulator(Queue<Task> tasks) {
        this.tasks = tasks;
        this.scheduler = new PriorityLJFScheduler(this);
        this.processors = createProcessors();
        clock = new Clock(1000); //1s
        init();
    }

    private void init() {
        clock.addObserver(scheduler);
        Arrays.stream(processors).forEach(clock::addObserver);
    }

    public void simulate() {
        Console.send("\n\nSimulation Timeline:", Message.Type.INFO);
        while(shouldRun()) {
            clock.tick();
            updateComponents();
        }
        terminateProcessors();
        Console.send("Simulation Completed.", Message.Type.INFO);

    }

    private void updateComponents() {
        pollNewTasks();
        clock.notifyObservers(clock.getCycle());
    }

    private boolean shouldRun() {
        return (clock.getCycle() + 1) <= Configuration.get().getCycleCount();
    }

    private void terminateProcessors() {
        for(Processor p : processors)
            p.terminateTask();
    }

    private Processor[] createProcessors() {
        Processor[] temp = new Processor[Configuration.get().getProcessorCount()];
        for(int i = 0; i < temp.length; i++)
            temp[i] = new Processor(i + 1);
        return temp;
    }

    private void pollNewTasks() {
        while(!tasks.isEmpty()) {
            Task task = tasks.peek();
            if(task.getCreationCycle() > clock.getCycle())
                break;
            Console.send("Task [" + task.getId() +
                            "] inserted into Scheduler.",
                    Message.Type.INFO);
            scheduler.addTask(task);
            tasks.remove();
        }
    }

    public Processor[] getProcessors() {
        return processors;
    }

}
