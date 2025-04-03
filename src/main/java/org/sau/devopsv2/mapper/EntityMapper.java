package org.sau.devopsv2.mapper;

import org.sau.devopsv2.dto.EmployeeDTO;
import org.sau.devopsv2.dto.TaskDTO;
import org.sau.devopsv2.entity.Employee;
import org.sau.devopsv2.entity.Task;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class EntityMapper {


    public EmployeeDTO convertToEmployeeDTO(Employee employee) {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setId(employee.getId());
        dto.setName(employee.getName());
        dto.setDepartment(employee.getDepartment());
        dto.setTaskIds(employee.getTasks().stream().map(Task::getId).collect(Collectors.toSet()));
        return dto;
    }

    public Employee convertToEmployeeEntity(EmployeeDTO dto, Set<Task> tasks) {
        Employee employee = new Employee();
        employee.setId(dto.getId());
        employee.setName(dto.getName());
        employee.setDepartment(dto.getDepartment());
        Set<Task> taskSet = dto.getTaskIds().stream()
                .map(taskId -> {
                    Task task = new Task();
                    task.setId(taskId);
                    return task;
                })
                .collect(Collectors.toSet());
        employee.setTasks(taskSet);
        return employee;
    }


    public List<EmployeeDTO> convertToEmployeeDTOList(List<Employee> employees) {
        return employees.stream().map(this::convertToEmployeeDTO).collect(Collectors.toList());
    }


    public TaskDTO convertToTaskDTO(Task task) {
        TaskDTO dto = new TaskDTO();
        dto.setId(task.getId());
        dto.setName(task.getName());
        dto.setDescription(task.getDescription());
        dto.setEmployeeIds(task.getEmployees().stream().map(Employee::getId).collect(Collectors.toSet()));
        return dto;
    }

    public Task convertToTaskEntity(TaskDTO dto, Set<Employee> employees) {
        Task task = new Task();
        task.setId(dto.getId());
        task.setName(dto.getName());
        task.setDescription(dto.getDescription());
        task.setEmployees(employees);
        return task;
    }

    public List<TaskDTO> convertToTaskDTOList(List<Task> tasks) {
        return tasks.stream().map(this::convertToTaskDTO).collect(Collectors.toList());
    }
}

