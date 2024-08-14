package org.example.springdataprojection.projection;

import org.springframework.beans.factory.annotation.Value;


public interface EmployeeProjection {

    String getPosition();

    @Value("#{target.department.name}")
    String getDepartmentName();

    @Value("#{target.firstName + ' ' + target.lastName}")
    String getFullName();

}
