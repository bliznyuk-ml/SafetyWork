package org.itstep.safetywork.service;

import lombok.RequiredArgsConstructor;
import org.itstep.safetywork.command.EquipmentCommand;
import org.itstep.safetywork.model.Employee;
import org.itstep.safetywork.model.Equipment;
import org.itstep.safetywork.repository.EmployeeRepository;
import org.itstep.safetywork.repository.EquipmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EquipmentService {
    private final EmployeeRepository employeeRepository;
    private final EquipmentRepository equipmentRepository;

    public Employee getEmployee(String employeeName, RedirectAttributes model) {
        List<Employee> employeeList = employeeRepository.findAll();
        Employee[] employeeArray = new Employee[1];
        String[] name = employeeName.split("\\s+");
        String lastName;
        String firstName;
        String surname;
        if(name.length == 3) {
            lastName = name[0];
            firstName = name[1];
            surname = name[2];
        } else {
            surname = "";
            firstName = "";
            lastName = "";
            model.addFlashAttribute("wrongName", "Невірно введено ПІБ");
        }
        employeeList.forEach(e -> {
            if(e.getLastName().equals(lastName) && e.getFirstName().equals(firstName) && e.getSurname().equals(surname)){
                employeeArray[0] = e;
            }
        });
        Employee employee = null;
        if(employeeArray[0] != null){
            employee = employeeArray[0];
        }
        return employee;
    }

    public void extracted(EquipmentCommand command, RedirectAttributes model, Equipment equipment) {
        Period periodOfNextTest = Period.between(LocalDate.now(), command.nextTestDate());
        if(periodOfNextTest.isNegative() || periodOfNextTest.isZero()){
            model.addFlashAttribute("wrongPeriodOfNextTest", "Дата наступної перевірки не може бути раніше за сьогоднішню або термін перевірки виплив");
        }
        else if(periodOfNextTest.getMonths() >= 6 || periodOfNextTest.getYears() >=1){
            model.addFlashAttribute("wrongPeriodOfNextTest", "Термін перевірки інструменту не може перевищувати 6 місяців");
        } else if (equipment.getDepartment().getId().equals(equipment.getEmployee().getDepartment().getId())) {
            equipmentRepository.save(equipment);
        } else {
            model.addFlashAttribute("responsibleEmployee", "Відповідальна особа за обладнання має працювати у підрозділі, де зареєстровано обладнання");
        }
    }
}
