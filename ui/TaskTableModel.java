package ui;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import model.Task;

public class TaskTableModel extends AbstractTableModel {
        private final String[] columns = {
        "ID", "Name", "Brand", "Price", "Type", "Status"
    };

    private List<Task> tasks = new ArrayList<>();

    @Override
    public int getRowCount() {
        return tasks.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }

    @Override
    public Object getValueAt(int row, int column) {
        Task task = tasks.get(row);

            return switch (column) {
                case 0 -> task.getTaskId();
                case 1 -> task.getName();
                case 2 -> task.getBrand();
                case 3 -> "₱ " + task.getPrice();
                case 4 -> task.getType();
                case 5 -> task.getStatus();
                default -> "";
            };
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = new ArrayList<>(tasks);
        fireTableDataChanged();
    }

    public String getTaskId(int row) {
        return tasks.get(row).getTaskId();
    }

}
