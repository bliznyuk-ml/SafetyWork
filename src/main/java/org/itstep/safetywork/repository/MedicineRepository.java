package org.itstep.safetywork.repository;

import org.itstep.safetywork.model.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicineRepository extends JpaRepository<Medicine, Integer> {
}
