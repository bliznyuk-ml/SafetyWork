package org.itstep.safetywork.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "job")
@EqualsAndHashCode(exclude = "phones")
@ToString(exclude = "phones")
@NoArgsConstructor
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    private String job;
    @OneToMany(mappedBy = "job")
    private List<Phone> phones = new ArrayList<>();

    public Job(String job) {
        this.job = job;
    }
}