package org.example.springdataprojection.repository;

import org.example.springdataprojection.entity.Employee;
import org.example.springdataprojection.projection.EmployeeProjection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<EmployeeProjection> findAllBy();
}
