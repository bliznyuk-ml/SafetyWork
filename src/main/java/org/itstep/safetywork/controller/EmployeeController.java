package org.itstep.safetywork.controller;

import lombok.RequiredArgsConstructor;
import org.itstep.safetywork.command.EmployeeCommand;
import org.itstep.safetywork.model.*;
import org.itstep.safetywork.repository.*;
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
    private final InstructionRepository instructionRepository;

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
        int periodOfIntroduction = Medicine.calculatePeriod(command.introduction(), LocalDate.now());
        int periodOfReInstruction = Medicine.calculatePeriod(command.reInstruction(), LocalDate.now());

        if(periodOfPassage >= 0 && periodOfNextPass <= 0 && age >= 18 && periodOfIntroduction >= 0 && periodOfReInstruction >= 0){
            employeeRepository.save(employee);
            Medicine medicine = new Medicine(command.dateOfPassage(), command.nextPassDate(), command.contraindications());
            Instruction instruction = new Instruction(command.introduction(), command.reInstruction());
            employee.setMedicine(medicine);
            medicine.setEmployee(employee);
            employee.setInstruction(instruction);
            instruction.setEmployee(employee);
            medicineRepository.save(medicine);
            instructionRepository.save(instruction);
        } if (age < 18){
            model.addFlashAttribute("wrongAge", "Працівнику не може бути менше 18 років");
        } if (periodOfPassage < 0) {
            model.addFlashAttribute("wrongPeriodOfPassage", "Дата проходження медогляду не має бути пізніше за сьогоднішню");
        } if (periodOfNextPass > 0) {
            model.addFlashAttribute("wrongPeriodOfNextPass", "Дата наступного медогляду не має бути раніше за сьогоднішню");
        }  if (periodOfIntroduction < 0) {
            model.addFlashAttribute("wrongPeriodOfIntroduction", "Дата проходження вступного інструктажу не має бути пізніше за сьогоднішню");
        } if (periodOfReInstruction < 0) {
            model.addFlashAttribute("wrongPeriodOfReInstruction", "Дата проходження первинного / повторного інструктажу не має бути пізніше за сьогоднішню");
        }
    }
}
