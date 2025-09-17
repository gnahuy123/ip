package lebron;

import java.util.ArrayList;
import java.util.List;

import gui.Main;
import javafx.application.Application;
import parser.Parser;
import storage.Storage;
import tasks.Task;
import ui.Ui;


/**
 * Main class for lebron chatbot
 *
 */
public class Lebron {

    private final Ui ui;
    private final List<Task> myList;
    private final Parser parser;
    private final Storage storage;

    /**
     * Constructor for Lebron chatbot.
     * Initializes UI, task list, parser, and storage components.
     * Loads existing tasks from storage file.
     *
     * @param fileName the name of the file to store/load tasks from
     */
    public Lebron(String fileName) {
        ui = new Ui();
        myList = new ArrayList<>();
        parser = new Parser(myList);
        storage = new Storage(myList, fileName);
        storage.loadTasksFromStorage();
    }

    /**
     * Main method to start the chatbot application.
     *
     * @param main command line arguments (not used)
     */
    public static void main(String[] main) {
        Application.launch(Main.class, main);
        //new Lebron("./data/userData.csv").run();
    }

    private void run() {

        //print welcome message
        ui.startUp();

        String userInput = ui.getNextLine();
        while (!userInput.equals("bye")) {
            ui.printHorizontalLine();
            ui.print(parser.parseUi(userInput));
            ui.printHorizontalLine();
            userInput = ui.getNextLine();
        }
        ui.printHorizontalLine();

        //print exit message
        ui.exit();
        storage.storeTasks();
    }

    /**
     * Used for GUI to get response everytime it sense userInput
     * @param userInput text input by user
     * @return response of lebron chatbot
     */
    public String getResponse(String userInput) {
        return parser.parseUi(userInput);
    }

    public Storage getStorage() {
        return storage;
    }
}
