package org.itstep.safetywork.repository;

import org.itstep.safetywork.model.Grade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GradeRepository extends JpaRepository<Grade, Integer> {
}
