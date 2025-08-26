import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DeadlineTask extends Task {

    protected LocalDate by;

    public DeadlineTask(String name, LocalDate by) {
        super(name);
        this.by = by;
    }

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
    public String toCSV() {
        return "Deadline," + super.toCSV() + "," +  this.by + "\n";
    }
}
