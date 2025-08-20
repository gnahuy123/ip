import java.util.Scanner;

public class Lebron {
    public static void main(String[] args) {
        Scanner myObj = new Scanner(System.in);
        String welcomeMsg = "Wassup, I'm Lebron. What popping homie?";
        String exitMsg = "Im gonna bounce. See ya fam!";

        //print welcome message
        System.out.println(welcomeMsg);
        printHorizontalLine();

        String userInput = myObj.nextLine();
        while (!userInput.equals("bye")) {
            printHorizontalLine();
            System.out.println(userInput);
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
}
