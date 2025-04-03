package org.sau.devopsv2.service.impl;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.sau.devopsv2.entity.Task;
import org.sau.devopsv2.repository.TaskRepository;
import org.sau.devopsv2.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @Override
    public Task getTaskById(Long id) {
        return taskRepository.findById(id).orElse(null);
    }

    @Override
    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public Task updateTask(Long id, Task task) {
        Task existingTask = getTaskById(id);
        if (existingTask != null) {
            existingTask.setName(task.getName());
            existingTask.setDescription(task.getDescription());
            return taskRepository.save(existingTask);
        }
        return null;
    }



    @Override
    public List<Task> getTasksByName(String name) {
        return taskRepository.findByNameContaining(name);
    }

    @Override
    public Set<Task> getTasksByIds(Set<Long> taskIds) {
        return new HashSet<>(taskRepository.findAllById(taskIds));
    }
    @Transactional
    public void deleteTask(Long taskId) {
        entityManager.createNativeQuery("DELETE FROM taskers WHERE task_id = :taskId")
                .setParameter("taskId", taskId)
                .executeUpdate();

        entityManager.createNativeQuery("DELETE FROM tasks WHERE id = :taskId")
                .setParameter("taskId", taskId)
                .executeUpdate();
    }
}
