package parser;

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
            public String execute(String helly, List<Task> ls) {
                return displayList(helly, ls);
            }
        },

        MARK("mark") {
            public String execute(String helly, List<Task> ls) {
                return markTask(helly, ls);
            }
        },

        UNMARK("unmark") {
            public String execute(String helly, List<Task> ls) {
                return unmarkTask(helly, ls);
            }
        },

        TODO("todo") {
            public String execute(String helly, List<Task> ls) {
                return addTodo(helly, ls);
            }
        },

        DEADLINE("deadline") {
            public String execute(String helly, List<Task> ls) {
                return addDeadline(helly, ls);
            }
        },

        EVENT("event") {
            public String execute(String helly, List<Task> ls) {
                return addEvent(helly, ls);
            }
        },

        DELETE("delete") {
            public String execute(String helly, List<Task> ls) {
                return removeTask(helly, ls);
            }
        },

        DUE("due") {
            public String execute(String helly, List<Task> ls) {
                return getDueTasks(helly, ls);
            }
        },

        FIND("find") {
            public String execute(String helly, List<Task> ls) {
                return findTask(helly, ls);
            }
        },

        UNKNOWN("unknown") {
            public String execute(String helly, List<Task> ls) {
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
        * @param helly argument that was followed by the command
        * @param myList {@code List<Task>} that contains users tasks
         */
        public abstract String execute(String helly, List<Task> myList);

        private boolean matches(String input) {
            return keyword.equalsIgnoreCase(input);
        }

        protected static Command fromString(String input) {
            assert input.split(" ").length > 1 : "Input string must be one word only";
            for (Command cmd: values()) {
                if (cmd.matches(input)) {
                    return cmd;
                }
            }
            return Command.UNKNOWN;
        }
    }

    private static String findTask(String helly, List<Task> ls) {
        List<Task> tempLs = new ArrayList<>();
        StringBuilder sb = new StringBuilder("Here are the matching tasks in your list:\n");
        for (Task t: ls) {
            if (t.getName().contains(helly)) {
                tempLs.add(t);
            }
        }

        if (tempLs.isEmpty()) {
            return "No tasks match your search";
        }

        for (Task t: tempLs) {
            sb.append(t.toString()).append('\n');
        }

        return sb.toString();
    }

    private static String getDueTasks(String helly, List<Task> ls) {
        StringBuilder res = new StringBuilder();
        try {
            LocalDate by;
            if (helly.isEmpty()) {
                by = LocalDate.now();
            } else {
                by = LocalDate.parse(helly);
            }
            res.append("The following are tasks that are due by ").append(by).append('\n');
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
            return "Please enter a value date YYYY-MM-DD";
        }
    }
    private static String displayList(String helly, List<Task> ls) {
        if (ls.isEmpty()) {
            return "List is Empty";
        } else {
            StringBuilder res = new StringBuilder("Here are the tasks in your list: \n");
            for (int i = 1; i < ls.size() + 1; i++) {
                Task curTask = ls.get(i - 1);
                res.append(i).append(".").append(curTask.toString()).append('\n');
            }
            return res.toString();
        }
    }
    private static String removeTask(String helly, List<Task> ls) {
        try {
            int idx = Integer.parseInt(helly) - 1;
            if (ls.size() <= idx || idx < 0) {
                int tmpnum = idx + 1;
                return "tasks.Task " + tmpnum + " does not exist!";
            } else {
                Task curTask = ls.remove(idx);
                return "I've Removed this task from the list " + '\n'
                        + curTask + '\n'
                        + "Now you have " + ls.size() + " items left in the list!";
            }
        } catch (NumberFormatException e) {
            return "Please input a valid integer after delete ";
        }
    }

    private static String markTask(String helly, List<Task> ls) {
        StringBuilder sb = new StringBuilder();
        try {
            int idx = Integer.parseInt(helly) - 1;
            if (ls.size() <= idx || idx < 0) {
                int tmpnum = idx + 1; // corrected from idx++
                sb.append("tasks.Task ").append(tmpnum).append(" does not exist!\n");
            } else {
                Task curTask = ls.get(idx);
                curTask.markAsCompleted();
                sb.append("I've just marked this as done my G\n");
                sb.append(curTask).append("\n");
            }
        } catch (NumberFormatException e) {
            sb.append("Please input a valid integer after mark \n");
        }
        return sb.toString();
    }

    private static String unmarkTask(String helly, List<Task> ls) {
        StringBuilder sb = new StringBuilder();
        try {
            int idx = Integer.parseInt(helly) - 1;
            if (ls.size() <= idx || idx < 0) {
                int tmpnum = idx + 1;
                sb.append("tasks.Task ").append(tmpnum).append(" does not exist!\n");
            } else {
                Task curTask = ls.get(idx);
                curTask.unmarkAsCompleted();
                sb.append("This task is officially undone\n");
                sb.append(curTask).append("\n");
            }
        } catch (NumberFormatException e) {
            sb.append("Please input a valid integer after unmark \n");
        }
        return sb.toString();
    }

    private static String addTodo(String s, List<Task> myList) {
        StringBuilder sb = new StringBuilder();
        sb.append("No Problem G, I got you\n");
        Task newTask = new ToDoTask(s);
        myList.add(newTask);
        sb.append(newTask).append("\n");
        sb.append("Now you have ").append(myList.size()).append(" tasks in the list\n");
        return sb.toString();
    }

    private static String addDeadline(String s, List<Task> myList) {
        StringBuilder sb = new StringBuilder();
        sb.append("Looks like you want to add a task with deadline\n");
        int idx = s.indexOf("/by");
        if (idx <= 0) {
            sb.append("Deadline should have a format of deadline 'name' /by YYYY-MM-DD\n");
            return sb.toString();
        }
        String name = s.substring(0, idx).trim();
        try {
            LocalDate by = LocalDate.parse(s.substring(idx + 3).trim());
            Task newTask = new DeadlineTask(name, by);
            myList.add(newTask);
            sb.append(newTask).append("\n");
            sb.append("Done bro\n");
        } catch (DateTimeParseException e) {
            sb.append("Deadline should have a format of deadline 'name' /by YYYY-MM-DD\n");
        }
        return sb.toString();
    }

    private static String addEvent(String s, List<Task> myList) {
        StringBuilder sb = new StringBuilder();
        sb.append("Hmm an event interesting\n");
        int idx0 = s.indexOf("/from");
        int idx1 = s.indexOf("/to");
        if (idx0 <= 0 || idx1 <= 0) {
            sb.append("Event should have a format of event 'name' /from YYYY-MM-DD /to YYYY-MM-DD\n");
            return sb.toString();
        }
        try {
            String name = s.substring(0, idx0).trim();
            LocalDate from = LocalDate.parse(s.substring(idx0 + 5, idx1).trim());
            LocalDate to = LocalDate.parse(s.substring(idx1 + 3).trim());
            Task newTask = new EventTask(name, from, to);
            myList.add(newTask);
            sb.append(newTask).append("\n");
            sb.append("Added the task for you\n");
        } catch (DateTimeParseException e) {
            sb.append("Event should have a format of event 'name' /from YYYY-MM-DD /to YYYY-MM-DD\n");
        }
        return sb.toString();
    }

}
