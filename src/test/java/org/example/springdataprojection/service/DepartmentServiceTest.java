package org.example.springdataprojection.service;

import org.example.springdataprojection.entity.Department;
import org.example.springdataprojection.repository.DepartmentRepository;
import org.example.springdataprojection.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest
public class DepartmentServiceTest {

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @BeforeEach
    public void setup() {
        employeeRepository.deleteAll();
        departmentRepository.deleteAll();
    }

    @Test
    public void testGetAllDepartments() {
        Department department1 = Department.builder()
                .name("Отдел продаж")
                .build();
        Department department2 = Department.builder()
                .name("Отдел IT")
                .build();

        departmentRepository.save(department1);
        departmentRepository.save(department2);

        List<Department> departments = departmentService.getAllDepartments();

        assertNotNull(departments);
        assertEquals(2, departments.size());
    }

    @Test
    public void testGetDepartmentById() {
        Department department = Department.builder()
                .name("Отдел продаж")
                .build();

        Department savedDepartment = departmentRepository.save(department);

        Department foundDepartment = departmentService.getDepartmentById(savedDepartment.getId());

        assertNotNull(foundDepartment);
        assertEquals(savedDepartment.getName(), foundDepartment.getName());
    }

    @Test
    public void testCreateDepartment() {
        Department department = Department.builder()
                .name("Отдел продаж")
                .build();

        Department createdDepartment = departmentService.createDepartment(department);

        assertNotNull(createdDepartment);
        assertEquals(department.getName(), createdDepartment.getName());
    }

    @Test
    public void testUpdateDepartment() {
        Department department = Department.builder()
                .name("Отдел продаж")
                .build();

        Department savedDepartment = departmentRepository.save(department);

        Department updatedDepartment = Department.builder()
                .name("Отдел IT")
                .build();

        Department updatedSavedDepartment = departmentService.updateDepartment(savedDepartment.getId(), updatedDepartment);

        assertNotNull(updatedSavedDepartment);
        assertEquals(updatedDepartment.getName(), updatedSavedDepartment.getName());
    }

    @Test
    public void testDeleteDepartment() {
        Department department = Department.builder()
                .name("Отдел продаж")
                .build();

        Department savedDepartment = departmentRepository.save(department);

        departmentService.deleteDepartment(savedDepartment.getId());

        Optional<Department> optionalDepartment = departmentRepository.findById(savedDepartment.getId());
        assertFalse(optionalDepartment.isPresent());
    }
}