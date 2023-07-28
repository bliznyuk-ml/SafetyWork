package org.itstep.safetywork.repository;

import org.itstep.safetywork.model.Instruction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstructionRepository extends JpaRepository<Instruction, Integer> {
}
