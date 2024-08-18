package simulation.simulator.utils;

import simulation.data.Configuration;
import simulation.simulator.Simulator;
import simulation.simulator.components.processor.Processor;
import simulation.simulator.components.processor.data.TaskReport;
import simulation.utils.AnsiColors;
import simulation.utils.NumberUtils;
import simulation.utils.io.console.Console;
import simulation.utils.io.console.messages.MessageBuilder;

public class ReportGenerator {

    private final Simulator simulator;

    private static final AnsiColors color1 = AnsiColors.GREEN_BACKGROUND;
    private static final AnsiColors color1alt = AnsiColors.BLUE_BACKGROUND;
    private static final AnsiColors color2 = AnsiColors.RED_BACKGROUND;
    private static final AnsiColors color2alt = AnsiColors.YELLOW_BACKGROUND;

    public ReportGenerator(Simulator simulator) {
        this.simulator = simulator;
    }

    public void generate() {
        Console.send(new MessageBuilder().text("Simulation Visual Report:").
                effects(AnsiColors.WHITE_BOLD_BRIGHT).start("\n\n").build());
        printClock();
        boolean firstColorPattern = false;
        for(Processor processor : simulator.getProcessors()) {
            if(!processor.getTaskHistory().isEmpty()) {
                printProcessorsHistory(processor, firstColorPattern);
                firstColorPattern = !firstColorPattern;
            }
        }
    }

    private void printProcessorsHistory(Processor processor, boolean firstColorPattern) {
        boolean altColor = false;
        Console.send(new MessageBuilder().text("P" + processor.getId() + " ")
                .effects(AnsiColors.WHITE_BOLD_BRIGHT).end(" ").build());
        int currentCycle = 1;
        for(TaskReport task : processor.getTaskHistory()) {
            int precedingCycles = getPrecedingCycles(task, currentCycle);
            if(precedingCycles > 0) {
                skipCycles(precedingCycles);
                currentCycle += precedingCycles - 1;
            }
            printTask(task, getTaskColor(altColor, firstColorPattern));
            currentCycle += task.getDuration();
            altColor = !altColor;
        }
        Console.newLine();
    }

    private int getPrecedingCycles(TaskReport task, int currentCycle) {
        return task.getStartCycle() - currentCycle;
    }

    private void printTask(TaskReport task, AnsiColors color) {
        printCycle(task.getTaskID(), task.getDuration(), AnsiColors.WHITE_BOLD_BRIGHT, color);
    }

    private void skipCycles(int cycles) {
        int tabAmount = cycles * 2;
        for(int i = 0; i < tabAmount; i++)
            Console.send(new MessageBuilder().text("  ").end("").build());
    }

    private AnsiColors getTaskColor(boolean altColor, boolean firstColorPattern) {
        if(altColor)
            return firstColorPattern ? color1alt : color2alt;
        return firstColorPattern ? color1 : color2;
    }

    private void printClock() {
        Console.send(new MessageBuilder().text("CLK")
                .effects(AnsiColors.WHITE_BOLD_BRIGHT).end(" ").build());
        boolean alt = false;
        for(int i = 1; i <= Configuration.get().getCycleCount(); i++) {
            if(alt)
                printCycle(i, 1, AnsiColors.WHITE_BOLD_BRIGHT, AnsiColors.BLACK_BACKGROUND);
            else
                printCycle(i, 1, AnsiColors.BLACK_BOLD, AnsiColors.WHITE_BACKGROUND);
            alt = !alt;
        }
        Console.newLine();
    }

    private void printCycle(int id, int cycleCount, AnsiColors textColor, AnsiColors bgColor) {
        int idDigitsLength = NumberUtils.countDigits(id);
        int tabCount = cycleCount * 4;
        MessageBuilder block = new MessageBuilder()
                .effects(textColor, bgColor)
                .end("");
        for(int i = 0; i < (tabCount / 2) - 1; i++)
            block.text(" ");
        block.text(String.valueOf(id));
        for(int i = (tabCount / 2) + idDigitsLength; i <= tabCount; i++)
            block.text(" ");
        Console.send(block.build());
    }

}
