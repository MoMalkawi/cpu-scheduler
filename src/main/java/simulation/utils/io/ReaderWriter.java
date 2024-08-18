package simulation.utils.io;

import simulation.simulator.components.processor.task.Task;
import simulation.data.Configuration;
import simulation.simulator.components.processor.task.TaskFactory;
import simulation.utils.AnsiColors;

import java.io.*;
import java.util.*;

public class ReaderWriter {

    private ReaderWriter() {}

    public static LinkedList<Task> readSortedTasks() {
        LinkedList<Task> tasks = readTasks();
        tasks.sort(Comparator.comparingInt(Task::getCreationCycle));
        return tasks;
    }

    private static LinkedList<Task> readTasks() {
        List<String> lines = readLines(Configuration.get().getTaskPath());
        LinkedList<Task> tasks = new LinkedList<>();
        for(String line : lines) {
            String[] data = line.split(" ");
            if(data.length > 1)
                tasks.add(TaskFactory.PRINT_TASK.create(Integer.parseInt(data[0]),
                    Integer.parseInt(data[1]), data[2].contains("1")));
        }
        return tasks;
    }

    public static List<String> readLines(String path) {
        try(BufferedReader reader = new BufferedReader(new FileReader(path)))  {
            List<String> lines = new ArrayList<>();
            String line;
            while((line = reader.readLine()) != null)
                lines.add(line);
            return lines;
        } catch(IOException e) {
            System.out.println(AnsiColors.format("Error Reading Lines...", AnsiColors.RED_BOLD_BRIGHT));
        }
        return Collections.emptyList();
    }

}
