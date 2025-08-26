import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Lebron {
    enum Command {
        LIST("list") {
            public void execute(String helly, List<Task> ls) {
               displayList(helly, ls);
            }
        },

        MARK("mark") {
            public void execute(String helly, List<Task> ls) {
               markTask(helly, ls);
            }
        },

        UNMARK("unmark") {
            public void execute(String helly, List<Task> ls) {
                unmarkTask(helly, ls);
            }
        },

        TODO("todo") {
            public void execute(String helly, List<Task> ls) {
                addTodo(helly, ls);
            }
        },

        DEADLINE("deadline") {
            public void execute(String helly, List<Task> ls) {
                addDeadline(helly, ls);
            }
        },

        EVENT("event") {
            public void execute(String helly, List<Task> ls) {
                addEvent(helly, ls);
            }
        },

        DELETE("delete") {
            public void execute(String helly, List<Task> ls) {
                removeTask(helly, ls);
            }
        },

        UNKNOWN("unknown") {
            public void execute(String helly, List<Task> ls) {
                System.out.println("What the helly do you mean, please try again");
            }
        };
        abstract public void execute(String helly, List<Task> myList);

        private final String keyword;

        Command(String keyword) {
            this.keyword = keyword;
        }

        public boolean matches(String input) {
            return keyword.equalsIgnoreCase(input);
        }

        public static Command fromString(String input) {
            for (Command cmd: values()) {
                if (cmd.matches(input)) {
                    return cmd;
                }
            }
            return Command.UNKNOWN;
        }
    }

    public static void main(String[] args) {
        Scanner myObj = new Scanner(System.in);
        String welcomeMsg = "Wassup, I'm Lebron. What popping homie?";
        String exitMsg = "Im gonna bounce. See ya fam!";
        List<Task> myList = new ArrayList<>();

        loadTasksFromStorage(myList);

        //print welcome message
        System.out.println(welcomeMsg);
        printHorizontalLine();

        String userInput = myObj.nextLine();
        while (!userInput.equals("bye")) {
            String[] splitUserInput = userInput.split(" ", 2);
            if (splitUserInput.length == 1) {
                splitUserInput = new String[]{userInput, ""};
            }
            printHorizontalLine();
            //Used enums to parse command instead of switch case
            Command.fromString(splitUserInput[0]).execute(splitUserInput[1], myList);
            printHorizontalLine();
            userInput = myObj.nextLine();
        }
        printHorizontalLine();

        //print exit message
        System.out.println(exitMsg);
        storeTasks(myList);
        printHorizontalLine();
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

    public static void printHorizontalLine() {
        int length = 50;
        char line = '-';

        for (int i = 0; i < length; i++) {
            System.out.print(line);
        }

        System.out.println();
    }

    public static void displayList(String helly, List<Task> ls) {
        if (ls.isEmpty()) {
            System.out.println("List is Empty");
        } else {
            System.out.println("Here are the tasks in your list: ");
            for (int i = 1; i < ls.size() + 1; i++) {
                Task curTask = ls.get(i-1);
                System.out.println(i + "." + curTask.toString());
            }
        }
    }
    public static void removeTask(String helly, List<Task> ls) {
        try {
            int idx = Integer.parseInt(helly) - 1;
            if (ls.size() <= idx || idx < 0) {
                int tmpnum = idx + 1;
                System.out.println("Task " + tmpnum + " does not exist!");
            } else {
                Task curTask = ls.remove(idx);
                System.out.println("I've Removed this task from the list ");
                System.out.println(curTask);
                System.out.println("Now you have " + ls.size() + " items left in the list!");
            }
        } catch (NumberFormatException e) {
            System.out.println("Please input a valid integer after delete ");
        }
    }

    public static void markTask(String helly, List<Task> ls) {
        try {
            int idx = Integer.parseInt(helly) - 1;
            if (ls.size() <= idx || idx < 0) {
                int tmpnum = idx++;
                System.out.println("Task " + tmpnum + " does not exist!");
            } else {
                Task curTask = ls.get(idx);
                curTask.markAsCompleted();
                System.out.println("I've just marked this as done my G");
                System.out.println(curTask);
            }
        } catch (NumberFormatException e) {
            System.out.println("Please input a valid integer after mark ");
        }
    }

    public static void unmarkTask(String helly, List<Task> ls) {
        try {
            int idx = Integer.parseInt(helly) - 1;
            if (ls.size() <= idx || idx < 0) {
                int tmpnum = idx + 1;
                System.out.println("Task " + tmpnum + " does not exist!");
            } else {
                Task curTask = ls.get(idx);
                curTask.unmarkAsCompleted();
                System.out.println("This task is officially undone");
                System.out.println(curTask);
            }
        } catch (NumberFormatException e) {
            System.out.println("Please input a valid integer after unmark ");
        }
    }

    public static void addTodo(String s, List<Task> myList) {
        System.out.println("No Problem G, I got you");
        Task newTask = new ToDoTask(s);
        myList.add(newTask);
        System.out.println(newTask);
        System.out.println("Now you have " + myList.size() + " tasks in the list");
    }

    public static void addDeadline(String s, List<Task> myList) {
        System.out.println("Looks like you want to add a Task with Deadline");
        int idx = s.indexOf("/by");
        if (idx <= 0) {
            System.out.println("Deadline should have a format of deadline 'name' /by YYYY-MM-DD");
            return;
        }
        String name = s.substring(0,idx).trim();
        try {
            LocalDate by = LocalDate.parse(s.substring(3 + idx).trim());
            Task newTask = new DeadlineTask(name, by);
            myList.add(newTask);
            System.out.println(newTask);
            System.out.println("Done bro");
        } catch (DateTimeParseException e) {
            System.out.println("Deadline should have a format of deadline 'name' /by YYYY-MM-DD");
        }
    }

    public static void addEvent(String s, List<Task> myList) {
        System.out.println("Hmm an event interesting");
        int idx0 = s.indexOf("/from");
        int idx1 = s.indexOf("/to");
        if (idx0 <= 0 || idx1 <= 0) {
            System.out.println("Event should have a format of event 'name' /from YYYY-MM-DD /to YYYY-MM-DD");
            return;
        }
        try {
            String name = s.substring(0,idx0).trim();
            LocalDate from = LocalDate.parse(s.substring(5+idx0,idx1).trim());
            LocalDate to = LocalDate.parse(s.substring(idx1+3).trim());
            Task newTask = new EventTask(name,from,to);
            myList.add(newTask);
            System.out.println(newTask);
            System.out.println("Added the task for you");
        } catch (DateTimeParseException e) {
            System.out.println("Event should have a format of event 'name' /from YYYY-MM-DD /to YYYY-MM-DD");
        }

    }
}
