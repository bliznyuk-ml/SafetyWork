package org.itstep.safetywork.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "violation")
@NoArgsConstructor
public class Violation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "date")
    private LocalDate date;
    @Column(name = "violation")
    private String violation;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Work work;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Employee employee;

    public Violation(String violation) {
        this.violation = violation;
    }
}
