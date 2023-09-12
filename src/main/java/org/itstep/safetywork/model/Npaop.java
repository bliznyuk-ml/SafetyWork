package org.itstep.safetywork.model;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "npaop")
@EqualsAndHashCode(exclude = {"highRiskWorkList", "educationList", "typeOfMachineryList"})
@ToString(exclude = {"highRiskWorkList", "educationList", "typeOfMachineryList"})
@NoArgsConstructor
public class Npaop {
    @Id
    private String id;
    @Column(name = "name", length = 1000)
    private String name;
    @Column(name = "link", length = 1000)
    private String link;
    @OneToMany(mappedBy = "npaop")
    private List<HighRiskWork> highRiskWorkList = new ArrayList<>();
    @OneToMany(mappedBy = "npaop")
    private List<Education> educationList = new ArrayList<>();
    @OneToMany(mappedBy = "npaop")
    private List<TypeOfMachinery> typeOfMachineryList = new ArrayList<>();

    public Npaop(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Npaop(String id, String name, String link) {
        this.id = id;
        this.name = name;
        this.link = link;
    }
}
