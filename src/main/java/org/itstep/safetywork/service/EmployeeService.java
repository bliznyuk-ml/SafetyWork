package org.itstep.safetywork.service;

import lombok.RequiredArgsConstructor;
import org.itstep.safetywork.command.EmployeeCommand;
import org.itstep.safetywork.model.Department;
import org.itstep.safetywork.model.Employee;
import org.itstep.safetywork.model.Medicine;
import org.itstep.safetywork.model.Profession;
import org.itstep.safetywork.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ProfessionRepository professionRepository;
    private final GradeRepository gradeRepository;
    private final DepartmentRepository departmentRepository;
    private final MedicineRepository medicineRepository;

//    public void addEmployee(EmployeeCommand command, RedirectAttributes model) {
//        Optional<Profession> professionOptional = professionRepository.findById(command.professionId());
//        Optional<Department> departmentOptional = departmentRepository.findById(command.departmentId());
//        Employee employee = Employee.fromCommand(command);
//        Medicine medicine = new Medicine(command.dateOfPassage(), command.nextPassDate(), command.contraindications());
//        //  employee.setMedicine(medicine);
//        professionOptional.ifPresent(profession -> {
//            employee.setProfession(profession);
//            if(profession.getId() == 1){
//                employee.setGrade(gradeRepository.getReferenceById(1));
//            } else {
//                employee.setGrade(gradeRepository.getReferenceById(2));
//            }
//        });
//        departmentOptional.ifPresent(employee::setDepartment);
//        int age = Employee.calculateAge(command.birthdate(), LocalDate.now());
//        if(age >= 18){
//            employeeRepository.save(employee);
//        } else {
//            model.addFlashAttribute("wrongAge", "Працівнику не може бути менше 18 років");
//        }
//        employee.setMedicine(medicine);
//        medicine.setEmployee(employee);
//        medicineRepository.save(medicine);
//    }
}
