package com.example.accessingdatapostgresql.repositories;

import com.example.accessingdatapostgresql.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository  extends JpaRepository<Employee, Long> {
    List<Employee> findAllByTitle(String title);
}
