package org.itstep.safetywork.repository;

import org.itstep.safetywork.model.Profession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfessionRepository extends JpaRepository<Profession, Integer> {
}
