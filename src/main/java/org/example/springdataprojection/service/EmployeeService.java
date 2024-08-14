package org.example.springdataprojection.service;

import org.example.springdataprojection.entity.Employee;
import org.example.springdataprojection.exception.EmployeeNotFoundException;
import org.example.springdataprojection.projection.EmployeeProjection;
import org.example.springdataprojection.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<EmployeeProjection> getAllEmployees() {
        return employeeRepository.findAllBy();
    }

    public Employee getEmployee(Long employeeId) {
        Optional<Employee> optEmployee = employeeRepository.findById(employeeId);
        if (optEmployee.isEmpty()) {
            throw new EmployeeNotFoundException("Сотрудник не найден");
        }

        return optEmployee.get();
    }

    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee updateEmployee(Long id, Employee employeeDetails) {
        Employee employee = employeeRepository.findById(id)
                                              .orElseThrow(() -> new EmployeeNotFoundException("Сотрудник не найден"));

        employee.setFirstName(employeeDetails.getFirstName());
        employee.setLastName(employeeDetails.getLastName());
        employee.setPosition(employeeDetails.getPosition());
        employee.setSalary(employeeDetails.getSalary());
        employee.setDepartment(employeeDetails.getDepartment());

        return employeeRepository.save(employee);
    }

    public void deleteEmployee(Long id) {
        Employee employee = employeeRepository.findById(id)
                                              .orElseThrow(() -> new EmployeeNotFoundException("Сотрудник не найден"));

        employeeRepository.delete(employee);
    }
}
