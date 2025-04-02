package org.sau.devopsv2.mapper;

import org.sau.devopsv2.dto.EmployeeDTO;
import org.sau.devopsv2.dto.TaskDTO;
import org.sau.devopsv2.entity.Employee;
import org.sau.devopsv2.entity.Task;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EntityMapper {


    public EmployeeDTO convertToEmployeeDTO(Employee employee) {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setId(employee.getId());
        dto.setName(employee.getName());
        dto.setDepartment(employee.getDepartment());
        return dto;
    }

    public Employee convertToEmployeeEntity(EmployeeDTO dto) {
        Employee employee = new Employee();
        employee.setId(dto.getId());
        employee.setName(dto.getName());
        employee.setDepartment(dto.getDepartment());
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
        return dto;
    }

    public Task convertToTaskEntity(TaskDTO dto) {
        Task task = new Task();
        task.setId(dto.getId());
        task.setName(dto.getName());
        task.setDescription(dto.getDescription());
        return task;
    }

    public List<TaskDTO> convertToTaskDTOList(List<Task> tasks) {
        return tasks.stream().map(this::convertToTaskDTO).collect(Collectors.toList());
    }
}

