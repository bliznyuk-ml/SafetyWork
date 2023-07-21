package org.itstep.safetywork.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
//import org.itstep.safetywork.command.ProfessionCommand;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "profession")
@NoArgsConstructor
@ToString(exclude = "employeeList")
public class Profession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true)
    private String name;
    @OneToMany(mappedBy = "profession")
    private List<Employee> employeeList = new ArrayList<>();

    public Profession(String name) {
        this.name = name;
    }

//    public static Profession fromCommand(ProfessionCommand command){
//        return new Profession(command.name());
//    }
}
