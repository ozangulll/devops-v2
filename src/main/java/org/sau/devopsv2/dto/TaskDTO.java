package org.sau.devopsv2.dto;

import java.util.Set;

public class TaskDTO {
    private Long id;
    private String name;
    private String description;
    private Set<Long> employeeIds;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setEmployeeIds(Set<Long> employeeIds) {
        this.employeeIds = employeeIds;
    }
    public Set<Long> getEmployeeIds() {
        return employeeIds;
    }
}
