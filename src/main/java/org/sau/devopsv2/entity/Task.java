package org.sau.devopsv2.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;
@Data
@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;

    @ManyToMany(mappedBy = "tasks")
    private Set<Employee> employees;

    // Getters and Setters
}

