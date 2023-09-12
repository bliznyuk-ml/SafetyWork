package org.itstep.safetywork.service;

import lombok.RequiredArgsConstructor;
import org.itstep.safetywork.command.EmployeeCommand;
import org.itstep.safetywork.model.*;
import org.itstep.safetywork.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ProfessionRepository professionRepository;
    private final GradeRepository gradeRepository;
    private final DepartmentRepository departmentRepository;
    private final MedicineRepository medicineRepository;
    private final InstructionRepository instructionRepository;

    public Employee checkEmployee(String employeeName, RedirectAttributes model) {
        List<Employee> employeeList = employeeRepository.findAll();
        Employee[] employeeArray = new Employee[1];
        String[] name = employeeName.split("\\s+");
        String lastName;
        String firstName;
        String surname;
        if (name.length == 3) {
            lastName = name[0];
            firstName = name[1];
            surname = name[2];
        } else {
            surname = "";
            firstName = "";
            lastName = "";
            model.addFlashAttribute("wrongName", "Невірно введено ПІБ:" + employeeName);
        }
        employeeList.forEach(e -> {
            if (e.getLastName().equals(lastName) && e.getFirstName().equals(firstName) && e.getSurname().equals(surname)) {
                employeeArray[0] = e;
            }
        });
        Employee employee = null;
        if (employeeArray[0] != null) {
            employee = employeeArray[0];
        }
        return employee;
    }

    public void addEmployeeService(EmployeeCommand command, RedirectAttributes model) {
        Optional<Profession> professionOptional = professionRepository.findById(command.professionId());
        Optional<Department> departmentOptional = departmentRepository.findById(command.departmentId());
        Employee employee = Employee.fromCommand(command);
        professionOptional.ifPresent(profession -> {
            employee.setProfession(profession);
            if (profession.getId() == 1) {
                employee.setGrade(gradeRepository.getReferenceById(1));
            } else {
                employee.setGrade(gradeRepository.getReferenceById(2));
            }
        });
        departmentOptional.ifPresent(employee::setDepartment);
        int age = Employee.calculateAge(command.birthdate(), LocalDate.now());
        Period periodOfPassage = Period.between(LocalDate.now(), command.dateOfPassage());
        Period periodOfNextPass = Period.between(LocalDate.now(), command.nextPassDate());
        Period periodOfIntroduction = Period.between(LocalDate.now(), command.introduction());
        Period periodOfReInstruction = Period.between(LocalDate.now(), command.reInstruction());

        if ((periodOfPassage.isNegative() || periodOfPassage.isZero()) &&
                (!periodOfNextPass.isNegative() || periodOfNextPass.isZero()) &&
                age >= 18 && (periodOfIntroduction.isNegative() || periodOfIntroduction.isZero())
                && (periodOfReInstruction.isNegative() || periodOfReInstruction.isZero())) {
            employeeRepository.save(employee);
            Medicine medicine = new Medicine(command.dateOfPassage(), command.nextPassDate(), command.contraindications());
            Instruction instruction = new Instruction(command.introduction(), command.reInstruction());
            employee.setMedicine(medicine);
            medicine.setEmployee(employee);
            employee.setInstruction(instruction);
            instruction.setEmployee(employee);
            medicineRepository.save(medicine);
            instructionRepository.save(instruction);
        }
        if (age < 18) {
            model.addFlashAttribute("wrongAge", "Працівнику не може бути менше 18 років");
        }
        if (!periodOfPassage.isNegative() && !periodOfPassage.isZero()) {
            model.addFlashAttribute("wrongPeriodOfPassage", "Дата проходження медогляду не має бути пізніше за сьогоднішню");
        }
        if (periodOfNextPass.isNegative() && !periodOfNextPass.isZero()) {
            model.addFlashAttribute("wrongPeriodOfNextPass", "Дата наступного медогляду не має бути раніше за сьогоднішню");
        }
        if (!periodOfIntroduction.isNegative() && !periodOfIntroduction.isZero()) {
            model.addFlashAttribute("wrongPeriodOfIntroduction", "Дата проходження вступного інструктажу не має бути пізніше за сьогоднішню");
        }
        if (!periodOfReInstruction.isNegative() && !periodOfReInstruction.isZero()) {
            model.addFlashAttribute("wrongPeriodOfReInstruction", "Дата проходження первинного / повторного інструктажу не має бути пізніше за сьогоднішню");
        }
    }
}
