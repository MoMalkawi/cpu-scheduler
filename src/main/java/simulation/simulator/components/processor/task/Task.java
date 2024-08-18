package simulation.simulator.components.processor.task;

public abstract class Task {

    private final int id;

    private final int creationCycle;

    private final int executionTime;

    private final boolean priority;

    public Task(int id, int creationCycle, int executionTime, boolean priority) {
        this.id = id;
        this.creationCycle = creationCycle;
        this.executionTime = executionTime;
        this.priority = priority;
    }

    public abstract void run();

    public int getCreationCycle() {
        return creationCycle;
    }

    public int getExecutionTime() {
        return executionTime;
    }

    public boolean isPriority() {
        return priority;
    }

    public int getId() {
        return id;
    }

}
