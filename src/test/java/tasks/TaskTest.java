package tasks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class TaskTest {

    @Test
    public void toDoTask_constructor_setsNameCorrectly() {
        ToDoTask task = new ToDoTask("Buy groceries");
        assertEquals("Buy groceries", task.getName());
        assertFalse(task.isCompleted());
    }

    @Test
    public void toDoTask_constructorWithCompletion_setsStatusCorrectly() {
        ToDoTask task = new ToDoTask("Completed task", true);
        assertEquals("Completed task", task.getName());
        assertTrue(task.isCompleted());
    }

    @Test
    public void toDoTask_toString_returnsCorrectFormat() {
        ToDoTask task = new ToDoTask("Test task");
        assertEquals("[T][ ] Test task", task.toString());

        task.markAsCompleted();
        assertEquals("[T][X] Test task", task.toString());
    }

    @Test
    public void toDoTask_toCsv_returnsCorrectFormat() {
        ToDoTask task = new ToDoTask("Test task");
        assertEquals("Todo,Test task,false\n", task.toCsv());

        task.markAsCompleted();
        assertEquals("Todo,Test task,true\n", task.toCsv());
    }

    @Test
    public void toDoTask_dueBy_returnsNull() {
        ToDoTask task = new ToDoTask("Test task");
        assertNull(task.dueBy());
    }

    @Test
    public void deadlineTask_constructor_setsFieldsCorrectly() {
        LocalDate dueDate = LocalDate.of(2024, 12, 31);
        DeadlineTask task = new DeadlineTask("Submit report", dueDate);

        assertEquals("Submit report", task.getName());
        assertFalse(task.isCompleted());
        assertEquals(dueDate, task.dueBy());
    }

    @Test
    public void deadlineTask_toString_formatsDateCorrectly() {
        LocalDate dueDate = LocalDate.of(2024, 1, 15);
        DeadlineTask task = new DeadlineTask("Submit report", dueDate);

        assertTrue(task.toString().contains("[D]"));
        assertTrue(task.toString().contains("Submit report"));
        assertTrue(task.toString().contains("Jan 15, 2024"));
    }

    @Test
    public void deadlineTask_toCsv_returnsCorrectFormat() {
        LocalDate dueDate = LocalDate.of(2024, 12, 31);
        DeadlineTask task = new DeadlineTask("Submit report", dueDate);

        assertEquals("Deadline,Submit report,false,2024-12-31\n", task.toCsv());
    }

    @Test
    public void eventTask_constructor_setsFieldsCorrectly() {
        LocalDate startDate = LocalDate.of(2024, 6, 1);
        LocalDate endDate = LocalDate.of(2024, 6, 3);
        EventTask task = new EventTask("Conference", startDate, endDate);

        assertEquals("Conference", task.getName());
        assertFalse(task.isCompleted());
        assertEquals(startDate, task.dueBy()); // EventTask returns start date as dueBy
    }

    @Test
    public void eventTask_toString_formatsDateCorrectly() {
        LocalDate startDate = LocalDate.of(2024, 6, 1);
        LocalDate endDate = LocalDate.of(2024, 6, 3);
        EventTask task = new EventTask("Conference", startDate, endDate);

        String result = task.toString();
        assertTrue(result.contains("[E]"));
        assertTrue(result.contains("Conference"));
        assertTrue(result.contains("Jun 1, 2024"));
        assertTrue(result.contains("Jun 3, 2024"));
    }

    @Test
    public void eventTask_toCsv_returnsCorrectFormat() {
        LocalDate startDate = LocalDate.of(2024, 6, 1);
        LocalDate endDate = LocalDate.of(2024, 6, 3);
        EventTask task = new EventTask("Conference", startDate, endDate);

        assertEquals("Event,Conference,false,2024-06-01,2024-06-03\n", task.toCsv());
    }

    @Test
    public void task_markAndUnmark_changesStatusCorrectly() {
        ToDoTask task = new ToDoTask("Test task");
        assertFalse(task.isCompleted());

        task.markAsCompleted();
        assertTrue(task.isCompleted());

        task.unmarkAsCompleted();
        assertFalse(task.isCompleted());
    }
}
