package org.itstep.safetywork.controller;

import lombok.RequiredArgsConstructor;
import org.itstep.safetywork.command.EmployeeCommand;
import org.itstep.safetywork.model.Department;
import org.itstep.safetywork.model.Employee;
import org.itstep.safetywork.model.Medicine;
import org.itstep.safetywork.model.Profession;
import org.itstep.safetywork.repository.*;
import org.itstep.safetywork.service.EmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.Optional;

@Controller
@RequestMapping("/addemployee")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeRepository employeeRepository;
    private final ProfessionRepository professionRepository;
    private final GradeRepository gradeRepository;
    private final DepartmentRepository departmentRepository;
    private final MedicineRepository medicineRepository;

    @GetMapping
    public String showEmployee(Model model){
        model.addAttribute("nameEmployee", "Валерій");
        model.addAttribute("employeeList", employeeRepository.findAll());
        model.addAttribute("professions", professionRepository.findAll());
        model.addAttribute("departments", departmentRepository.findAll());
        return "addemployee";
    }

    @PostMapping
    public String create(@ModelAttribute EmployeeCommand command, RedirectAttributes model){
        addEmployee(command, model);
        return "redirect:/addemployee";
    }

    private void addEmployee(EmployeeCommand command, RedirectAttributes model) {
        Optional<Profession> professionOptional = professionRepository.findById(command.professionId());
        Optional<Department> departmentOptional = departmentRepository.findById(command.departmentId());
        Employee employee = Employee.fromCommand(command);
        professionOptional.ifPresent(profession -> {
            employee.setProfession(profession);
            if(profession.getId() == 1){
                employee.setGrade(gradeRepository.getReferenceById(1));
            } else {
                employee.setGrade(gradeRepository.getReferenceById(2));
            }
        });
        departmentOptional.ifPresent(employee::setDepartment);
        int age = Employee.calculateAge(command.birthdate(), LocalDate.now());
        int periodOfPassage = Medicine.calculatePeriod(command.dateOfPassage(), LocalDate.now());
        int periodOfNextPass = Medicine.calculatePeriod(command.nextPassDate(), LocalDate.now());
        if(periodOfPassage >= 0 && periodOfNextPass <= 0 && age >= 18){
            employeeRepository.save(employee);
            Medicine medicine = new Medicine(command.dateOfPassage(), command.nextPassDate(), command.contraindications());
            employee.setMedicine(medicine);
            medicine.setEmployee(employee);
            medicineRepository.save(medicine);
        } if (age < 18){
            model.addFlashAttribute("wrongAge", "Працівнику не може бути менше 18 років");
        } if (periodOfPassage < 0) {
            model.addFlashAttribute("wrongPeriodOfPassage", "Дата проходження медогляду не має бути пізніше за сьогоднішню");
        } if (periodOfNextPass > 0) {
            model.addFlashAttribute("wrongPeriodOfNextPass", "Дата наступного медогляду не має бути раніше за сьогоднішню");
        }
    }
}
