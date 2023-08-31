package org.itstep.safetywork.repository;

import org.itstep.safetywork.model.MachineryModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MachineryModelRepository extends JpaRepository<MachineryModel, Integer> {
    Optional<MachineryModel> findByModel(String s);

}
