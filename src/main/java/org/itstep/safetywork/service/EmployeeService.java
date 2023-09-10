package org.itstep.safetywork.service;

import lombok.RequiredArgsConstructor;
import org.itstep.safetywork.model.Employee;
import org.itstep.safetywork.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ProfessionRepository professionRepository;
    private final GradeRepository gradeRepository;
    private final DepartmentRepository departmentRepository;
    private final MedicineRepository medicineRepository;

    public Employee checkEmployee(String employeeName, RedirectAttributes model) {
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
            model.addFlashAttribute("wrongName", "Невірно введено ПІБ:" + employeeName);
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
}
