package org.itstep.safetywork.controller;

import lombok.RequiredArgsConstructor;
import org.itstep.safetywork.repository.EmployeeRepository;
import org.itstep.safetywork.repository.NpaopRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/education")
@RequiredArgsConstructor
public class EducationController {

    private final EmployeeRepository employeeRepository;
    private final NpaopRepository npaopRepository;

    @GetMapping
    public String showEducation(Model model){
        model.addAttribute("employeeList", employeeRepository.findAll());
        model.addAttribute("npaopList", npaopRepository.findAll());
        return "education";
    }

}
