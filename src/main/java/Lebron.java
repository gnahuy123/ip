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
    private final Storage storage;

    //TODO move fileName out
    public Lebron(String fileName) {
        ui = new Ui();
        myList = new ArrayList<>();
        parser = new Parser(myList);
        storage = new Storage(myList, fileName);
        storage.loadTasksFromStorage();
    }

    public static void main(String[] main) {
        new Lebron("./data/userData.csv").run();
    }

    public void run() {

        //print welcome message
        ui.startUp();

        String userInput = ui.getNextLine();
        while (!userInput.equals("bye")) {
            ui.printHorizontalLine();
            parser.parseUi(userInput);
            ui.printHorizontalLine();
            userInput = ui.getNextLine();
        }
        ui.printHorizontalLine();

        //print exit message
        ui.exit();
        storage.storeTasks();
    }
}
