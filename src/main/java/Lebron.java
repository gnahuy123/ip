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

            printHorizontalLine();
            if (userInput.equals("list")) {
                displayList(myList);
            } else if (userInput.split(" ")[0].equals("mark")) {
                markTask(userInput.split(" ")[1], myList, true);
            }  else if (userInput.split(" ")[0].equals("unmark")) {
                markTask(userInput.split(" ")[1], myList, false);
            }
            else {
                myList.add(new Task(userInput));
                System.out.println("added: " + userInput);
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

    public static void markTask(String helly, List<Task> ls, boolean toComplete) {
        try {
            int idx = Integer.parseInt(helly) - 1;
            if (ls.size() <= idx || idx < 0) {
                System.out.println("Task " + idx + "does not exist!");
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
}
