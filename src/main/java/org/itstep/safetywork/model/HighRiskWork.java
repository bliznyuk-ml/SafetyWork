package org.itstep.safetywork.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "high_risk_work")
public class HighRiskWork {
    @Id
    private Integer id;
    @Column(name = "name")
    private String name;

}
