package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.IdGenerator;

public class TaskManager {
    private Map<String, Task> tasks = new HashMap<>();
    private static final String FILE_PATH = "taskslist.ser";
    private static final Logger LOGGER = Logger.getLogger(TaskManager.class.getName());

    public synchronized void addTask(Task task) {
        if (task == null) {
            throw new IllegalArgumentException("Record cannot be null.");
        }
        String id;
        do {
            id = IdGenerator.generateId(java.time.Year.now().getValue());
        } while (tasks.containsKey(id));
        task.setTaskId(id);
        tasks.put(id, task);
    }

    public synchronized Task deleteTask(String id) {
        return tasks.remove(id);
    }

    public synchronized Task findTask(String id) {
        return tasks.get(id);
    }

    public synchronized Map<String, Task> findAll() {
        return new HashMap<>(tasks);
    }

    public synchronized void setTask(Task task) {
        if (task != null && task.getTaskId() != null && tasks.containsKey(task.getTaskId())) {
            tasks.put(task.getTaskId(), task);
        }
    }

    @SuppressWarnings("unchecked")
    public synchronized void loadTasks() {
        File file = new File(FILE_PATH);
        if (!file.isFile()) {
            tasks = new HashMap<>();
            return;
        }
        try (ObjectInputStream input = new ObjectInputStream(new FileInputStream(file))) {
            Object data = input.readObject();
            if (data instanceof Map<?, ?>) {
                tasks = new HashMap<>((Map<String, Task>) data);
            } else {
                tasks = new HashMap<>();
            }
        } catch (IOException | ClassNotFoundException | ClassCastException ex) {
            LOGGER.log(Level.WARNING, "Unable to load saved records. Starting with an empty list.", ex);
            tasks = new HashMap<>();
        }
    }

    public synchronized int saveTasks() {
        try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            output.writeObject(tasks);
            return 0;
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, "Unable to save records.", ex);
            return 1;
        }
    }
}
