package org.itstep.safetywork.repository;

import org.itstep.safetywork.model.Phone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhoneRepository extends JpaRepository<Phone, Integer> {
}
