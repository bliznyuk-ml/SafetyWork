package org.itstep.safetywork.command;


import java.time.LocalDate;

public record ToolCommand(
        Integer toolNameId,
        Integer manufacturerId,
        String model,
        String serialNumber,
        LocalDate nextTestDate,
        Integer departmentId
) {
}
