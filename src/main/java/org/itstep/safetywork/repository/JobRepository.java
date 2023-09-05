package org.itstep.safetywork.repository;

import org.itstep.safetywork.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job, Integer> {
}
