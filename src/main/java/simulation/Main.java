package simulation;

import simulation.data.Configuration;
import simulation.simulator.Simulator;
import simulation.simulator.utils.ReportGenerator;
import simulation.utils.io.ReaderWriter;
import simulation.utils.io.console.Console;

public class Main {

    public static void main(String[] args) {
        Console.init();
        Configuration.get(); //force initialization
        Simulator simulator = new Simulator(ReaderWriter.readSortedTasks());
        simulator.simulate();
        ReportGenerator reportGenerator = new ReportGenerator(simulator);
        reportGenerator.generate();
        Console.close();
    }

}
