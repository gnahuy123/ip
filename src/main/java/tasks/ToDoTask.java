package tasks;

public class ToDoTask extends Task {
    public ToDoTask(String name) {
        super(name);
    }

    public ToDoTask(String name, boolean isCompleted) {
        super(name, isCompleted);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    @Override
    public String toCSV() {
        return "Todo," + super.toCSV() + "\n";
    }
}
