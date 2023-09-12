package org.itstep.safetywork.controller;
import lombok.RequiredArgsConstructor;
import org.itstep.safetywork.command.EmployeeCommand;
import org.itstep.safetywork.model.*;
import org.itstep.safetywork.repository.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class DetailEmployeeController {
    private final EmployeeRepository employeeRepository;
    private final EducationRepository educationRepository;
    private final ProfessionRepository professionRepository;
    private final DepartmentRepository departmentRepository;
    private final GradeRepository gradeRepository;

    @GetMapping("employee/showdetails/{id}")
    public String showDetailsEmployee(@PathVariable Integer id, Model model) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if (optionalEmployee.isPresent()) {
            Employee employee = optionalEmployee.get();
            model.addAttribute("employee", employee);
        }
        List<Education> educationList = educationRepository.findAllByEmployeeId(id);
        model.addAttribute("educationList", educationList);
        List<HighRiskWork> highRiskWorkList = new ArrayList<>();
        for (Education education : educationList) {

            List<HighRiskWork> riskWorks = education.getNpaop().getHighRiskWorkList();
            highRiskWorkList.addAll(riskWorks);
        }
        if (!highRiskWorkList.isEmpty()) {
            model.addAttribute("highRiskWorkList", highRiskWorkList);
        }
        return "employeeDetails";
    }

    @GetMapping("employee/edit/{id}")
    public String showEditEmployee(@PathVariable Integer id, Model model) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if (optionalEmployee.isPresent()) {
            Employee employee = optionalEmployee.get();
            model.addAttribute("employee", employee);
            model.addAttribute("departmentName", employee.getDepartment().getName());
            model.addAttribute("professionName", employee.getProfession().getName());
        }
        model.addAttribute("departments", departmentRepository.findAll());

        model.addAttribute("professions", professionRepository.findAll());
        return "editEmployee";
    }

    @PostMapping("employee/edit/{id}")
    public String editEmployee(@PathVariable Integer id, EmployeeCommand command) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if (optionalEmployee.isPresent()) {
            Employee employee = optionalEmployee.get();
            Optional<Profession> optionalProfession = professionRepository.findById(command.professionId());
            optionalProfession.ifPresent(profession -> {
                employee.setProfession(profession);
                if (profession.getId() == 1) {
                    employee.setGrade(gradeRepository.getReferenceById(1));
                } else {
                    employee.setGrade(gradeRepository.getReferenceById(2));
                }
            });
            Optional<Department> optionalDepartment = departmentRepository.findById(command.departmentId());
            optionalDepartment.ifPresent(employee::setDepartment);
            employeeRepository.save(employee);
        }
        return "redirect:/employee/edit/" + id;
    }
}
