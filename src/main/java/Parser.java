import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class Parser {

    List<Task> taskList;

    public Parser(List<Task> ls) {
        this.taskList = ls;
    }
    public void parseUi(String userInput) {
        String[] splitUserInput = userInput.split(" ", 2);
        if (splitUserInput.length == 1) {
            splitUserInput = new String[]{userInput, ""};
        }
        Command.fromString(splitUserInput[0]).execute(splitUserInput[1], taskList);
    }

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

        DUE("due") {
            public void execute(String helly, List<Task> ls) { getDueTasks(helly, ls ); }
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

    public static void getDueTasks(String helly, List<Task> ls) {
        try {
            LocalDate by;
            if (helly.isEmpty()) {
                by = LocalDate.now();
            } else {
                by = LocalDate.parse(helly);
            }
            System.out.println("The following are tasks that are due by " + by);
            List<Task> dueTasks = new ArrayList<>();

            for (Task t: ls) {
                LocalDate dueBy = t.dueBy();
                if (dueBy != null && dueBy.isBefore(by)) {
                    dueTasks.add(t);
                }
            }

            dueTasks.sort((x, y) -> x.dueBy().isBefore(y.dueBy()) ? -1 : 1);

            for (Task t: dueTasks) {
                System.out.println(t);
            }

        } catch (DateTimeParseException e) {
            System.out.println("Please enter a value date YYYY-MM-DD");
        }
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
