package org.itstep.safetywork.repository;

import org.itstep.safetywork.model.HighRiskWork;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HighRiskWorkRepository extends JpaRepository<HighRiskWork, Integer> {
    List<HighRiskWork> findAllByNpaopId(String id);
}
