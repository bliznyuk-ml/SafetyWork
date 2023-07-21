package org.itstep.safetywork.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "grade")
@NoArgsConstructor
@ToString(exclude = "emploeeList")
public class Grade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true)
    private String name;
    @OneToMany(mappedBy = "grade")
    private List<Employee> employeeList = new ArrayList<>();

    public Grade(String name) {
        this.name = name;
    }

//    public static Grade fromCommand(GradeCommand command){
//        return new Grade(command.name());
//    }
}
