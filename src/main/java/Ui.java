public class Ui {
    public Ui() {}

    public  void printHorizontalLine() {
        int length = 50;
        char line = '-';

        for (int i = 0; i < length; i++) {
            System.out.print(line);
        }

        System.out.println();
    }
}
