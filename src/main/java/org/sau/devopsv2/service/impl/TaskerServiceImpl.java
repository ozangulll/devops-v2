package org.sau.devopsv2.service;

import org.sau.devopsv2.dto.TaskerDTO;
import org.sau.devopsv2.entity.Employee;
import org.sau.devopsv2.entity.Task;
import org.sau.devopsv2.entity.Tasker;
import org.sau.devopsv2.repository.EmployeeRepository;
import org.sau.devopsv2.repository.TaskRepository;
import org.sau.devopsv2.repository.TaskerRepository;
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
        throw new RuntimeException("Employee or Task not found");
    }
    @Override
    public List<TaskerDTO> getAllAssignments() {
        return taskerRepository.findAll().stream().map(tasker -> {
            TaskerDTO dto = new TaskerDTO();
            dto.setId(tasker.getId());
            dto.setEmployeeId(tasker.getEmployee().getId());
            dto.setTaskId(tasker.getTask().getId());
            return dto;
        }).collect(Collectors.toList());
    }
    @Override
    public void deleteAssignment(Long id) {
        taskerRepository.deleteById(id);
    }
}
