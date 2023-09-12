package org.itstep.safetywork.controller;

import lombok.RequiredArgsConstructor;
import org.itstep.safetywork.command.EmployeeCommand;
import org.itstep.safetywork.repository.*;
import org.itstep.safetywork.service.EmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/addemployee")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeRepository employeeRepository;
    private final ProfessionRepository professionRepository;
    private final DepartmentRepository departmentRepository;
    private final NpaopRepository npaopRepository;
    private final EmployeeService employeeService;

    @GetMapping
    public String showEmployee(Model model) {
        model.addAttribute("nameEmployee", "Валерій");
        model.addAttribute("employeeList", employeeRepository.findAll());
        model.addAttribute("professions", professionRepository.findAll());
        model.addAttribute("departments", departmentRepository.findAll());
        model.addAttribute("npaopList", npaopRepository.findAll());
        return "addEmployee";
    }

    @PostMapping
    public String create(@ModelAttribute EmployeeCommand command, RedirectAttributes model) {
        addEmployee(command, model);
        return "redirect:/addemployee";
    }

    private void addEmployee(EmployeeCommand command, RedirectAttributes model) {
        employeeService.addEmployeeService(command, model);
    }
}