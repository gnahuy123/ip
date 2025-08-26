public class DeadlineTask extends Task {

    protected String by;

    public DeadlineTask(String name, String by) {
        super(name);
        this.by = by;
    }

    public DeadlineTask(String name, boolean isCompleted, String by) {
        super(name, isCompleted);
        this.by = by;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ')';
    }

    @Override
    public String toCSV() {
        return "Deadline," + super.toCSV() + "," + this.by + "\n";
    }
}
