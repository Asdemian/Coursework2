package service;

import exception.TaskNotFoundExceptio;
import model.Task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class TaskService {
    private static final Map<Integer, Task> TASKS = new HashMap<>();

    private TaskService() {
    }

    public static void add(Task task) {
        TASKS.put(task.getId(), task);
    }

    public static Collection<Task> getTasksByDay(LocalDate day) {
        Collection<Task> tasksByDay = new ArrayList<>();
        Collection<Task> allTasks = TASKS.values();
        for (Task task : allTasks) {
            LocalDateTime currentDateTime = task.getDateTime();
            if (currentDateTime.toLocalDate().equals(day)) {
                tasksByDay.add(task);
                continue;
            }
            LocalDateTime nexDateTime = currentDateTime;
            do {
                nexDateTime = task.getRepeat().nextTime(nexDateTime);
                if (nexDateTime == null) {
                    break;
                }
                if (nexDateTime.toLocalDate().equals(day)) {
                    tasksByDay.add(task);
                    break;
                }
            } while (nexDateTime.toLocalDate().isBefore(day));

        }
        return tasksByDay;
    }

    public static void removeById(int id) throws TaskNotFoundExceptio {
        if (TASKS.remove(id) == null) {
            throw new TaskNotFoundExceptio(id);
        }
    }
}
