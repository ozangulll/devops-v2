package org.sau.devopsv2.dto;


import lombok.Data;

@Data
public class TaskerDTO {
    private Long id;
    private Long employeeId;
    private Long taskId;

    public Long getId() {
        return id;
    }
    public Long getEmployeeId() {
        return employeeId;
    }
    public Long getTaskId() {
        return taskId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }
}
