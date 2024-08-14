package org.example.springdataprojection.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;


@Getter
@Setter
@Accessors(chain = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String position;
    private Double salary;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

}
