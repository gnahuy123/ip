import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Lebron {
    public static void main(String[] args) {
        Scanner myObj = new Scanner(System.in);
        String welcomeMsg = "Wassup, I'm Lebron. What popping homie?";
        String exitMsg = "Im gonna bounce. See ya fam!";
        List<String> myList = new ArrayList<>();

        //print welcome message
        System.out.println(welcomeMsg);
        printHorizontalLine();

        String userInput = myObj.nextLine();
        while (!userInput.equals("bye")) {

            printHorizontalLine();
            if (userInput.equals("list")) {
                printList(myList);
            } else {
                myList.add(userInput);
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

    public static void printList(List<String> ls) {
        if (ls.isEmpty()) {
            System.out.println("List is Empty");
        } else {
            for (int i = 1; i < ls.size() + 1; i++) {
                System.out.println(i + ". " + ls.get(i-1));
            }
        }
    }
}
