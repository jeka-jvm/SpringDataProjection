package org.example.springdataprojection.repository;

import org.example.springdataprojection.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
