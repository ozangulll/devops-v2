package org.sau.devopsv2.entity;


import javax.persistence.*;
import java.util.Set;

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

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Tasker> taskers;

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

    public Set<Tasker> getTaskers() {
        return taskers;
    }
    public void setTaskers(Set<Tasker> taskers) {
        this.taskers = taskers;
    }
}

