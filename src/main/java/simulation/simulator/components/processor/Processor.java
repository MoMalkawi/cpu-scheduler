package simulation.simulator.components.processor;

import simulation.data.Configuration;
import simulation.simulator.components.processor.data.TaskReport;
import simulation.simulator.components.processor.task.Task;
import simulation.utils.AnsiColors;
import simulation.utils.io.console.Console;
import simulation.utils.io.console.messages.Message;
import simulation.utils.io.console.messages.MessageBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class Processor implements Observer {

    private final int id;

    private Task task = null;

    private int lastTaskInsertionCycle;

    private final List<TaskReport> taskHistory;

    public Processor(int id) {
        this.id = id;
        this.taskHistory = new ArrayList<>();
    }

    @Override
    public void update(Observable o, Object arg) {
        if(task == null)
            return;
        int cycle = (int) arg;
        if((lastTaskInsertionCycle + task.getExecutionTime()) <= cycle) {
            Console.send("Task [" + task.getId() + "] Finished.",
                    Message.Type.SUCCESS);
            Console.send("Task ["+task.getId()+"] exited Processor ["+id+"].",
                    Message.Type.SUCCESS);
            taskHistory.add(new TaskReport(task.getId(), Math.max(cycle - lastTaskInsertionCycle, 1),
                    lastTaskInsertionCycle, cycle));
            task = null;
        }
    }

    public void executeTask(Task task, int cycle) {
        this.task = task;
        this.lastTaskInsertionCycle = cycle;
        Console.send(new MessageBuilder()
                .text("Executing: Task ["+task.getId()+"] from Processor ["+id+"]")
                .effects(AnsiColors.YELLOW_BOLD_BRIGHT)
                .build());
        task.run();
    }

    public void terminateTask() {
        if(task != null) {
            taskHistory.add(new TaskReport(task.getId(), (Configuration.get().getCycleCount() + 1)  - lastTaskInsertionCycle,
                    lastTaskInsertionCycle, Configuration.get().getCycleCount()));
            task = null;
            lastTaskInsertionCycle = 0;
        }
    }

    public boolean isEmpty() {
        return task == null;
    }

    public List<TaskReport> getTaskHistory() {
        return taskHistory;
    }

    public int getId() {
        return id;
    }

    public Task getTask() {
        return task;
    }

}
