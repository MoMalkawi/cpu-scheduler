package simulation.data;

import simulation.utils.AnsiColors;
import simulation.utils.io.console.Console;
import simulation.utils.io.console.messages.MessageBuilder;

public class Configuration {

    private static Configuration instance = null;

    private int processorCount;

    private int cycleCount;

    private String taskPath;

    public static Configuration get() {
        if(instance == null) {
            Configuration config = new Configuration();
            fetchInput(config);
            instance = config;
        }
        return instance;
    }

    private static void fetchInput(Configuration configuration) {
        Console.send(new MessageBuilder()
                .text("=~=~=~=~=~=~=~=~=~=~=~ Configuration =~=~=~=~=~=~=~=~=~=~")
                .effects(AnsiColors.WHITE_BACKGROUND_BRIGHT, AnsiColors.BLACK_BOLD)
                .build());
        Console.send(new MessageBuilder()
                .text("Hello! Please answer the following questions to get started!")
                .effects(AnsiColors.BLUE_BOLD_BRIGHT)
                .build());
        fillConfigurationInput(configuration);
        Console.send(new MessageBuilder()
                .text("Thank you!")
                .effects(AnsiColors.BLUE_BOLD_BRIGHT)
                .build());
        Console.send(new MessageBuilder()
                .text("=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~")
                .effects(AnsiColors.WHITE_BACKGROUND_BRIGHT, AnsiColors.BLACK_BOLD)
                .build());
    }

    private static void fillConfigurationInput(Configuration configuration) {
        configuration.processorCount = Integer.parseInt(Console.
                receive("How many processors do you want? (numbers only)"));
        configuration.cycleCount = Integer.parseInt(Console.
                receive("How many cycles do you want? (numbers only)"));
        configuration.taskPath = Console.
                receive("Enter the path to your tasks input file (absolute)");
    }

    public int getProcessorCount() {
        return processorCount;
    }

    public int getCycleCount() {
        return cycleCount;
    }

    public String getTaskPath() {
        return taskPath;
    }

}
