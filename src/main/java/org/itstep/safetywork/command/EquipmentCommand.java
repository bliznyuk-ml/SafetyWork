package org.itstep.safetywork.command;

import java.time.LocalDate;

public record EquipmentCommand(
        String equipmentName,
        String model,
        String serialNumber,
        LocalDate nextTestDate,
        Integer departmentId,
        Integer employeeId,
        String employeeName
) {
}
