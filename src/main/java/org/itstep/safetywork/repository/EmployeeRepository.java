package org.itstep.safetywork.repository;

import org.itstep.safetywork.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
}
