package org.itstep.safetywork.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.itstep.safetywork.model.Education;
import org.itstep.safetywork.model.Employee;
import org.itstep.safetywork.repository.EducationRepository;
import org.itstep.safetywork.repository.EmployeeRepository;
import org.itstep.safetywork.repository.NpaopRepository;
import org.itstep.safetywork.service.EducationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/addeducation/edit/{idEmployee}")
@RequiredArgsConstructor
public class AddEducationController {
    private final EmployeeRepository employeeRepository;
    @Getter
    private final EducationRepository educationRepository;
    private final NpaopRepository npaopRepository;
    private final EducationService educationService;

    @GetMapping
    public String showEditEducation(@PathVariable Integer idEmployee, Model model) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(idEmployee);
        if (optionalEmployee.isPresent()) {
            Employee employee = optionalEmployee.get();
            model.addAttribute("employee", employee);
        }
        model.addAttribute("educationList", educationRepository.findAllByEmployeeId(idEmployee));
        model.addAttribute("npaopList", npaopRepository.findAll());
        return "addEducation";
    }

    @PostMapping
    public String addEducation(@PathVariable @ModelAttribute Integer idEmployee, Education edu, String npaopId, RedirectAttributes model) {
        educationService.addEducationService(idEmployee, edu, npaopId, model);
        return "redirect:/addeducation/edit/" + idEmployee;
    }


}
