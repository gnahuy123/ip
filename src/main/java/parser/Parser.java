package parser;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import tasks.DeadlineTask;
import tasks.EventTask;
import tasks.Task;
import tasks.ToDoTask;


/**
* Class that handles parsing of commands
*
 */
public class Parser {

    private final List<Task> taskList;
    /**
    * Constructor for Parser
    *
    * @param ls the {@code List<Task>} that will contain the tasks
     */
    public Parser(List<Task> ls) {
        assert ls != null : "taskList cannot be null!";
        this.taskList = ls;
    }
    /**
    * Method that takes String and splits it into command and argument
    * It then passes the argument to the respective command
    *
    * @param userInput String that user inputs
     */
    public String parseUi(String userInput) {
        String[] splitUserInput = userInput.split(" ", 2);
        if (splitUserInput.length == 1) {
            splitUserInput = new String[]{userInput, ""};
        }
        return Command.fromString(splitUserInput[0]).execute(splitUserInput[1], taskList);
    }

    enum Command {
        LIST("list") {
            public String execute(String taskInfo, List<Task> ls) {
                return displayList(taskInfo, ls);
            }
        },

        MARK("mark") {
            public String execute(String taskInfo, List<Task> ls) {
                return markTask(taskInfo, ls);
            }
        },

        UNMARK("unmark") {
            public String execute(String taskInfo, List<Task> ls) {
                return unmarkTask(taskInfo, ls);
            }
        },

        TODO("todo") {
            public String execute(String taskInfo, List<Task> ls) {
                return addTodo(taskInfo, ls);
            }
        },

        DEADLINE("deadline") {
            public String execute(String taskInfo, List<Task> ls) {
                return addDeadline(taskInfo, ls);
            }
        },

        EVENT("event") {
            public String execute(String taskInfo, List<Task> ls) {
                return addEvent(taskInfo, ls);
            }
        },

        DELETE("delete") {
            public String execute(String taskInfo, List<Task> ls) {
                return removeTask(taskInfo, ls);
            }
        },

        DUE("due") {
            public String execute(String taskInfo, List<Task> ls) {
                return getDueTasks(taskInfo, ls);
            }
        },

        FIND("find") {
            public String execute(String taskInfo, List<Task> ls) {
                return findTask(taskInfo, ls);
            }
        },

        HELP("help") {
            public String execute(String taskInfo, List<Task> ls) {
                return getHelp();
            }
        },

        UNKNOWN("unknown") {
            public String execute(String taskInfo, List<Task> ls) {
                return "What the helly do you mean, please try again";
            }
        };

        private final String keyword;

        Command(String keyword) {
            this.keyword = keyword;
        }
        /**
        * Method that is responsible for printing responses
        *
        * @param taskInfo argument that was followed by the command
        * @param myList {@code List<Task>} that contains users tasks
         */
        public abstract String execute(String taskInfo, List<Task> myList);

        private boolean matches(String input) {
            return keyword.equalsIgnoreCase(input);
        }

        protected static Command fromString(String input) {
            assert input.split(" ").length == 1 : "Input string must be one word only";
            for (Command cmd: values()) {
                if (cmd.matches(input)) {
                    return cmd;
                }
            }
            return Command.UNKNOWN;
        }
    }
    private static String getHelp() {
        String helpContent = "";
        String resourcePath = "help/help.txt";

        try (InputStream inputStream = Parser.class.getClassLoader().getResourceAsStream(resourcePath)) {
            if (inputStream == null) {
                throw new IOException("Help file not found" + resourcePath);
            }

            Path tempFile = Files.createTempFile("help", "txt");
            Files.copy(inputStream, tempFile, StandardCopyOption.REPLACE_EXISTING);
            helpContent = Files.readString(tempFile);
            Files.delete(tempFile);
            return "King James says: \n" + helpContent;
        } catch (IOException e) {
            return "Nothing easy, even help files miss sometimes!";
        }
    }

    private static String findTask(String taskInfo, List<Task> ls) {
        List<Task> tempLs = new ArrayList<>();
        StringBuilder sb = new StringBuilder("King James found these matching tasks:\n");
        for (Task t: ls) {
            if (t.getName().contains(taskInfo)) {
                tempLs.add(t);
            }
        }

        if (tempLs.isEmpty()) {
            return "No buckets made with that search! Try again.";
        }

        for (Task t: tempLs) {
            sb.append(t.toString()).append('\n');
        }

        return sb.toString();
    }

    private static String getDueTasks(String taskInfo, List<Task> ls) {
        StringBuilder res = new StringBuilder();
        try {
            LocalDate by;
            if (taskInfo.isEmpty()) {
                by = LocalDate.now();
            } else {
                by = LocalDate.parse(taskInfo);
            }
            res.append("Tasks due by ").append(by).append(" (King's schedule):\n");
            List<Task> dueTasks = new ArrayList<>();

            for (Task t: ls) {
                LocalDate dueBy = t.dueBy();
                if (dueBy != null && dueBy.isBefore(by)) {
                    dueTasks.add(t);
                }
            }

            dueTasks.sort((x, y) -> x.dueBy().isBefore(y.dueBy()) ? -1 : 1);

            for (Task t: dueTasks) {
                res.append(t).append('\n');
            }
            return res.toString();
        } catch (DateTimeParseException e) {
            return "King James needs dates in YYYY-MM-DD format!";
        }
    }

    private static String displayList(String taskInfo, List<Task> ls) {
        if (ls.isEmpty()) {
            return "The King's court is empty!";
        } else {
            StringBuilder res = new StringBuilder("Behold the King's tasks: \n");
            for (int i = 1; i < ls.size() + 1; i++) {
                Task curTask = ls.get(i - 1);
                res.append(i).append(".").append(curTask.toString()).append('\n');
            }
            return res.toString();
        }
    }

    private static String removeTask(String taskInfo, List<Task> ls) {
        try {
            int idx = Integer.parseInt(taskInfo) - 1;
            if (ls.size() <= idx || idx < 0) {
                int tmpnum = idx + 1;
                return "Task " + tmpnum + " doesn't exist in the King's court!";
            } else {
                Task curTask = ls.remove(idx);
                return "King James has removed this task: \n"
                        + curTask + '\n'
                        + "Now you have " + ls.size() + " tasks left!";
            }
        } catch (NumberFormatException e) {
            return "The King needs a valid number after delete!";
        }
    }

    private static String markTask(String taskInfo, List<Task> ls) {
        StringBuilder sb = new StringBuilder();
        try {
            int idx = Integer.parseInt(taskInfo) - 1;
            if (ls.size() <= idx || idx < 0) {
                int tmpnum = idx + 1;
                sb.append("Task ").append(tmpnum).append(" doesn't exist in the King's court!\n");
            } else {
                Task curTask = ls.get(idx);
                curTask.markAsCompleted();
                sb.append("Crown this task! Marked as done:\n");
                sb.append(curTask).append("\n");
            }
        } catch (NumberFormatException e) {
            sb.append("The King needs a valid number after mark!\n");
        }
        return sb.toString();
    }

    private static String unmarkTask(String taskInfo, List<Task> ls) {
        StringBuilder sb = new StringBuilder();
        try {
            int idx = Integer.parseInt(taskInfo) - 1;
            if (ls.size() <= idx || idx < 0) {
                int tmpnum = idx + 1;
                sb.append("Task ").append(tmpnum).append(" doesn't exist in the King's court!\n");
            } else {
                Task curTask = ls.get(idx);
                curTask.unmarkAsCompleted();
                sb.append("This task is back in the game! Unmarked:\n");
                sb.append(curTask).append("\n");
            }
        } catch (NumberFormatException e) {
            sb.append("The King needs a valid number after unmark!\n");
        }
        return sb.toString();
    }

    private static String addTodo(String s, List<Task> myList) {
        StringBuilder sb = new StringBuilder();
        sb.append("King James has added a new task:\n");
        Task newTask = new ToDoTask(s);
        myList.add(newTask);
        sb.append(newTask).append("\n");
        sb.append("Now you have ").append(myList.size()).append(" tasks in the list\n");
        return sb.toString();
    }

    private static String addDeadline(String s, List<Task> myList) {
        StringBuilder sb = new StringBuilder();
        sb.append("King James is setting a deadline!\n");
        int idx = s.indexOf("/by");
        if (idx <= 0) {
            sb.append("The King demands: deadline 'name' /by YYYY-MM-DD\n");
            return sb.toString();
        }
        String name = s.substring(0, idx).trim();
        try {
            LocalDate by = LocalDate.parse(s.substring(idx + 3).trim());
            Task newTask = new DeadlineTask(name, by);
            myList.add(newTask);
            sb.append(newTask).append("\n");
            sb.append("Nothing easy! Deadline set!\n");
        } catch (DateTimeParseException e) {
            sb.append("The King demands: deadline 'name' /by YYYY-MM-DD\n");
        }
        return sb.toString();
    }

    private static String addEvent(String s, List<Task> myList) {
        StringBuilder sb = new StringBuilder();
        sb.append("King James is adding an event!\n");
        int idx0 = s.indexOf("/from");
        int idx1 = s.indexOf("/to");
        if (idx0 <= 0 || idx1 <= 0) {
            sb.append("The King demands: event 'name' /from YYYY-MM-DD /to YYYY-MM-DD\n");
            return sb.toString();
        }
        try {
            String name = s.substring(0, idx0).trim();
            LocalDate from = LocalDate.parse(s.substring(idx0 + 5, idx1).trim());
            LocalDate to = LocalDate.parse(s.substring(idx1 + 3).trim());
            Task newTask = new EventTask(name, from, to);
            myList.add(newTask);
            sb.append(newTask).append("\n");
            sb.append("Witness! Event added!\n");
        } catch (DateTimeParseException e) {
            sb.append("The King demands: event 'name' /from YYYY-MM-DD /to YYYY-MM-DD\n");
        }
        return sb.toString();
    }

}
