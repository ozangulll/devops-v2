package org.sau.devopsv2.controller;

import org.sau.devopsv2.dto.TaskDTO;
import org.sau.devopsv2.entity.Task;
import org.sau.devopsv2.mapper.EntityMapper;
import org.sau.devopsv2.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private EntityMapper mapper;

    @GetMapping
    public List<TaskDTO> getAllTasks() {
        return mapper.convertToTaskDTOList(taskService.getAllTasks());
    }

    @GetMapping("/{id}")
    public TaskDTO getTaskById(@PathVariable Long id) {
        return mapper.convertToTaskDTO(taskService.getTaskById(id));
    }

    @PostMapping
    public TaskDTO createTask(@RequestBody TaskDTO taskDTO) {
        Task task = mapper.convertToTaskEntity(taskDTO);
        return mapper.convertToTaskDTO(taskService.createTask(task));
    }

    @PutMapping("/{id}")
    public TaskDTO updateTask(@PathVariable Long id, @RequestBody TaskDTO taskDTO) {
        Task task = mapper.convertToTaskEntity(taskDTO);
        return mapper.convertToTaskDTO(taskService.updateTask(id, task));
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
    }
}
