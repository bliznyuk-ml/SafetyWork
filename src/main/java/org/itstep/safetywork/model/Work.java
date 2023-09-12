package org.itstep.safetywork.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "work")
@EqualsAndHashCode(exclude = {"workPermit", "employeeList", "highRiskWorkList",
        "machineryList", "equipmentList", "toolList", "violationList"})
@ToString(exclude = {"workPermit", "employeeList", "highRiskWorkList",
        "machineryList", "equipmentList", "toolList", "violationList"})
@NoArgsConstructor
public class Work {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "date_start")
    private LocalDate dateStart;
    @Column(name = "date_end")
    private LocalDate dateEnd;
    @Column(name = "is_done")
    private boolean isDone;
    @OneToOne(mappedBy = "work", cascade = CascadeType.PERSIST, optional = false, fetch = FetchType.LAZY)
    private WorkPermit workPermit;
    @OneToMany(mappedBy = "work")
    private List<Employee> employeeList = new ArrayList<>();
    @ManyToMany(mappedBy = "workList")
    private List<HighRiskWork> highRiskWorkList = new ArrayList<>();
    @OneToMany(mappedBy = "work")
    private List<Machinery> machineryList = new ArrayList<>();
    @OneToMany(mappedBy = "work")
    private List<Equipment> equipmentList = new ArrayList<>();
    @OneToMany(mappedBy = "work")
    private List<Tool> toolList = new ArrayList<>();
    @OneToMany(mappedBy = "work")
    private List<Violation> violationList = new ArrayList<>();

    public Work(String name, LocalDate dateStart, LocalDate dateEnd) {
        this.name = name;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
    }
}
