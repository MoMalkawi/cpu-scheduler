package simulation.simulator.components.processor.task.impl;

import simulation.simulator.components.processor.task.Task;
import simulation.utils.AnsiColors;
import simulation.utils.io.console.Console;
import simulation.utils.io.console.messages.MessageBuilder;

public class PrintTask extends Task {

    public PrintTask(int id, int creationCycle, int executionTime, boolean priority) {
        super(id, creationCycle, executionTime, priority);
    }

    @Override
    public void run() {
        Console.send(new MessageBuilder()
                .text("Task [" + getId() + "] Running...")
                .effects(AnsiColors.YELLOW_BOLD)
                .build());
    }

}
