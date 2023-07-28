package org.itstep.safetywork.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.Period;

@Data
@Entity
@Table(name = "medicine")
@NoArgsConstructor
public class Medicine {
    @Id
    private Integer id;
    @Column(name = "date_of_passage")
    @PastOrPresent
    private LocalDate dateOfPassage;
    @Column(name = "next_pass_date")
    @FutureOrPresent
    private LocalDate nextPassDate;
    @Column(name = "contraindications")
    private String contraindications;
    @Column
    private Period periodToMedicalChekup;
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    //  @JoinColumn(name = "id")
    private Employee employee;

    public Medicine(LocalDate dateOfPassage, LocalDate nextPassDate, String contraindications) {
        this.dateOfPassage = dateOfPassage;
        this.nextPassDate = nextPassDate;
        this.contraindications = contraindications;
    }

    public static int calculatePeriod(LocalDate date, LocalDate currentDate){
        return Period.between(date, currentDate).getDays();
    }
}
