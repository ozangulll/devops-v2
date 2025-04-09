package org.sau.devopsv2.service.impl;

import org.sau.devopsv2.entity.Employee;
import org.sau.devopsv2.entity.Tasker;
import org.sau.devopsv2.exception.CustomException;
import org.sau.devopsv2.repository.EmployeeRepository;
import org.sau.devopsv2.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id).orElseThrow(() -> new CustomException("Employee not found with id: " + id));
    }

    @Override
    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public Employee updateEmployee(Long id, Employee employee) {
        Employee existingEmployee = getEmployeeById(id);

        if (existingEmployee != null) {
            // Mevcut taskers ile yeni taskers'ı güncelle
            Set<Tasker> updatedTaskers = employee.getTaskers();
            existingEmployee.setTaskers(updatedTaskers);
            existingEmployee.setName(employee.getName());
            existingEmployee.setDepartment(employee.getDepartment());
            return employeeRepository.save(existingEmployee);
        }
        // Eğer mevcut employee bulunamadıysa, exception fırlatıyoruz.
        throw new CustomException("Employee with id " + id + " could not be updated. Employee not found.");
    }

    @Override
    public void deleteEmployee(Long id) {
        Employee employee = getEmployeeById(id);
        if (employee != null) {
            employeeRepository.delete(employee);
        } else {
            throw new CustomException("Employee with id " + id + " not found. Deletion failed.");
        }
    }

    @Override
    public List<Employee> getEmployeesByDepartment(String department) {
        return employeeRepository.findByDepartment(department);
    }

    @Override
    public Set<Employee> getEmployeesByIds(Set<Long> employeeIds) {
        Set<Employee> employees = new HashSet<>(employeeRepository.findAllById(employeeIds));
        if (employees.isEmpty()) {
            throw new CustomException("No employees found with the provided IDs.");
        }
        return employees;
    }
}
