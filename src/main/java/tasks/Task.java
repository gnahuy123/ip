package tasks;

import java.time.LocalDate;

abstract public class Task {
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

    public LocalDate dueBy() {
        return null;
    }

    public void markAsCompleted() {
        this.isCompleted = true;
    }

    public void unmarkAsCompleted() {
        this.isCompleted = false;
    }

    public String toCSV() {
        return name + "," + (isCompleted ? "true" : "false");
    }
    @Override
    public String toString() {
        char symbol = this.isCompleted ? 'X' : ' ';
        return "[" + symbol + "] " + name;
    }
}
