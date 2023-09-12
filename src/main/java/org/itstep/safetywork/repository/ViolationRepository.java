package org.itstep.safetywork.repository;

import org.itstep.safetywork.model.Violation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ViolationRepository extends JpaRepository<Violation, Integer> {
    List<Violation> findAllViolationByWorkIsDone(boolean b);
}
