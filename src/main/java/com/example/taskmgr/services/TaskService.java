package com.example.taskmgr.services;

import com.example.taskmgr.entities.Task;
import org.springframework.stereotype.Service;

import javax.xml.stream.events.StartDocument;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class TaskService {
    private final List<Task> taskList;
    private AtomicInteger taskId = new AtomicInteger(0);

    public static class TaskNotFoundException extends IllegalStateException {
        public TaskNotFoundException(Integer id) {
            super("task with " + id + " not found");
        }
    }

    public TaskService() {
        this.taskList = new ArrayList<>();
        taskList.add(new Task(taskId.incrementAndGet(), "Task 1", "Description 1", "12-12-2022"));
        taskList.add(new Task(taskId.incrementAndGet(), "Task 2", "Description 2","11-11-2022"));
        taskList.add(new Task(taskId.incrementAndGet(), "Task 3", "Description 3", "11-11-2022"));

    }

    public List<Task> getTasks() {
        return taskList;
    }

    public Task createTask(
            String title,
            String description,
            String dueDate
    ) {
        //TODO: ensure date is not before today.
        var newTask = new Task(taskId.incrementAndGet(), title, description, dueDate);
        taskList.add(newTask);
        return newTask;
    }

    public Task getTaskById(Integer id) {
        return taskList.stream()
                .filter(task -> task.getId().equals(id)).findFirst()
                .orElseThrow(() -> new TaskNotFoundException(id));
    }

    public Task updateTask(
            Integer id,
            String title,
            String description,
            String dueDate
    ) {
        var task = getTaskById(id);
        if (task == null) {
            throw new TaskNotFoundException(id);
        }
        if(title !=null) task.setTitle(title);
        if(description !=null) task.setDescription(description);
        if(dueDate !=null) task.setDueDate(dueDate);

        return task;
    }
    public Task deleteTask(Integer id) {
        var task = getTaskById(id);
        if(task == null) {
            throw new TaskNotFoundException(id);
        }
        taskList.remove(task);
        return task;
    }

}
