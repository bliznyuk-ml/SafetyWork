package org.itstep.safetywork.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.itstep.safetywork.command.EmployeeCommand;

import java.time.LocalDate;
import java.time.Period;
import java.util.Date;

@Data
@Entity
@Table(name = "employee")
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
    @Column(name = "age")
    private int age;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Profession profession;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Grade grade;

    public Employee(String firstName, String lastName, String surname, LocalDate birthdate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.surname = surname;
        this.birthdate = birthdate;
    }
//
//    public void addProfession(Profession profession){
//        profession.getEmployeeList().add(this);
//        this.profession = profession;
//    }
//
//    public void addGrade(Grade grade){
//        grade.getEmployeeList().add(this);
//        this.grade = grade;
//    }

    public static Employee fromCommand(EmployeeCommand command){
        return new Employee(command.firstName(), command.lastName(), command.surname(), command.birthdate());
    }

    public static int calculateAge(LocalDate birthdate, LocalDate currentDate){
        return Period.between(birthdate, currentDate).getYears();
    }
}