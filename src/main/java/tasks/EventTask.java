package tasks;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
* Event Task that has a start time and end time.
 */
public class EventTask extends Task {

    protected LocalDate from;
    protected LocalDate to;

    /**
    * Constructor that creates EventTask.
    *
    * @param name/description of task.
    * @param from start date.
    * @param to end date.
     */
    public EventTask(String name, LocalDate from, LocalDate to) {
        super(name);
        this.from = from;
        this.to = to;
    }

    /**
     * Constructor that creates EventTask while allowing created of marked tasks.
     *
     * @param name/description of task.
     * @param isCompleted completion status of task.
     * @param from start date.
     * @param to end date.
     */
    public EventTask(String name, boolean isCompleted, LocalDate from, LocalDate to) {
        super(name, isCompleted);
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " +
                from.format(DateTimeFormatter.ofPattern("MMM d, uuuu")) +
                " to: " +
                to.format(DateTimeFormatter.ofPattern("MMM d, uuuu")) + ')';
    }

    @Override
    public LocalDate dueBy() {
        return from;
    }

    @Override
    public String toCSV() {
        return "Event," + super.toCSV() + "," + this.from.toString() + "," + this.to.toString() + "\n";
    }

}
