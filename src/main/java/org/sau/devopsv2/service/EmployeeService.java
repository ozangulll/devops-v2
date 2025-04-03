package org.sau.devopsv2.service;

import org.sau.devopsv2.entity.Employee;

import java.util.List;
import java.util.Set;

public interface EmployeeService {
    List<Employee> getAllEmployees();
    Employee getEmployeeById(Long id);
    Employee createEmployee(Employee employee);
    Employee updateEmployee(Long id, Employee employee);
    void deleteEmployee(Long id);
    List<Employee> getEmployeesByDepartment(String department);
    Set<Employee> getEmployeesByIds(Set<Long> employeeIds);
}
