package org.itstep.safetywork.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "department")
@EqualsAndHashCode(exclude = {"employeeList", "equipmentList", "toolList"})
@ToString(exclude = {"employeeList", "equipmentList", "toolList"})
@NoArgsConstructor
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true)
    private String name;
    @OneToMany(mappedBy = "department")
    private List<Employee> employeeList = new ArrayList<>();
    @OneToMany(mappedBy = "department")
    private List<Tool> toolList = new ArrayList<>();
    @OneToMany(mappedBy = "department")
    private List<Equipment> equipmentList = new ArrayList<>();

    public Department(String name) {
        this.name = name;
    }
}
