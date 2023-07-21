package org.itstep.safetywork.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.itstep.safetywork.command.ToolCommand;

@Data
@Entity
@Table(name = "tools")
@NoArgsConstructor

public class Tool {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotBlank
    @Column(name = "name")
    private String name;
    @NotBlank
    @Column(name = "serial_number")
    private String serialNumber;

    public Tool(String name, String serialNumber) {
        this.name = name;
        this.serialNumber = serialNumber;
    }

    public static Tool fromCommand(ToolCommand toolCommand){
        return new Tool(toolCommand.name(), toolCommand.serialNumber());
    }
}
