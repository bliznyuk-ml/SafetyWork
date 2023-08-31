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
@Table(name = "machineryModel")
@EqualsAndHashCode(exclude = "machineryList")
@ToString(exclude = "machineryList")
public class MachineryModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "model")
    private String model;

    @OneToMany(mappedBy = "machineryModel")
    private List<Machinery> machineryList = new ArrayList<>();

    public MachineryModel(String model) {
        this.model = model;
    }
}
