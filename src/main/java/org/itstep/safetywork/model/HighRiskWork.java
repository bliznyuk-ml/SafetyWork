package org.itstep.safetywork.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.UniqueElements;
import org.itstep.safetywork.command.HighRiskWorkCommand;
import org.itstep.safetywork.command.ToolCommand;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "high_risk_work")
@ToString(exclude = {"npaop", "workList"})
@NoArgsConstructor
public class HighRiskWork {
    @Id
    private Integer id;
    @Column(name = "name", length = 1000)
    private String name;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Npaop npaop;
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "highRiskWork_work", joinColumns = @JoinColumn(name = "highRiskWork_id"),
    inverseJoinColumns = @JoinColumn(name = "work_id"))
    private List<Work> workList = new ArrayList<>();
    public HighRiskWork(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public static HighRiskWork fromCommand(HighRiskWorkCommand highRiskWorkCommand){
        return new HighRiskWork(highRiskWorkCommand.id(), highRiskWorkCommand.name());
    }
}
