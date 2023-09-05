package org.itstep.safetywork.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.itstep.safetywork.command.EquipmentCommand;

import java.time.LocalDate;
import java.time.Period;

@Data
@Entity
@Table(name = "eqipment")
@NoArgsConstructor
public class Equipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private EquipmentName equipmentName;
    @NotBlank
    @Column(name = "model")
    private String model;
    @NotBlank
    @Column(name = "serial_number")
    private String serialNumber;
    @Column(name = "next_test_date")
    private LocalDate nextTestDate;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Department department;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Employee employee;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Work work;
    @Column
    private Period periodOfNextTest;

    public Equipment(String model, String serialNumber, LocalDate nextTestDate) {
        this.model = model;
        this.serialNumber = serialNumber;
        this.nextTestDate = nextTestDate;
    }

    public static Equipment equipmentFromCommand(EquipmentCommand equipmentCommand){
        return new Equipment(equipmentCommand.model(), equipmentCommand.serialNumber(), equipmentCommand.nextTestDate());
    }
}
