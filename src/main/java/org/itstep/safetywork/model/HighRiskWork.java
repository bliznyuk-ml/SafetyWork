package org.itstep.safetywork.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.UniqueElements;
import org.itstep.safetywork.command.HighRiskWorkCommand;
import org.itstep.safetywork.command.ToolCommand;

@Data
@Entity
@Table(name = "high_risk_work")
@ToString(exclude = "npaop")
@NoArgsConstructor
public class HighRiskWork {
    @Id
    private Integer id;
    @Column(name = "name", length = 1000)
    private String name;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Npaop npaop;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Work work;
    public HighRiskWork(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public static HighRiskWork fromCommand(HighRiskWorkCommand highRiskWorkCommand){
        return new HighRiskWork(highRiskWorkCommand.id(), highRiskWorkCommand.name());
    }
}
