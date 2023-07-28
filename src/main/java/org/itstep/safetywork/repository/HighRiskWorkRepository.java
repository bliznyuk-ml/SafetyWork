package org.itstep.safetywork.repository;

import org.itstep.safetywork.model.HighRiskWork;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HighRiskWorkRepository extends JpaRepository<HighRiskWork, Integer> {
}
