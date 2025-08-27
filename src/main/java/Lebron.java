import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Lebron {

    private final Ui ui;
    private final List<Task> myList;
    private final Parser parser;

    //TODO move fileName out
    public Lebron(String fileName) {
        ui = new Ui();
        myList = new ArrayList<>();
        loadTasksFromStorage(myList);
        parser = new Parser(myList);
    }

    public static void main(String[] main) {
        new Lebron("./data/userData.csv").run();
    }

    public void run() {
        Scanner myObj = new Scanner(System.in);
        String welcomeMsg = "Wassup, I'm Lebron. What popping homie?";
        String exitMsg = "Im gonna bounce. See ya fam!";

        //print welcome message
        System.out.println(welcomeMsg);
        ui.printHorizontalLine();

        String userInput = myObj.nextLine();
        while (!userInput.equals("bye")) {
            String[] splitUserInput = userInput.split(" ", 2);
            if (splitUserInput.length == 1) {
                splitUserInput = new String[]{userInput, ""};
            }
            ui.printHorizontalLine();
            //Used enums to parse command instead of switch case
            //TODO add splitting logic into parser
            parser.parseUi(splitUserInput[0], splitUserInput[1]);
            //Command.fromString(splitUserInput[0]).execute(splitUserInput[1], myList);
            ui.printHorizontalLine();
            userInput = myObj.nextLine();
        }
        ui.printHorizontalLine();

        //print exit message
        System.out.println(exitMsg);
        storeTasks(myList);
        ui.printHorizontalLine();
    }

    public static void storeTasks(List<Task> ls) {
        String fileName = "./data/userData.csv";
        // Having fileWriter in the argument ensures i dont have to call file close if there is a error
        try (FileWriter writer = new FileWriter(fileName, false)) {
            for (Task t: ls) {
                writer.write(t.toCSV());
            }
        } catch (IOException e) {
            System.out.println("Data file does not exist");
        }
    }

    public static void loadTasksFromStorage(List<Task> ls) {
        String fileName = "./data/userData.csv";
        try {
            File f = new File(fileName);
            boolean isNew = f.createNewFile();
            if (!isNew) {
                //ensure file only contains ASCII characters
                Scanner scanner = new Scanner(f, java.nio.charset.StandardCharsets.UTF_8);
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
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Task parseTask(String s) {
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
