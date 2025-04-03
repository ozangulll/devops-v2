package org.sau.devopsv2.service;

import org.sau.devopsv2.entity.Task;

import java.util.List;
import java.util.Set;

public interface TaskService {
    List<Task> getAllTasks();
    Task getTaskById(Long id);
    Task createTask(Task task);
    Task updateTask(Long id, Task task);
    void deleteTask(Long id);
    List<Task> getTasksByName(String name);
    Set<Task> getTasksByIds(Set<Long> ids);
}
