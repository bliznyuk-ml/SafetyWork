package org.itstep.safetywork.command;

import org.itstep.safetywork.model.Profession;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public record EmployeeCommand(String lastName, String firstName, String surname, LocalDate birthdate, Integer professionId) {
}
