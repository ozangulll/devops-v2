package org.sau.devopsv2.entity;


import lombok.Data;



import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String department;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
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
    public String getDepartment() {
        return department;
    }
    public void setDepartment(String department) {
        this.department = department;
    }

    public Set<Tasker> getTaskers() {
        return taskers;
    }

    public void setTaskers(Set<Tasker> taskers) {
        this.taskers = taskers;
    }
}

