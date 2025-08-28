package tasks;

/*
* Most basic task that just has a name
 */
public class ToDoTask extends Task {

    /*
    * Construction of task.
    *
    * @param name name/description.
     */
    public ToDoTask(String name) {
        super(name);
    }

    /*
     * Construction of task but with ability of make marked tasks
     *
     * @param name name/description.
     * @param isCompleted completion staytus of task.
     */
    public ToDoTask(String name, boolean isCompleted) {
        super(name, isCompleted);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    @Override
    public String toCsv() {
        return "Todo," + super.toCsv() + "\n";
    }
}
