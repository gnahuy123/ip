public class EventTask extends Task {


    protected String from;
    protected String to;

    public EventTask(String name, String from, String to) {
        super(name);
        this.from = from;
        this.to = to;
    }

    public EventTask(String name, boolean isCompleted, String from, String to) {
        super(name, isCompleted);
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from + " to: " + to + ')';
    }

    @Override
    public String toCSV() {
        return "Event," + super.toCSV() + "," + this.from + "," + this.to + "\n";
    }

}
