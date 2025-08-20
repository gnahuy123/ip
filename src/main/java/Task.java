public class Task {
    protected String name;
    protected boolean isCompleted;

    public Task(String name, boolean isCompleted) {
        this.name = name;
        this.isCompleted = isCompleted;
    }

    public Task(String name) {
        this.name = name;
        this.isCompleted = false;
    }

    public void markAsCompleted() {
        this.isCompleted = true;
    }

    public void unmarkAsCompleted() {
        this.isCompleted = false;
    }

    @Override
    public String toString() {
        char symbol = this.isCompleted ? 'X' : ' ';
        return "[" + symbol + "] " + name;
    }
}
