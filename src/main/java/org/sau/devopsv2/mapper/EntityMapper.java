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

    // Employee → EmployeeDTO
    public EmployeeDTO convertToEmployeeDTO(Employee employee) {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setId(employee.getId());
        dto.setName(employee.getName());
        dto.setDepartment(employee.getDepartment());

        Set<Long> taskIds = employee.getTaskers() != null
                ? employee.getTaskers().stream()
                .map(tasker -> tasker.getTask().getId())
                .collect(Collectors.toSet())
                : Set.of();

        dto.setTaskIds(taskIds);
        return dto;
    }

    // Task → TaskDTO
    public TaskDTO convertToTaskDTO(Task task) {
        TaskDTO dto = new TaskDTO();
        dto.setId(task.getId());
        dto.setName(task.getName());
        dto.setDescription(task.getDescription());

        Set<Long> employeeIds = task.getTaskers() != null
                ? task.getTaskers().stream()
                .map(tasker -> tasker.getEmployee().getId())
                .collect(Collectors.toSet())
                : Set.of();

        dto.setEmployeeIds(employeeIds);
        return dto;
    }

    // DTO'dan Entity'ye dönüşüm (Tasker ilişkisi olmadan sadece temel alanlar set edilir)
    public Employee convertToEmployeeEntity(EmployeeDTO dto) {
        Employee employee = new Employee();
        employee.setId(dto.getId());
        employee.setName(dto.getName());
        employee.setDepartment(dto.getDepartment());
        // Tasker ilişkileri dışarıdan set edilmelidir (servis katmanında)
        return employee;
    }

    public Task convertToTaskEntity(TaskDTO dto) {
        Task task = new Task();
        task.setId(dto.getId());
        task.setName(dto.getName());
        task.setDescription(dto.getDescription());
        // Tasker ilişkileri dışarıdan set edilmelidir (servis katmanında)
        return task;
    }

    // List dönüşümleri
    public List<EmployeeDTO> convertToEmployeeDTOList(List<Employee> employees) {
        return employees.stream().map(this::convertToEmployeeDTO).collect(Collectors.toList());
    }

    public List<TaskDTO> convertToTaskDTOList(List<Task> tasks) {
        return tasks.stream().map(this::convertToTaskDTO).collect(Collectors.toList());
    }
}
