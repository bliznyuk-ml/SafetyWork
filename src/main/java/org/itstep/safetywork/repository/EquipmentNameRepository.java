package org.itstep.safetywork.repository;

import org.itstep.safetywork.model.EquipmentName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EquipmentNameRepository extends JpaRepository<EquipmentName, Integer> {
    Optional<EquipmentName> findByName(String s);
}
