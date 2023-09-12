package org.itstep.safetywork.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "education")
@NoArgsConstructor
public class Education {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    //    @Column(name = "number_of_certificate")
    private String numberOfCertificate;
    //    @Column(name = "date_of_passage")
    private LocalDate dateOfPassageEducation;
    //    @Column(name = "next_pass_date")
    private LocalDate nextPassDateEducation;
    //    @Column(name = "group")
    private Integer groupOfEducation;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Npaop npaop;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Employee employee;

    public Education(String numberOfCertificate, LocalDate dateOfPassage, LocalDate nextPassDate, Integer groupOfEducation) {
        this.numberOfCertificate = numberOfCertificate;
        this.dateOfPassageEducation = dateOfPassage;
        this.nextPassDateEducation = nextPassDate;
        this.groupOfEducation = groupOfEducation;
    }

    public Education(String numberOfCertificate, LocalDate dateOfPassageEducation, LocalDate nextPassDateEducation) {
        this.numberOfCertificate = numberOfCertificate;
        this.dateOfPassageEducation = dateOfPassageEducation;
        this.nextPassDateEducation = nextPassDateEducation;
    }
}
