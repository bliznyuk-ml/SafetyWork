package org.itstep.safetywork.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class Phone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "phone")
    private String phoneNumber;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Job job;

    public Phone(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
