package ui;

import java.util.Scanner;
/**
 * Class that handles User Interaction in Lebron
*/
public class Ui {
    private static final String welcomeMsg = "Wassup, I'm lebron.Lebron. What popping homie?";
    private static final String exitMsg = "Im gonna bounce. See ya fam!";

    private final Scanner myObj;

    public Ui() {
        myObj = new Scanner(System.in);
    }

    public void printHorizontalLine() {
        int length = 50;
        char line = '-';

        for (int i = 0; i < length; i++) {
            System.out.print(line);
        }

        System.out.println();
    }

    /**
    * Function that prints
    * Ui have responsibility to print
     */
    public void print(String s) {
        System.out.println(s);
    }
    public void startUp() {
        System.out.println(welcomeMsg);
        printHorizontalLine();
    }

    public void exit() {
        System.out.println(exitMsg);
        printHorizontalLine();
    }

    public String getNextLine() {
        return myObj.nextLine();
    }
}
