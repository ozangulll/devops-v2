package org.sau.devopsv2.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String department;

    @ManyToMany
    @JoinTable(
            name = "taskers",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "task_id")
    )
    private Set<Task> tasks;

    // Getters and Setters
}

