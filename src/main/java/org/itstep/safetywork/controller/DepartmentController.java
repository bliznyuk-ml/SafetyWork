package org.itstep.safetywork.controller;

import lombok.RequiredArgsConstructor;
import org.itstep.safetywork.model.Department;
import org.itstep.safetywork.repository.DepartmentRepository;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/department")
@RequiredArgsConstructor
public class DepartmentController {
    private final DepartmentRepository departmentRepository;

    @GetMapping
    public String showDepartment(Model model){
        model.addAttribute("departments", departmentRepository.findAll());
        return "departments";
    }

    @PostMapping
    @Transactional
    public String create(String name){
        Department department = new Department(name);
        departmentRepository.save(department);
        return "redirect:/addemployee";
    }
}
