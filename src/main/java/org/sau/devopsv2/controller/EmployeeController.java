package org.sau.devopsv2.controller;

import org.sau.devopsv2.dto.EmployeeDTO;
import org.sau.devopsv2.entity.Employee;
import org.sau.devopsv2.mapper.EntityMapper;
import org.sau.devopsv2.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EntityMapper mapper;

    @GetMapping
    public List<EmployeeDTO> getAllEmployees() {
        return mapper.convertToEmployeeDTOList(employeeService.getAllEmployees());
    }

    @GetMapping("/{id}")
    public EmployeeDTO getEmployeeById(@PathVariable Long id) {
        return mapper.convertToEmployeeDTO(employeeService.getEmployeeById(id));
    }

    @PostMapping("/create")
    public EmployeeDTO createEmployee(@RequestBody EmployeeDTO employeeDTO) {
        employeeDTO.setId(null);
        Employee employee = mapper.convertToEmployeeEntity(employeeDTO);
        return mapper.convertToEmployeeDTO(employeeService.createEmployee(employee));
    }

    @PutMapping("/update/{id}")
    public EmployeeDTO updateEmployee(@PathVariable Long id, @RequestBody EmployeeDTO employeeDTO) {
        Employee employee = mapper.convertToEmployeeEntity(employeeDTO);
        return mapper.convertToEmployeeDTO(employeeService.updateEmployee(id, employee));
    }

    @DeleteMapping("/delete/{id}")
    public void deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
    }
}
