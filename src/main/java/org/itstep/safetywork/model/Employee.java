package org.itstep.safetywork.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.itstep.safetywork.command.EmployeeCommand;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "employee")
@EqualsAndHashCode(exclude = {"instruction", "educationList", "medicine", "equipmentList", "responsibleForMachinery", "violationList"})
@ToString(exclude = {"instruction", "educationList", "medicine", "equipmentList", "responsibleForMachinery", "violationList"})
@NoArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotBlank
    @Column(name = "last_name")
    private String lastName;
    @NotBlank
    @Column(name = "first_name")
    private String firstName;
    @NotBlank
    @Column(name = "surname")
    private String surname;
    //   @NotBlank
    @Column(name = "birthday")
    private LocalDate birthdate;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Profession profession;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Grade grade;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Department department;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Work work;
    @OneToOne(mappedBy = "employee", cascade = CascadeType.PERSIST, optional = false, fetch = FetchType.LAZY)
    private Medicine medicine;
    @OneToOne(mappedBy = "employee", cascade = CascadeType.PERSIST, optional = false, fetch = FetchType.LAZY)
    private Instruction instruction;
    @OneToOne(mappedBy = "employee", cascade = CascadeType.PERSIST, optional = false, fetch = FetchType.LAZY)
    private ResponsibleForMachinery responsibleForMachinery;
    @OneToMany(mappedBy = "employee")
    private List<Education> educationList = new ArrayList<>();
    @OneToMany(mappedBy = "employee")
    private List<Equipment> equipmentList = new ArrayList<>();
    @OneToMany(mappedBy = "employee")
    private List<Violation> violationList = new ArrayList<>();

    public Employee(String firstName, String lastName, String surname, LocalDate birthdate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.surname = surname;
        this.birthdate = birthdate;
    }

    public static Employee fromCommand(EmployeeCommand command) {
        return new Employee(command.firstName(), command.lastName(), command.surname(), command.birthdate());
    }

    public static int calculateAge(LocalDate birthdate, LocalDate currentDate) {
        return Period.between(birthdate, currentDate).getYears();
    }
}
