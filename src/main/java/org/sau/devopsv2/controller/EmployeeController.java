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
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }
    //working
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateEmployee(@PathVariable Long id, @RequestBody EmployeeDTO employeeDTO) {
        Optional<Employee> employeeOpt = Optional.ofNullable(employeeService.getEmployeeById(id));

        if (employeeOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse("Employee not found with id: " + id, HttpStatus.NOT_FOUND.value()));
        }

        Employee employee = employeeOpt.get();
        employee.setName(employeeDTO.getName());
        employee.setDepartment(employeeDTO.getDepartment());
        employee = employeeService.updateEmployee(id,employee);

        // Update tasks if taskIds are provided
        if (employeeDTO.getTaskIds() != null) {
            // Remove old tasker relations
            Set<Tasker> existingTaskers = employee.getTaskers();
            taskerRepository.deleteAll(existingTaskers);

            // Add new tasker relations based on taskIds
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

        ApiResponse response = new ApiResponse("Employee updated successfully with employee id:" + employee.getId(), HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }



}
