package tasks;

import java.time.LocalDate;
/*
* Abstract class Task that is the superclass to other concrete tasks.
 */
abstract public class Task {
    protected String name;
    protected boolean isCompleted;

    /*
    * Constructor for Task with.
    *
    * @param name description or name of task.
    * @param isCompleted used to declare if tasks are done already.
     */
    public Task(String name, boolean isCompleted) {
        this.name = name;
        this.isCompleted = isCompleted;
    }

    /*
     * Constructor for Task.
     *
     * @param name description or name of task.
     */
    public Task(String name) {
        this.name = name;
        this.isCompleted = false;
    }

    /*
    * Method that returns when the Task is due.
    *
    * @return Due Date in LocalDate type.
     */
    public LocalDate dueBy() {
        return null;
    }

    public void markAsCompleted() {
        this.isCompleted = true;
    }

    public void unmarkAsCompleted() {
        this.isCompleted = false;
    }

    /*
    * Converts Task into CSV form.
    *
    * @return csv from.
     */
    public String toCSV() {
        return name + "," + (isCompleted ? "true" : "false");
    }

    @Override
    public String toString() {
        char symbol = this.isCompleted ? 'X' : ' ';
        return "[" + symbol + "] " + name;
    }
}
