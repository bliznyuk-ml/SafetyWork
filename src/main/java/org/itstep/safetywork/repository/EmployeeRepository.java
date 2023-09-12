package org.itstep.safetywork.repository;

import org.itstep.safetywork.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    List<Employee> findAllByGradeId(Integer i);
}
