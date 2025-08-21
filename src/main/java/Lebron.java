import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Lebron {
    public static void main(String[] args) {
        Scanner myObj = new Scanner(System.in);
        String welcomeMsg = "Wassup, I'm Lebron. What popping homie?";
        String exitMsg = "Im gonna bounce. See ya fam!";
        List<Task> myList = new ArrayList<>();

        //print welcome message
        System.out.println(welcomeMsg);
        printHorizontalLine();

        String userInput = myObj.nextLine();
        while (!userInput.equals("bye")) {
            String[] splitUserInput = userInput.split(" ", 2);
            printHorizontalLine();
            if (userInput.equals("list")) {
                displayList(myList);
            } else if (splitUserInput[0].equals("mark")) {
                markTask(splitUserInput[1], myList, true);
            } else if (splitUserInput[0].equals("unmark")) {
                markTask(splitUserInput[1], myList, false);
            } else if (splitUserInput[0].equals("todo")) {
                addTodo(splitUserInput[1], myList);
            } else if (splitUserInput[0].equals("deadline")) {
                addDeadline(splitUserInput[1], myList);
            } else if (splitUserInput[0].equals("event")) {
                addEvent(splitUserInput[1], myList);
            } else if (splitUserInput[0].equals("delete")) {
                removeTask(splitUserInput[1], myList);
            }else {
                System.out.println("What the helly do you mean? Please try again");
            }
            printHorizontalLine();
            userInput = myObj.nextLine();
        }
        printHorizontalLine();

        //print exit message
        System.out.println(exitMsg);
        printHorizontalLine();
    }

    public static void printHorizontalLine() {
        int length = 50;
        char line = '-';

        for (int i = 0; i < length; i++) {
            System.out.print(line);
        }

        System.out.println();
    }

    public static void displayList(List<Task> ls) {
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
                int tmpnum = idx++;
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

    public static void markTask(String helly, List<Task> ls, boolean toComplete) {
        try {
            int idx = Integer.parseInt(helly) - 1;
            if (ls.size() <= idx || idx < 0) {
                int tmpnum = idx++;
                System.out.println("Task " + tmpnum + " does not exist!");
            } else if (toComplete){
                Task curTask = ls.get(idx);
                curTask.markAsCompleted();
                System.out.println("I've just marked this as done my G");
                System.out.println(curTask);
            } else {
                Task curTask = ls.get(idx);
                curTask.unmarkAsCompleted();
                System.out.println("This task is officially undone");
                System.out.println(curTask);
            }
        } catch (NumberFormatException e) {
            System.out.println("Please input a valid integer after mark ");
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
            System.out.println("Deadline should have a format of deadline 'name' /by 'time'");
            return;
        }
        String name = s.substring(0,idx).trim();
        String by = s.substring(3 + idx).trim();
        Task newTask = new DeadlineTask(name, by);
        myList.add(newTask);
        System.out.println(newTask);
        System.out.println("Done bro");
    }

    public static void addEvent(String s, List<Task> myList) {
        System.out.println("Hmm an event interesting");
        int idx0 = s.indexOf("/from");
        int idx1 = s.indexOf("/to");
        if (idx0 <= 0 || idx1 <= 0) {
            System.out.println("Event should have a format of event 'name' /from 'start' /to 'finish'");
            return;
        }
        String name = s.substring(0,idx0).trim();
        String from = s.substring(5+idx0,idx1).trim();
        String to = s.substring(idx1+3).trim();
        Task newTask = new EventTask(name,from,to);
        myList.add(newTask);
        System.out.println(newTask);
        System.out.println("Added the task for you");
    }
}
