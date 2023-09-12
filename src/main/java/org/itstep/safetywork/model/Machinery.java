package org.itstep.safetywork.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.itstep.safetywork.command.MachineryCommand;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "machinery")
@ToString(exclude = "responsibleForMachinery")
@NoArgsConstructor
public class Machinery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private TypeOfMachinery typeOfMachinery;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private MachineryModel machineryModel;
    @Column(name = "registration")
    private String registration;
    @Column(name = "state_number")
    private String stateNumber;
    @Column(name = "ChTO")
    private LocalDate ChTO;
    @Column(name = "PTO")
    private LocalDate PTO;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private ResponsibleForMachinery responsibleForMachinery;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Department department;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Work work;

    public Machinery(String registration, String stateNumber, LocalDate chTO, LocalDate PTO) {
        this.registration = registration;
        this.stateNumber = stateNumber;
        ChTO = chTO;
        this.PTO = PTO;
    }

    public Machinery(String registration, String stateNumber) {
        this.registration = registration;
        this.stateNumber = stateNumber;
    }

    public static Machinery fromCommand(MachineryCommand machineryCommand) {
        return new Machinery(
                machineryCommand.registration(),
                machineryCommand.stateNumber(),
                machineryCommand.ChTO(),
                machineryCommand.PTO()
        );
    }
}
