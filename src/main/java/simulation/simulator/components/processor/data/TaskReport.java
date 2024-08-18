package simulation.simulator.components.processor.data;

public class TaskReport {

    private final int taskID;

    private final int duration;

    private final int startCycle;

    private final int endCycle;

    public TaskReport(int taskID, int duration, int startCycle, int endCycle) {
        this.taskID = taskID;
        this.duration = duration;
        this.startCycle = startCycle;
        this.endCycle = endCycle;
    }

    public int getTaskID() {
        return taskID;
    }

    public int getDuration() {
        return duration;
    }

    public int getStartCycle() {
        return startCycle;
    }

    public int getEndCycle() {
        return endCycle;
    }

}
