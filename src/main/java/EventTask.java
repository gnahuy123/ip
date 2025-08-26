import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EventTask extends Task {

    protected LocalDate from;
    protected LocalDate to;

    public EventTask(String name, LocalDate from, LocalDate to) {
        super(name);
        this.from = from;
        this.to = to;
    }

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
