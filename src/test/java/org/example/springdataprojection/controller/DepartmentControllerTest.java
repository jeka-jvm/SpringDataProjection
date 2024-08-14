package org.example.springdataprojection.controller;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.springdataprojection.entity.Department;
import org.example.springdataprojection.exception.DepartmentNotFoundException;
import org.example.springdataprojection.service.DepartmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.util.Arrays;
import java.util.List;


@WebMvcTest(DepartmentController.class)
public class DepartmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DepartmentService departmentService;

    @Autowired
    private ObjectMapper objectMapper;

    private Department department1;
    private Department department2;

    @BeforeEach
    public void setUp() {
        department1 = new Department(1L, "HR");
        department2 = new Department(2L, "IT");
    }

    @Test
    public void testGetAllDepartments() throws Exception {
        List<Department> departments = Arrays.asList(department1, department2);
        when(departmentService.getAllDepartments()).thenReturn(departments);

        mockMvc.perform(get("/departments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(departments.size()))
                .andExpect(jsonPath("$[0].id").value(department1.getId()))
                .andExpect(jsonPath("$[0].name").value(department1.getName()))
                .andExpect(jsonPath("$[1].id").value(department2.getId()))
                .andExpect(jsonPath("$[1].name").value(department2.getName()));
    }

    @Test
    public void testGetDepartmentById() throws Exception {
        when(departmentService.getDepartmentById(1L)).thenReturn(department1);

        mockMvc.perform(get("/departments/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(department1.getId()))
                .andExpect(jsonPath("$.name").value(department1.getName()));
    }

    @Test
    public void testCreateDepartment() throws Exception {
        when(departmentService.createDepartment(any(Department.class))).thenReturn(department1);

        mockMvc.perform(post("/departments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(department1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(department1.getId()))
                .andExpect(jsonPath("$.name").value(department1.getName()));
    }

    @Test
    public void testUpdateDepartment() throws Exception {
        when(departmentService.updateDepartment(eq(1L), any(Department.class))).thenReturn(department1);

        mockMvc.perform(put("/departments/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(department1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(department1.getId()))
                .andExpect(jsonPath("$.name").value(department1.getName()));
    }

    @Test
    public void testDeleteDepartment() throws Exception {
        when(departmentService.getDepartmentById(1L)).thenReturn(department1);

        mockMvc.perform(delete("/departments/{id}", 1L))
                .andExpect(status().isOk());

        when(departmentService.getDepartmentById(1L))
                .thenThrow(new DepartmentNotFoundException("Отдел не найден"));

        mockMvc.perform(get("/departments/{id}", 1L))
                .andExpect(status().isNotFound());
    }
}