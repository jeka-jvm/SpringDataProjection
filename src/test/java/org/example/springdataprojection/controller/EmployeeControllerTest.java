package org.example.springdataprojection.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.springdataprojection.entity.Department;
import org.example.springdataprojection.entity.Employee;
import org.example.springdataprojection.exception.EmployeeNotFoundException;
import org.example.springdataprojection.projection.EmployeeProjection;
import org.example.springdataprojection.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @Autowired
    private ObjectMapper objectMapper;

    private Employee employee1;
    private Employee employee2;
    private Department department1;

    @BeforeEach
    public void setUp() {
        department1 = Department.builder()
                .id(1L)
                .name("HR")
                .build();

        employee1 = Employee.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .position("Developer")
                .salary(75000.00)
                .department(department1)
                .build();

        employee2 = Employee.builder()
                .id(2L)
                .firstName("Jane")
                .lastName("Smith")
                .position("Manager")
                .salary(85000.00)
                .department(department1)
                .build();
    }

    @Test
    public void testGetAllEmployees() throws Exception {
        EmployeeProjection employeeProjection1 = new EmployeeProjection() {
            @Override
            public String getPosition() { return employee1.getPosition(); }
            @Override
            public String getDepartmentName() { return employee1.getDepartment().getName(); }
            @Override
            public String getFullName() { return employee1.getFirstName() + " " + employee1.getLastName(); }
        };

        EmployeeProjection employeeProjection2 = new EmployeeProjection() {
            @Override
            public String getPosition() { return employee2.getPosition(); }
            @Override
            public String getDepartmentName() { return employee2.getDepartment().getName(); }
            @Override
            public String getFullName() { return employee2.getFirstName() + " " + employee2.getLastName(); }
        };

        List<EmployeeProjection> projections = Arrays.asList(employeeProjection1, employeeProjection2);
        when(employeeService.getAllEmployees()).thenReturn(projections);

        mockMvc.perform(get("/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(projections.size()))
                .andExpect(jsonPath("$[0].position").value(employee1.getPosition()))
                .andExpect(jsonPath("$[0].departmentName").value(employee1.getDepartment().getName()))
                .andExpect(jsonPath("$[0].fullName").value(employee1.getFirstName() + " " + employee1.getLastName()))
                .andExpect(jsonPath("$[1].position").value(employee2.getPosition()))
                .andExpect(jsonPath("$[1].departmentName").value(employee2.getDepartment().getName()))
                .andExpect(jsonPath("$[1].fullName").value(employee2.getFirstName() + " " + employee2.getLastName()));
    }

    @Test
    public void testGetEmployeeById() throws Exception {
        when(employeeService.getEmployee(1L)).thenReturn(employee1);

        mockMvc.perform(get("/employees/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(employee1.getId()))
                .andExpect(jsonPath("$.firstName").value(employee1.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(employee1.getLastName()))
                .andExpect(jsonPath("$.position").value(employee1.getPosition()))
                .andExpect(jsonPath("$.salary").value(employee1.getSalary()))
                .andExpect(jsonPath("$.department.id").value(department1.getId()))
                .andExpect(jsonPath("$.department.name").value(department1.getName()));
    }

    @Test
    public void testCreateEmployee() throws Exception {
        when(employeeService.createEmployee(any(Employee.class))).thenReturn(employee1);

        mockMvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employee1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(employee1.getId()))
                .andExpect(jsonPath("$.firstName").value(employee1.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(employee1.getLastName()))
                .andExpect(jsonPath("$.position").value(employee1.getPosition()))
                .andExpect(jsonPath("$.salary").value(employee1.getSalary()))
                .andExpect(jsonPath("$.department.id").value(department1.getId()))
                .andExpect(jsonPath("$.department.name").value(department1.getName()));
    }

    @Test
    public void testUpdateEmployee() throws Exception {
        when(employeeService.updateEmployee(eq(1L), any(Employee.class))).thenReturn(employee1);

        mockMvc.perform(put("/employees/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employee1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(employee1.getId()))
                .andExpect(jsonPath("$.firstName").value(employee1.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(employee1.getLastName()))
                .andExpect(jsonPath("$.position").value(employee1.getPosition()))
                .andExpect(jsonPath("$.salary").value(employee1.getSalary()))
                .andExpect(jsonPath("$.department.id").value(department1.getId()))
                .andExpect(jsonPath("$.department.name").value(department1.getName()));
    }

    @Test
    public void testDeleteEmployee() throws Exception {
        when(employeeService.getEmployee(1L)).thenReturn(employee1);

        mockMvc.perform(delete("/employees/{id}", 1L))
                .andExpect(status().isOk());

        when(employeeService.getEmployee(1L))
                .thenThrow(new EmployeeNotFoundException("Сотрудник не найден"));

        mockMvc.perform(get("/employees/{id}", 1L))
                .andExpect(status().isNotFound());
    }
}