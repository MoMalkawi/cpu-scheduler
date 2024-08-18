package simulation.simulator.components.processor.task;

import simulation.simulator.components.processor.task.impl.PrintTask;

public enum TaskFactory {

    PRINT_TASK {
        @Override
        public Task create(int creationCycle, int executionTime, boolean priority, Object... args) {
            return new PrintTask(++tasksCreated, creationCycle, executionTime, priority);
        }
    };

    private static int tasksCreated = 0;

    public abstract Task create(int creationCycle, int executionTime, boolean priority, Object... args);

    public static Task createTask(TaskFactory taskFactory, int creationCycle, int executionTime, boolean priority, Object... args) {
        return taskFactory.create(creationCycle, executionTime, priority, args);
    }

}
