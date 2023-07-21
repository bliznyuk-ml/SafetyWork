package org.itstep.safetywork.controller;

import lombok.RequiredArgsConstructor;
import org.itstep.safetywork.command.EmployeeCommand;
import org.itstep.safetywork.model.Employee;
import org.itstep.safetywork.model.Profession;
import org.itstep.safetywork.repository.EmployeeRepository;
import org.itstep.safetywork.repository.GradeRepository;
import org.itstep.safetywork.repository.ProfessionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.Optional;

@Controller
@RequestMapping("/addemployee")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeRepository employeeRepository;
    private final ProfessionRepository professionRepository;
    private final GradeRepository gradeRepository;

    @GetMapping
    public String showEmployee(Model model){
        model.addAttribute("employeeList", employeeRepository.findAll());
        model.addAttribute("professions", professionRepository.findAll());
        return "addemployee";
    }

    @PostMapping
    public String create(EmployeeCommand command, Model model){
        Optional<Profession> professionOptional = professionRepository.findById(command.professionId());
        professionOptional.ifPresent(profession -> {
            Employee employee = Employee.fromCommand(command);
            employee.setProfession(profession);
            if(profession.getId() == 1){
                employee.setGrade(gradeRepository.getReferenceById(1));
            } else {
                employee.setGrade(gradeRepository.getReferenceById(2));
            }
            int age = Employee.calculateAge(command.birthdate(), LocalDate.now());
            if(age >= 18){
                employee.setAge(age);
            } else {
                model.addAttribute("wrongAge", "Працівнику не може бути менше 18 років");

            }

            employeeRepository.save(employee);
        });
        return "redirect:/addemployee";
    }

}
