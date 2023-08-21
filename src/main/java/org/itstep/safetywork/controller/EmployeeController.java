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
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
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
    private final NpaopRepository npaopRepository;
    private final EducationRepository educationRepository;

    @GetMapping
    public String showEmployee(Model model) {
        model.addAttribute("nameEmployee", "Валерій");
        model.addAttribute("employeeList", employeeRepository.findAll());
        model.addAttribute("professions", professionRepository.findAll());
        model.addAttribute("departments", departmentRepository.findAll());
        model.addAttribute("npaopList", npaopRepository.findAll());
        return "addemployee";
    }

    @PostMapping
    public String create(@ModelAttribute EmployeeCommand command, RedirectAttributes model) {
        addEmployee(command, model);
        return "redirect:/addemployee";
    }

    private void addEmployee(EmployeeCommand command, RedirectAttributes model) {
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

//        Education education = new Education(command.numberOfCertificate(), command.dateOfPassageEducation(),
//                command.nextPassDateEducation(), command.groupOfEducation());
//        Optional<Npaop> npaopOptional = npaopRepository.findById(command.npaopId());
//        npaopOptional.ifPresent(education::setNpaop);
//        List<Education> educationList = new ArrayList<>();
//        educationList.add(education);
//        employee.setEducationList(educationList);


        if ((periodOfPassage.isNegative() || periodOfPassage.isZero()) &&
                (!periodOfNextPass.isNegative() || periodOfNextPass.isZero()) &&
                age >= 18 && (periodOfIntroduction.isNegative() || periodOfIntroduction.isZero())
                && (periodOfReInstruction.isNegative() || periodOfReInstruction.isZero())) {
            //           educationRepository.save(education);
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
