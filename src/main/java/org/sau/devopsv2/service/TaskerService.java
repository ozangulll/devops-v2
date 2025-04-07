package org.sau.devopsv2.service;

import org.sau.devopsv2.dto.TaskerDTO;

import java.util.List;

public interface TaskerService {
    TaskerDTO assignTask(TaskerDTO dto);
    List<TaskerDTO> getAllAssignments();
    void deleteAssignment(Long id);
}
