package org.itstep.safetywork.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.itstep.safetywork.command.ToolCommand;

import java.time.LocalDate;
import java.time.Period;

@Data
@Entity
@Table(name = "tools")
@NoArgsConstructor

public class Tool {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private ToolName toolName;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Manufacturer manufacturer;
    @NotBlank
    @Column(name = "model")
    private String model;
    @NotBlank
    @Column(name = "serial_number")
    private String serialNumber;
    @Column(name = "nextTestDate")
    private LocalDate nextTestDate;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Department department;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Work work;
    @Column
    private Period periodToNextToolChekup;

    public Tool(String model, String serialNumber, LocalDate nextTestDate) {
        this.model = model;
        this.serialNumber = serialNumber;
        this.nextTestDate = nextTestDate;
    }

    public Tool(String model, String serialNumber) {
        this.model = model;
        this.serialNumber = serialNumber;
    }

    public static Tool toolFromCommand(ToolCommand toolCommand) {
        return new Tool(toolCommand.model(), toolCommand.serialNumber(), toolCommand.nextTestDate());
    }
}
