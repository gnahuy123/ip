package tasks;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
/**
* Task that has a by field
 */
public class DeadlineTask extends Task {

    protected LocalDate by;

    /**
    * Constructor for deadline task.
    *
    * @param name name/description.
    * @param by due date of Task.
     */
    public DeadlineTask(String name, LocalDate by) {
        super(name);
        this.by = by;
    }

    /**
     * Constructor for deadline task but with ability to make marked tasks.
     *
     * @param name name/description.
     * @param isCompleted completion status of task
     * @param by due date of Task.
     */
    public DeadlineTask(String name, boolean isCompleted, LocalDate by) {
        super(name, isCompleted);
        this.by = by;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by.format(DateTimeFormatter.ofPattern("MMM d, uuuu")) + ')';
    }

    @Override
    public LocalDate dueBy() {
        return by;
    }

    @Override
    public String toCsv() {
        return "Deadline," + super.toCsv() + "," + this.by + "\n";
    }
}
