package org.itstep.safetywork.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.Period;

@Data
@Entity
@Table(name = "instruction")
@NoArgsConstructor
public class Instruction {
    @Id
    private Integer id;
    @Column(name = "date_introduction")
    @PastOrPresent
    private LocalDate introduction;
    @Column(name = "re_instruction")
    @PastOrPresent
    private LocalDate reInstruction;
    @Column
    private Period periodToInstruction;
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private Employee employee;

    public Instruction(LocalDate introduction, LocalDate reInstruction) {
        this.introduction = introduction;
        this.reInstruction = reInstruction;
    }
}
