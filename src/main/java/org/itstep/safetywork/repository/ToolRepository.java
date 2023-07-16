package org.itstep.safetywork.repository;

import org.itstep.safetywork.model.Tool;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ToolRepository extends JpaRepository<Tool, Integer> {
}
