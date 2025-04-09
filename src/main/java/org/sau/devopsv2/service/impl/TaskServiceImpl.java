package org.sau.devopsv2.service.impl;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.sau.devopsv2.entity.Task;
import org.sau.devopsv2.exception.CustomException;
import org.sau.devopsv2.repository.TaskRepository;
import org.sau.devopsv2.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;
    /*
    @Autowired
    private EntityManager entityManager;

     */

    @Override
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @Override
    public Task getTaskById(Long id) {
        Optional<Task> task = taskRepository.findById(id);
        if (task.isEmpty()) {
            throw new CustomException("Task not found with ID: " + id);
        }
        return task.get();
    }

    @Override
    public Task createTask(Task task) {
        // Check if task with same name already exists
        Task existingTask = taskRepository.findByName(task.getName());
        if (existingTask != null) {
            throw new CustomException("Task with name '" + task.getName() + "' already exists.");
        }
        return taskRepository.save(task);
    }


    @Override
    public Task updateTask(Long id, Task task) {
        Task existingTask = getTaskById(id);
        if (existingTask == null) {
            throw new CustomException("Cannot update. Task not found with ID: " + id);
        }
        existingTask.setName(task.getName());
        existingTask.setDescription(task.getDescription());
        existingTask.setTaskers(task.getTaskers());
        return taskRepository.save(existingTask);
    }

    @Override
    public void deleteTask(Long id) {
        Task task = getTaskById(id);
        if (task == null) {
            throw new CustomException("Task with ID: " + id + " does not exist. Cannot delete.");
        }
        taskRepository.deleteById(id);
    }

    @Override
    public List<Task> getTasksByName(String name) {
        return taskRepository.findByNameContaining(name);
    }

    @Override
    public Set<Task> getTasksByIds(Set<Long> taskIds) {
        Set<Task> tasks = new HashSet<>(taskRepository.findAllById(taskIds));
        if (tasks.isEmpty()) {
            throw new CustomException("No tasks found with provided IDs.");
        }
        return tasks;
    }

    /*
    @Transactional
    public void deleteTask(Long taskId) {
        entityManager.createNativeQuery("DELETE FROM taskers WHERE task_id = :taskId")
                .setParameter("taskId", taskId)
                .executeUpdate();

        entityManager.createNativeQuery("DELETE FROM tasks WHERE id = :taskId")
                .setParameter("taskId", taskId)
                .executeUpdate();
    }
     */
}
