package org.itstep.safetywork.repository;

import org.itstep.safetywork.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {
}
