package org.itstep.safetywork.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.itstep.safetywork.model.Education;
import org.itstep.safetywork.model.Employee;
import org.itstep.safetywork.model.Npaop;
import org.itstep.safetywork.repository.EducationRepository;
import org.itstep.safetywork.repository.EmployeeRepository;
import org.itstep.safetywork.repository.NpaopRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.Period;
import java.util.Collections;
import java.util.Optional;

@Controller
@RequestMapping("/addeducation/edit/{idEmployee}")
@RequiredArgsConstructor
public class AddEducationController {
    private final EmployeeRepository employeeRepository;
    @Getter
    private final EducationRepository educationRepository;
    private final NpaopRepository npaopRepository;

    @GetMapping
    public String showEditEducation(@PathVariable Integer idEmployee, Model model){
        Optional<Employee> optionalEmployee = employeeRepository.findById(idEmployee);
        Employee employee = new Employee();
        optionalEmployee.ifPresent(e -> {
            employee.setFirstName(e.getFirstName());
            employee.setLastName(e.getLastName());
            employee.setSurname(e.getSurname());
            employee.setEducationList(e.getEducationList());
            employee.setDepartment(e.getDepartment());
            employee.setProfession(e.getProfession());
        });
        model.addAttribute("employee", employee);
        model.addAttribute("educationList", educationRepository.findAllByEmployeeId(idEmployee));
        model.addAttribute("npaopList", npaopRepository.findAll());
        return "addeducation";
    }

    @PostMapping
    public String addEducation(@PathVariable @ModelAttribute Integer idEmployee, Education edu, String npaopId, RedirectAttributes model){
        Period periodOfPassage = Period.between(LocalDate.now(), edu.getDateOfPassageEducation());
        Period periodOfNextPassage = Period.between(LocalDate.now(), edu.getNextPassDateEducation());
        if((periodOfPassage.isNegative() ||
                periodOfPassage.isZero()) &&
                (!periodOfNextPassage.isNegative() &&
                        !periodOfNextPassage.isZero())){
            Education education = new Education(edu.getNumberOfCertificate(), edu.getDateOfPassageEducation(), edu.getNextPassDateEducation(), edu.getGroupOfEducation());
            Optional<Npaop> npaopOptional = npaopRepository.findById(npaopId);
            npaopOptional.ifPresent(education::setNpaop);
            Optional<Employee> optionalEmployee = employeeRepository.findById(idEmployee);
            if (optionalEmployee.isPresent()) {
                Employee updatedEmployee = optionalEmployee.get();
                education.setEmployee(updatedEmployee);
                educationRepository.save(education);
            }
        }
        if(!periodOfPassage.isNegative() && !periodOfPassage.isZero()){
            model.addFlashAttribute("wrongPeriodOfPassageOfEducation", "Дата проходження навчання не може бути пізніше сьогоднішньої");
        }
        if(periodOfNextPassage.isNegative() || periodOfNextPassage.isZero()){
            model.addFlashAttribute("wrongPeriodOfNextPassOfEducation", "Дата наступного проходження навчання не може бути рашіше за сьогоднішню, або термін наступного навчання сплинув");
        }
        return "redirect:/addeducation/edit/"+idEmployee;
    }
}
