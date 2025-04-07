package org.sau.devopsv2.controller;

import org.sau.devopsv2.dto.TaskDTO;
import org.sau.devopsv2.dto.response.ApiResponse;
import org.sau.devopsv2.entity.Employee;
import org.sau.devopsv2.entity.Task;
import org.sau.devopsv2.entity.Tasker;
import org.sau.devopsv2.mapper.EntityMapper;
import org.sau.devopsv2.repository.EmployeeRepository;
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

    private final TaskService taskService;
    private final EmployeeService employeeService;
    private final TaskerRepository taskerRepository;
    private final EmployeeRepository employeeRepository;
    private final EntityMapper mapper;

    public TaskController(TaskService taskService, EmployeeService employeeService, TaskerRepository taskerRepository, EmployeeRepository employeeRepository, EntityMapper mapper) {
        this.taskService = taskService;
        this.employeeService = employeeService;
        this.taskerRepository = taskerRepository;
        this.employeeRepository = employeeRepository;
        this.mapper = mapper;
    }

    //working
    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createTask(@RequestBody TaskDTO taskDTO) {
        Task task = mapper.convertToTaskEntity(taskDTO);
        task = taskService.createTask(task);

        if (taskDTO.getEmployeeIds() != null) {
            for (Long empId : taskDTO.getEmployeeIds()) {
                Optional<Employee> empOpt = Optional.ofNullable(employeeService.getEmployeeById(empId));
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
        List<Task> tasks = taskService.getAllTasks();
        return ResponseEntity.ok(mapper.convertToTaskDTOList(tasks));
    }

    //working
    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable Long id) {
        Optional<Task> taskOpt = Optional.ofNullable(taskService.getTaskById(id));
        return taskOpt
                .map(task -> ResponseEntity.ok(mapper.convertToTaskDTO(task)))
                .orElse(ResponseEntity.notFound().build());
    }

    //working
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateTask(@PathVariable Long id, @RequestBody TaskDTO taskDTO) {
        Optional<Task> taskOpt = Optional.ofNullable(taskService.getTaskById(id));
        if (taskOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Task task1 = taskOpt.get();
        task1.setName(taskDTO.getName());
        task1.setDescription(taskDTO.getDescription());

        if (taskDTO.getEmployeeIds() != null) {
            // Remove old tasker relations
            Set<Tasker> existingTaskers = task1.getTaskers();
            taskerRepository.deleteAll(existingTaskers);

            // Add new tasker relations based on taskIds
            for (Long employeeId : taskDTO.getEmployeeIds()) {
                Employee finalEmp = employeeService.getEmployeeById(employeeId);
                Task finalTask = task1;

                Tasker tasker = new Tasker();
                tasker.setEmployee(finalEmp);
                tasker.setTask(finalTask);
                taskerRepository.save(tasker);
            }
        }
        // Task'ı güncelle
        task1 = taskService.updateTask(id, task1);

        ApiResponse response = new ApiResponse("Task updated successfully with task id:" +task1.getId(), HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }
}
