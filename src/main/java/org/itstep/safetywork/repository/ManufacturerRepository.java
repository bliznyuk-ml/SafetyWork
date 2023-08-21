package org.itstep.safetywork.repository;

import org.itstep.safetywork.model.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManufacturerRepository extends JpaRepository<Manufacturer, Integer> {
}
