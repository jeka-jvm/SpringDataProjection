package org.example.springdataprojection.service;

import org.example.springdataprojection.entity.Department;
import org.example.springdataprojection.exception.DepartmentNotFoundException;
import org.example.springdataprojection.repository.DepartmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    public Department getDepartmentById(Long id) {
        return departmentRepository.findById(id)
                                   .orElseThrow(() -> new DepartmentNotFoundException("Отдел не найден"));
    }

    public Department createDepartment(Department department) {
        return departmentRepository.save(department);
    }

    public Department updateDepartment(Long id, Department departmentDetails) {
        Department department = departmentRepository.findById(id)
                                                    .orElseThrow(() -> new RuntimeException("Отдел не найден"));

        department.setName(departmentDetails.getName());

        return departmentRepository.save(department);
    }

    public void deleteDepartment(Long id) {
        Department department = departmentRepository.findById(id)
                                                    .orElseThrow(() -> new RuntimeException("Отдел не найден"));

        departmentRepository.delete(department);
    }
}
