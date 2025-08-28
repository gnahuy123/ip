package storage;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import tasks.DeadlineTask;
import tasks.EventTask;
import tasks.Task;
import tasks.ToDoTask;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StorageTest {

    @TempDir
    Path tempDir;

    @Test
    public void storeTasks_emptyList_createsEmptyFile() throws IOException {
        List<Task> taskList = new ArrayList<>();
        File testFile = new File(tempDir.toFile(), "test.csv");
        Storage storage = new Storage(taskList, testFile.getAbsolutePath());

        storage.storeTasks();

        assertTrue(testFile.exists());
        assertEquals(0, testFile.length());
    }

    @Test
    public void storeTasks_withTasks_writesCorrectFormat() throws IOException {
        List<Task> taskList = new ArrayList<>();
        taskList.add(new ToDoTask("Test task", true));
        taskList.add(new DeadlineTask("Deadline task", false, LocalDate.of(2023, 12, 31)));
        taskList.add(new EventTask("Event task", true,
                LocalDate.of(2023, 10, 1),
                LocalDate.of(2023, 10, 2)));

        File testFile = new File(tempDir.toFile(), "test.csv");
        Storage storage = new Storage(taskList, testFile.getAbsolutePath());

        storage.storeTasks();

        // Read the file and verify contents
        java.util.Scanner scanner = new java.util.Scanner(testFile);
        List<String> lines = new ArrayList<>();
        while (scanner.hasNextLine()) {
            lines.add(scanner.nextLine());
        }
        scanner.close();

        assertEquals(3, lines.size());
        assertTrue(lines.get(0).contains("Todo,Test task,true"));
        assertTrue(lines.get(1).contains("Deadline,Deadline task,false,2023-12-31"));
        assertTrue(lines.get(2).contains("Event,Event task,true,2023-10-01,2023-10-02"));
    }

    @Test
    public void loadTasksFromStorage_nonexistentFile_createsNewFile() throws IOException {
        List<Task> taskList = new ArrayList<>();
        File testFile = new File(tempDir.toFile(), "nonexistent.csv");
        Storage storage = new Storage(taskList, testFile.getAbsolutePath());

        storage.loadTasksFromStorage();

        assertTrue(testFile.exists());
        assertTrue(taskList.isEmpty());
    }

    @Test
    public void loadTasksFromStorage_existingFile_loadsTasksCorrectly() throws IOException {
        // Create a test file with known content
        File testFile = new File(tempDir.toFile(), "test.csv");
        try (FileWriter writer = new FileWriter(testFile)) {
            writer.write("Todo,Test task,true\n");
            writer.write("Deadline,Deadline task,false,2023-12-31\n");
            writer.write("Event,Event task,true,2023-10-01,2023-10-02\n");
        }

        List<Task> taskList = new ArrayList<>();
        Storage storage = new Storage(taskList, testFile.getAbsolutePath());

        storage.loadTasksFromStorage();

        assertEquals(3, taskList.size());

        // Verify Todo task
        assertEquals("Test task", taskList.get(0).getName());
        assertTrue(taskList.get(0).isCompleted());

        // Verify Deadline task
        assertEquals("Deadline task", taskList.get(1).getName());
        assertFalse(taskList.get(1).isCompleted());
        assertEquals(LocalDate.of(2023, 12, 31), taskList.get(1).dueBy());

        // Verify Event task
        assertEquals("Event task", taskList.get(2).getName());
        assertTrue(taskList.get(2).isCompleted());
    }

}