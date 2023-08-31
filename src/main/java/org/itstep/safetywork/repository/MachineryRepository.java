package org.itstep.safetywork.repository;

import org.itstep.safetywork.model.Machinery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MachineryRepository extends JpaRepository<Machinery, Integer> {
}
