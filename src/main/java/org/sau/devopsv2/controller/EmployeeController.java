package org.sau.devopsv2.controller;

import org.sau.devopsv2.dto.EmployeeDTO;
import org.sau.devopsv2.dto.response.ApiResponse;
import org.sau.devopsv2.entity.Employee;
import org.sau.devopsv2.entity.Task;
import org.sau.devopsv2.entity.Tasker;
import org.sau.devopsv2.mapper.EntityMapper;
import org.sau.devopsv2.repository.TaskerRepository;
import org.sau.devopsv2.service.EmployeeService;
import org.sau.devopsv2.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/employees")

public class EmployeeController {
    @Autowired
    private final EmployeeService employeeService;
    @Autowired
    private final TaskService taskService;
    @Autowired
    private final TaskerRepository taskerRepository;
    @Autowired
    private final EntityMapper mapper;

    public EmployeeController(EmployeeService employeeService, TaskService taskService, TaskerRepository taskerRepository, EntityMapper mapper) {
        this.employeeService = employeeService;
        this.taskService = taskService;
        this.taskerRepository = taskerRepository;
        this.mapper = mapper;

    }

    //working
    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee employee = mapper.convertToEmployeeEntity(employeeDTO);
        employee = employeeService.createEmployee(employee);
        // taskIds null değilse -> Tasker ilişkisi kur
        if (employeeDTO.getTaskIds() != null) {
            for (Long taskId : employeeDTO.getTaskIds()) {
                Optional<Task> taskOpt = Optional.ofNullable(taskService.getTaskById(taskId));
                Employee finalEmployee = employee;
                taskOpt.ifPresent(task -> {
                    Tasker tasker = new Tasker();
                    tasker.setEmployee(finalEmployee);
                    tasker.setTask(task);
                    taskerRepository.save(tasker);
                });
            }
        }
        ApiResponse response = new ApiResponse("Employee created successfully with employee id:" +employee.getId(), HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }

    // working
    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(mapper.convertToEmployeeDTOList(employees));
    }

    // working
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable Long id) {
        Optional<Employee> employeeOpt = Optional.ofNullable(employeeService.getEmployeeById(id));
        return employeeOpt
                .map(employee -> ResponseEntity.ok(mapper.convertToEmployeeDTO(employee)))
                .orElse(ResponseEntity.notFound().build());
    }

    // working
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        ApiResponse response = new ApiResponse("Employee deleted successfully with id: " + id, HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }
    //working
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateEmployee(@PathVariable Long id, @RequestBody EmployeeDTO employeeDTO) {
        Employee employee = employeeService.getEmployeeById(id);

        if (employee == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse("Employee not found with id: " + id, HttpStatus.NOT_FOUND.value()));
        }

        // Update basic info
        employee.setName(employeeDTO.getName());
        employee.setDepartment(employeeDTO.getDepartment());

        // Remove old taskers
        Set<Tasker> existingTaskers = employee.getTaskers();
        if (existingTaskers != null && !existingTaskers.isEmpty()) {
            taskerRepository.deleteAll(existingTaskers);
            existingTaskers.clear(); // İlişkiyi koparmak için
        }

        // Add new taskers if taskIds are provided
        if (employeeDTO.getTaskIds() != null) {
            for (Long taskId : employeeDTO.getTaskIds()) {
                Task task = taskService.getTaskById(taskId);
                if (task != null) {
                    Tasker tasker = new Tasker();
                    tasker.setEmployee(employee);
                    tasker.setTask(task);
                    taskerRepository.save(tasker);
                }
            }
        }

        employeeService.updateEmployee(id,employee);
        ApiResponse response = new ApiResponse("Employee updated successfully with employee id: " + employee.getId(), HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }



}
