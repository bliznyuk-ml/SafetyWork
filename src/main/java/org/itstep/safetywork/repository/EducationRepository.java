package org.itstep.safetywork.repository;

import org.itstep.safetywork.model.Education;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EducationRepository extends JpaRepository<Education, Integer> {
    List<Education> findAllByEmployeeId(Integer id);
}
