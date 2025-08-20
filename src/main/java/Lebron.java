public class Lebron {
    public static void main(String[] args) {
        String welcomeMsg = "Wassup, I'm Lebron. What popping homie?";
        String exitMsg = "Im gonna bounce. See ya fam!";

        printHorizontalLine();

        System.out.println(welcomeMsg);

        printHorizontalLine();

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
