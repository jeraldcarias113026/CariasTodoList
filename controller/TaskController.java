package controller;

import java.util.ArrayList;
import java.util.List;
import model.Task;
import model.TaskManager;
import ui.MainForm;

public class TaskController {
    private final TaskManager model;
    private MainForm view;

    public TaskController(TaskManager model) {
        this.model = model;
    }

    public void setView(MainForm view) {
        this.view = view;
    }

    public boolean handleAddTask(String name, String brand,
        String price, String type, String status) {

    if (isBlank(name) || isBlank(brand) || isBlank(price)
            || isBlank(type) || isBlank(status)) {
        return false;
    }

    Task task = new Task();

    task.setName(name.trim());
    task.setBrand(brand.trim());
    task.setPrice(price.trim());
    task.setType(type.trim());
    task.setStatus(status.trim());

    model.addTask(task);
    model.saveTasks();

    refreshView();
    return true;
}
   public boolean handleUpdateTask(String id, String name,
        String brand, String price, String type, String status) {

    if (isBlank(id) || isBlank(name) || isBlank(brand)
            || isBlank(price) || isBlank(type) || isBlank(status)) {
        return false;
    }

    Task task = model.findTask(id);

    if (task == null) {
        return false;
    }

    task.setName(name.trim());
    task.setBrand(brand.trim());
    task.setPrice(price.trim());
    task.setType(type.trim());
    task.setStatus(status.trim());

    model.setTask(task);
    model.saveTasks();

    refreshView();
    return true;
}

    public boolean handleDeleteTask(String id) {
        if (isBlank(id) || model.deleteTask(id) == null) {
            return false;
        }
        model.saveTasks();
        refreshView();
        return true;
    }

    public List<Task> getAllTask() {
        return new ArrayList<>(model.findAll().values());
    }

    public Task getTaskDetails(String id) {
        return model.findTask(id);
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    private void refreshView() {
        if (view != null) {
            view.refreshTaskList();
        }
    }
}
