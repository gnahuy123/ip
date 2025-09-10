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
        assertEquals("List is Empty", result);
    }

    @Test
    public void listCommand_nonEmptyList_returnsFormattedList() {
        List<Task> taskList = new ArrayList<>();
        taskList.add(new ToDoTask("Test Task"));
        String result = Parser.Command.LIST.execute("", taskList);
        assertEquals("Here are the tasks in your list: \n1.[T][ ] Test Task\n", result);
    }

    @Test
    public void markCommand_invalidIndex_returnsErrorMessage() {
        List<Task> taskList = new ArrayList<>();
        String result = Parser.Command.MARK.execute("5", taskList);
        assertEquals("Task 5 does not exist!\n", result);
    }

    @Test
    public void unmarkCommand_invalidIndex_returnsErrorMessage() {
        List<Task> taskList = new ArrayList<>();
        String result = Parser.Command.UNMARK.execute("5", taskList);
        assertEquals("Task 5 does not exist!\n", result);
    }

    @Test
    public void deleteCommand_invalidIndex_returnsErrorMessage() {
        List<Task> taskList = new ArrayList<>();
        String result = Parser.Command.DELETE.execute("5", taskList);
        assertEquals("tasks.Task 5 does not exist!", result);
    }

    @Test
    public void dueCommand_invalidDate_returnsErrorMessage() {
        List<Task> taskList = new ArrayList<>();
        String result = Parser.Command.DUE.execute("invalid-date", taskList);
        assertEquals("Please enter a value date YYYY-MM-DD", result);
    }

    @Test
    public void deadlineCommand_invalidFormat_returnsErrorMessage() {
        List<Task> taskList = new ArrayList<>();
        String result = Parser.Command.DEADLINE.execute("test task", taskList);
        assertEquals(
                """
                        Looks like you want to add a task with deadline
                        Deadline should have a format of deadline 'name' /by YYYY-MM-DD
                        """, result);
    }

    @Test
    public void eventCommand_invalidFormat_returnsErrorMessage() {
        List<Task> taskList = new ArrayList<>();
        String result = Parser.Command.EVENT.execute("test event", taskList);
        assertEquals("""
                Hmm an event interesting
                Event should have a format of event 'name' /from YYYY-MM-DD /to YYYY-MM-DD
                """, result);
    }

    @Test
    public void unknownCommand_returnsErrorMessage() {
        List<Task> taskList = new ArrayList<>();
        String result = Parser.Command.UNKNOWN.execute("", taskList);
        assertEquals("What the taskInfo do you mean, please try again", result);
    }

    @Test
    public void dueCommand_emptyDate_usesCurrentDate() {
        List<Task> taskList = new ArrayList<>();
        String result = Parser.Command.DUE.execute("", taskList);
        assert(result.contains("The following are tasks that are due by " + LocalDate.now()));
    }
}
