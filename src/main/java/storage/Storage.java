package storage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import tasks.DeadlineTask;
import tasks.EventTask;
import tasks.Task;
import tasks.ToDoTask;

/*
* Class responsible for hard-disk storage of tasks
*
 */
public class Storage {

    private final List<Task> ls;
    private final String fileName;

    /*
    * Constructor for Storage class
    *
    * @param ls, List<Task> that contains users task
    * @param fileName name of csv file that stores tasks
     */
    public Storage(List<Task> ls, String fileName) {
        this.ls = ls;
        this.fileName = fileName;
    }

    /*
    * Method used after execution of main program to store tasks back in hard drive
     */
    public void storeTasks() {
        // Having fileWriter in the argument ensures i dont have to call file close if there is a error
        try (FileWriter writer = new FileWriter(fileName, false)) {
            for (Task t: ls) {
                writer.write(t.toCsv());
            }
        } catch (IOException e) {
            System.out.println("Data file does not exist");
        }
    }

    /*
    * Method that loads tasks from hard disk to Task List
     */
    public void loadTasksFromStorage() {
        try {
            File f = new File(fileName);
            boolean isNew = f.createNewFile();
            if (!isNew) {
                // Ensure you close the scanner so testcases can run
                try (Scanner scanner = new Scanner(f, java.nio.charset.StandardCharsets.UTF_8)) {
                    while (scanner.hasNextLine()) {
                        String line = scanner.nextLine();
                        Task t = parseTask(line);
                        if (t != null) {
                            ls.add(t);
                        } else {
                            System.out.println("file corrupted");
                        }
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected static Task parseTask(String s) {
        //format name, isCompleted
        String[] cells = s.split(",", -1);
        Task res;
        boolean isCompleted = Boolean.parseBoolean(cells[2]);
        switch (cells[0]) {
        case "Todo" -> res = new ToDoTask(cells[1], isCompleted);
        case "Deadline" -> res =
                cells.length == 4
                        ? new DeadlineTask(
                        cells[1],
                        isCompleted,
                        LocalDate.parse(cells[3]))
                        : null;
        case "Event" -> res =
                cells.length == 5
                        ? new EventTask(
                        cells[1],
                        isCompleted,
                        LocalDate.parse(cells[3]),
                        LocalDate.parse(cells[4]))
                        : null;
        default -> res = null;
        }
        return res;
    }
}
