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
@Table(name = "equipmentName")
@EqualsAndHashCode(exclude = "equipmentList")
@ToString(exclude = "equipmentList")
public class EquipmentName {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "equipmentName")
    private List <Equipment> equipmentList = new ArrayList<>();

    public EquipmentName(String name) {
        this.name = name;
    }
}
