package ui;

import java.util.Scanner;

public class Ui {
    Scanner myObj;
    static String welcomeMsg = "Wassup, I'm lebron.Lebron. What popping homie?";
    static String exitMsg = "Im gonna bounce. See ya fam!";

    public Ui() {
        myObj = new Scanner(System.in);
    }

    public  void printHorizontalLine() {
        int length = 50;
        char line = '-';

        for (int i = 0; i < length; i++) {
            System.out.print(line);
        }

        System.out.println();
    }

    /*
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
