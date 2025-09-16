package parser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import tasks.Task;
import tasks.ToDoTask;


public class ParserTest {
    @Test
    public void parseUi_singleArg_secondEmptyString() {
        Parser p = new Parser(new ArrayList<Task>());
        assertEquals(
                p.parseUi("list"),
                Parser.Command.fromString("list").execute("", new ArrayList<Task>())
        );
    }

    @Test
    public void parseUi_doubleArg_splitSuccess() {
        Parser p = new Parser(new ArrayList<Task>());
        assertEquals(
                p.parseUi("todo fried chicken"),
                Parser.Command.fromString("todo").execute("fried chicken", new ArrayList<Task>())
        );
    }

    @Test
    public void listCommand_emptyList_returnsEmptyMessage() {
        List<Task> emptyList = new ArrayList<>();
        String result = Parser.Command.LIST.execute("", emptyList);
        assertEquals("The King's court is empty!", result);
    }

    @Test
    public void listCommand_nonEmptyList_returnsFormattedList() {
        List<Task> taskList = new ArrayList<>();
        taskList.add(new ToDoTask("Test Task"));
        String result = Parser.Command.LIST.execute("", taskList);
        assertEquals("Behold the King's tasks: \n1.[T][ ] Test Task\n", result);
    }

    @Test
    public void markCommand_invalidIndex_returnsErrorMessage() {
        List<Task> taskList = new ArrayList<>();
        String result = Parser.Command.MARK.execute("5", taskList);
        assertEquals("Task 5 doesn't exist in the King's court!\n", result);
    }

    @Test
    public void unmarkCommand_invalidIndex_returnsErrorMessage() {
        List<Task> taskList = new ArrayList<>();
        String result = Parser.Command.UNMARK.execute("5", taskList);
        assertEquals("Task 5 doesn't exist in the King's court!\n", result);
    }

    @Test
    public void deleteCommand_invalidIndex_returnsErrorMessage() {
        List<Task> taskList = new ArrayList<>();
        String result = Parser.Command.DELETE.execute("5", taskList);
        assertEquals("Task 5 doesn't exist in the King's court!", result);
    }

    @Test
    public void dueCommand_invalidDate_returnsErrorMessage() {
        List<Task> taskList = new ArrayList<>();
        String result = Parser.Command.DUE.execute("invalid-date", taskList);
        assertEquals("King James needs dates in YYYY-MM-DD format!", result);
    }

    @Test
    public void deadlineCommand_invalidFormat_returnsErrorMessage() {
        List<Task> taskList = new ArrayList<>();
        String result = Parser.Command.DEADLINE.execute("test task", taskList);
        assertEquals(
                "King James is setting a deadline!\n" +
                        "The King demands: deadline 'name' /by YYYY-MM-DD\n",
                result);
    }

    @Test
    public void eventCommand_invalidFormat_returnsErrorMessage() {
        List<Task> taskList = new ArrayList<>();
        String result = Parser.Command.EVENT.execute("test event", taskList);
        assertEquals(
                "King James is adding an event!\n" +
                        "The King demands: event 'name' /from YYYY-MM-DD /to YYYY-MM-DD\n",
                result);
    }

    @Test
    public void unknownCommand_returnsErrorMessage() {
        List<Task> taskList = new ArrayList<>();
        String result = Parser.Command.UNKNOWN.execute("", taskList);
        assertEquals("What the helly do you mean, please try again", result);
    }

    @Test
    public void dueCommand_emptyDate_usesCurrentDate() {
        List<Task> taskList = new ArrayList<>();
        String result = Parser.Command.DUE.execute("", taskList);
        assert(result.contains("Tasks due by " + LocalDate.now() + " (King's schedule):"));
    }
}
