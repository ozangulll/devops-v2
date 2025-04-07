package org.sau.devopsv2.controller;

import lombok.RequiredArgsConstructor;
import org.sau.devopsv2.dto.TaskDTO;
import org.sau.devopsv2.dto.response.ApiResponse;
import org.sau.devopsv2.entity.Employee;
import org.sau.devopsv2.entity.Task;
import org.sau.devopsv2.entity.Tasker;
import org.sau.devopsv2.mapper.EntityMapper;
import org.sau.devopsv2.repository.EmployeeRepository;
import org.sau.devopsv2.repository.TaskRepository;
import org.sau.devopsv2.repository.TaskerRepository;
import org.sau.devopsv2.service.EmployeeService;
import org.sau.devopsv2.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/tasks")

public class TaskController {

    private final TaskService taskRepository;
    private final EmployeeService employeeRepository;
    private final TaskerRepository taskerRepository;
    private final EntityMapper mapper;

    public TaskController(TaskService taskRepository, EmployeeService employeeRepository, TaskerRepository taskerRepository, EntityMapper mapper) {
        this.taskRepository = taskRepository;
        this.employeeRepository = employeeRepository;
        this.taskerRepository = taskerRepository;
        this.mapper = mapper;
    }


    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createTask(@RequestBody TaskDTO taskDTO) {
        Task task = mapper.convertToTaskEntity(taskDTO);
        task = taskRepository.createTask(task);

        if (taskDTO.getEmployeeIds() != null) {
            for (Long empId : taskDTO.getEmployeeIds()) {
                Optional<Employee> empOpt = Optional.ofNullable(employeeRepository.getEmployeeById(empId));
                Task finalTask = task;
                empOpt.ifPresent(employee -> {
                    Tasker tasker = new Tasker();
                    tasker.setTask(finalTask);
                    tasker.setEmployee(employee);
                    taskerRepository.save(tasker);
                });
            }
        }

        ApiResponse response = new ApiResponse("Task created successfully with task id:" +task.getId(), HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }

    //Working
    @GetMapping
    public ResponseEntity<List<TaskDTO>> getAllTasks() {
        List<Task> tasks = taskRepository.getAllTasks();
        return ResponseEntity.ok(mapper.convertToTaskDTOList(tasks));
    }

    //working
    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable Long id) {
        Optional<Task> taskOpt = Optional.ofNullable(taskRepository.getTaskById(id));
        return taskOpt
                .map(task -> ResponseEntity.ok(mapper.convertToTaskDTO(task)))
                .orElse(ResponseEntity.notFound().build());
    }

    //working
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskRepository.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<TaskDTO> updateTask(@PathVariable Long id, @RequestBody TaskDTO taskDTO) {
        Optional<Task> taskOpt = Optional.ofNullable(taskRepository.getTaskById(id));
        if (taskOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Task task = taskOpt.get();
        task.setName(taskDTO.getName());
        task.setDescription(taskDTO.getDescription());
        task = taskRepository.updateTask(id,task);

        return ResponseEntity.ok(mapper.convertToTaskDTO(task));
    }
}
