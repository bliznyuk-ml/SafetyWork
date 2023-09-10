package org.itstep.safetywork.command;

import java.time.LocalDate;

public record WorkPermitCommand(
        String name,
        LocalDate dateStart,
        LocalDate dateEnd,
        Integer[] highRiskId,
        Integer leader,
        String[] employeeName,
        Integer[] machineryId,
        Integer[] equipmentId,
        Integer[] toolId,
        String workPermitNumber,
        LocalDate dateWorkPermit

) {
}
