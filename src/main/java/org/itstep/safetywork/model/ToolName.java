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
@NoArgsConstructor
@Table(name = "toolName")
@EqualsAndHashCode(exclude = "toolList")
@ToString(exclude = "toolList")
public class ToolName {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "toolName")
    private List<Tool> toolList = new ArrayList<>();

    public ToolName(String name) {
        this.name = name;
    }
}
