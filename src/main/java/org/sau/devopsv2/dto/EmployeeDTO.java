package org.sau.devopsv2.dto;

import java.util.Set;

public class EmployeeDTO {
    private Long id;
    private String name;
    private String department;
    private Set<Long> taskIds;
    // Getter ve Setter metotları
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Set<Long> getTaskIds() {
        return taskIds;
    }
    public void setTaskIds(Set<Long> taskIds) {
        this.taskIds = taskIds;
    }
}
