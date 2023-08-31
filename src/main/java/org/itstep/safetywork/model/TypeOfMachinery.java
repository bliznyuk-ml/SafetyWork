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
@NoArgsConstructor
@Table(name = "typeOfMachinery")
@EqualsAndHashCode(exclude = {"machineryList"})
@ToString(exclude = {"machineryList"})
public class TypeOfMachinery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "type")
    private String type;
    @OneToMany(mappedBy = "typeOfMachinery")
    private List<Machinery> machineryList = new ArrayList<>();
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Npaop npaop;

    public TypeOfMachinery(String type) {
        this.type = type;
    }
}
