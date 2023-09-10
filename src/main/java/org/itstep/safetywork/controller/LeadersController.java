package org.itstep.safetywork.controller;

import lombok.RequiredArgsConstructor;
import org.itstep.safetywork.dao.EmployeeDaoImpl;
import org.itstep.safetywork.model.Department;
import org.itstep.safetywork.model.Employee;
import org.itstep.safetywork.repository.DepartmentRepository;
import org.itstep.safetywork.repository.EmployeeRepository;
import org.itstep.safetywork.repository.GradeRepository;
import org.itstep.safetywork.repository.ProfessionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class LeadersController {
    private final EmployeeRepository employeeRepository;
    private final ProfessionRepository professionRepository;
    private final DepartmentRepository departmentRepository;
    private final EmployeeDaoImpl employeeDao;
    @GetMapping("/leaders")
    public String showLeaders(Model model){
        model.addAttribute("title", "Керівники");
        model.addAttribute("employeeList", employeeDao.findLeaders());
        model.addAttribute("professions", professionRepository.findAll());
        model.addAttribute("departments", departmentRepository.findAll());

        return "leaders";
    }

    @GetMapping("/workers")
    public String showWorkers(Model model){
        model.addAttribute("title", "Робітники");
        model.addAttribute("employeeList", employeeDao.findWorkers());
        model.addAttribute("professions", professionRepository.findAll());
        model.addAttribute("departments", departmentRepository.findAll());
        return "leaders";
    }


}
