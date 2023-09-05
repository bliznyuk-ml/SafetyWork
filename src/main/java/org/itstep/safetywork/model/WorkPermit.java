package org.itstep.safetywork.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "work_permit")
@NoArgsConstructor
public class WorkPermit {
    @Id
    private Integer id;
    @Column(name = "number")
    private String number;
    @Column(name = "date_wp")
    private LocalDate date;
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private Work work;

    public WorkPermit(String number, LocalDate date) {
        this.number = number;
        this.date = date;
    }
}
