package org.sau.devopsv2.service.impl;

import org.sau.devopsv2.dto.TaskerDTO;
import org.sau.devopsv2.entity.Employee;
import org.sau.devopsv2.entity.Task;
import org.sau.devopsv2.entity.Tasker;
import org.sau.devopsv2.exception.CustomException;
import org.sau.devopsv2.repository.EmployeeRepository;
import org.sau.devopsv2.repository.TaskRepository;
import org.sau.devopsv2.repository.TaskerRepository;
import org.sau.devopsv2.service.TaskerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskerServiceImpl implements TaskerService {

    @Autowired
    private TaskerRepository taskerRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public TaskerDTO assignTask(TaskerDTO dto) {
        Optional<Employee> employeeOpt = employeeRepository.findById(dto.getEmployeeId());
        Optional<Task> taskOpt = taskRepository.findById(dto.getTaskId());

        if (employeeOpt.isPresent() && taskOpt.isPresent()) {
            Tasker tasker = new Tasker();
            tasker.setEmployee(employeeOpt.get());
            tasker.setTask(taskOpt.get());

            Tasker saved = taskerRepository.save(tasker);
            dto.setId(saved.getId());
            return dto;
        }
        throw new CustomException("Employee or Task not found for assignment");
    }

    @Override
    public List<TaskerDTO> getAllAssignments() {
        List<Tasker> taskers = taskerRepository.findAll();
        if (taskers.isEmpty()) {
            throw new CustomException("No assignments found");
        }
        return taskers.stream().map(tasker -> {
            TaskerDTO dto = new TaskerDTO();
            dto.setId(tasker.getId());
            dto.setEmployeeId(tasker.getEmployee().getId());
            dto.setTaskId(tasker.getTask().getId());
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public void deleteAssignment(Long id) {
        Optional<Tasker> taskerOpt = taskerRepository.findById(id);
        if (taskerOpt.isEmpty()) {
            throw new CustomException("Assignment with ID " + id + " not found");
        }
        taskerRepository.deleteById(id);
    }

    @Override
    public TaskerDTO updateAssignment(Long id, TaskerDTO dto) {
        Optional<Tasker> taskerOpt = taskerRepository.findById(id);
        Optional<Employee> employeeOpt = employeeRepository.findById(dto.getEmployeeId());
        Optional<Task> taskOpt = taskRepository.findById(dto.getTaskId());

        if (taskerOpt.isPresent() && employeeOpt.isPresent() && taskOpt.isPresent()) {
            Tasker tasker = taskerOpt.get();
            tasker.setEmployee(employeeOpt.get());
            tasker.setTask(taskOpt.get());

            Tasker updated = taskerRepository.save(tasker);
            dto.setId(updated.getId());
            return dto;
        }
        throw new CustomException("Assignment, Employee, or Task not found for update");
    }
}
