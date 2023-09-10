package org.itstep.safetywork.repository;

import org.itstep.safetywork.model.Work;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkRepository extends JpaRepository<Work, Integer> {
    Object findAllByIsDone(Boolean b);
}
