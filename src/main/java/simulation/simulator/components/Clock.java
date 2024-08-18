package simulation.simulator.components;

import simulation.utils.AnsiColors;
import simulation.utils.io.console.Console;
import simulation.utils.io.console.messages.Message;
import simulation.utils.io.console.messages.MessageBuilder;

import java.util.Observable;

public class Clock extends Observable {

    private int cycle;

    private final int cycleTime;

    public Clock(int cycleTime) { //ms
        this.cycleTime = cycleTime;
    }

    public void tick() {
        try {
            Thread.sleep(cycleTime);
            cycle++;
            setChanged();
            Console.send(new MessageBuilder()
                    .text("Cycle [" + cycle + "]")
                    .effects(AnsiColors.BLACK_BACKGROUND, AnsiColors.PURPLE_BOLD)
                    .build());
        } catch (InterruptedException e) {
            Console.send("[ERROR] Can't sleep.", Message.Type.ERROR);
            System.exit(0);
        }
    }

    public int getCycle() {
        return cycle;
    }

}
