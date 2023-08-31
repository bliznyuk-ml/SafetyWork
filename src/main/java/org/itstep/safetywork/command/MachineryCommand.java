package org.itstep.safetywork.command;

import java.time.LocalDate;

public record MachineryCommand(
        Integer typeOfMachineryId,
        String typeOfMachinery,
        String machineryModel,
        String registration,
        String stateNumber,
        LocalDate ChTO,
        LocalDate PTO,
        Integer departmentId,
        String employeeName,
        String orderNumber,
        LocalDate dateOrder,
        Integer responsibleId,
        String npaopId
) {
}
