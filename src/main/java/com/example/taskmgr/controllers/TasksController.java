package com.example.taskmgr.controllers;

import com.example.taskmgr.dtos.ErrorResponse;
import com.example.taskmgr.entities.Task;
import com.example.taskmgr.services.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class TasksController {
    private TaskService taskService;

    public TasksController(TaskService taskService) {
        this.taskService = taskService;
    }
    @GetMapping("/tasks")
    ResponseEntity<List<Task>> getTask() {
        return ResponseEntity.ok(taskService.getTasks());
    }

    @PostMapping("/tasks")
    ResponseEntity<Task> createTask(@RequestBody Task task) {
        var newTask= taskService.createTask(task.getTitle(), task.getDescription(), task.getDueDate().toString());
        return ResponseEntity.created(URI.create("/tasks/"+ newTask.getId())).body(newTask);
    }

    @GetMapping("/tasks/{id}")
    ResponseEntity<Task> getTask(@PathVariable("id") Integer id,@RequestBody Task task) {
        return ResponseEntity.ok(taskService.getTaskById(id));
    }
    @PatchMapping("/tasks/{id}")
    ResponseEntity<Task> updateTask(@PathVariable("id") Integer id, @RequestBody Task task) {
      //TODO: for later
        var updatedTask = taskService.updateTask(
                id,
                task.getTitle(),
                task.getDescription(),
                task.getDueDate()
        );

      return ResponseEntity.accepted().body(updatedTask);

    }

    @DeleteMapping("/tasks/{id}")
    ResponseEntity<Task> deleteTask(@PathVariable("id") Integer id, @RequestBody Task task) {
        //TODO: for later
        return ResponseEntity.accepted().body(taskService.deleteTask(id));
    }

    @ExceptionHandler(TaskService.TaskNotFoundException.class)
    ResponseEntity<ErrorResponse> handlerError(TaskService.TaskNotFoundException e) {
        //return ResponseEntity.notFound().build(); //Or you can use
        return new ResponseEntity<>(
                new ErrorResponse(e.getMessage()),
                HttpStatus.NOT_FOUND
        );
    }
}
