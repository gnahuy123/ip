import java.time.LocalDate;

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
        return "[D]" + super.toString() + " (by: " + by + ')';
    }

    @Override
    public String toCSV() {
        return "Deadline," + super.toCSV() + "," + this.by.toString() + "\n";
    }
}
