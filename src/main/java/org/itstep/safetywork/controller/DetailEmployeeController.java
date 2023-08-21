package org.itstep.safetywork.controller;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.itstep.safetywork.model.Education;
import org.itstep.safetywork.model.Employee;
import org.itstep.safetywork.model.HighRiskWork;
import org.itstep.safetywork.repository.EducationRepository;
import org.itstep.safetywork.repository.EmployeeRepository;
import org.itstep.safetywork.repository.HighRiskWorkRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class DetailEmployeeController {
    private final EmployeeRepository employeeRepository;
    private final EducationRepository educationRepository;
    private final HighRiskWorkRepository highRiskWorkRepository;

    @GetMapping("employee/showdetails/{id}")
    public String showDetailsEmployee(@PathVariable Integer id, Model model){
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if(optionalEmployee.isPresent()){
            Employee employee = optionalEmployee.get();
            model.addAttribute("employee", employee);
        }
        List<Education> educationList = educationRepository.findAllByEmployeeId(id);
        model.addAttribute("educationList", educationList);
        List<HighRiskWork> highRiskWorkList = new ArrayList<>();
        for(Education education : educationList){

            List<HighRiskWork> riskWorks = education.getNpaop().getHighRiskWorkList();
            highRiskWorkList.addAll(riskWorks);
        }
        if(!highRiskWorkList.isEmpty()){
            model.addAttribute("highRiskWorkList", highRiskWorkList);
        }
        return "employeeDetails";
    }
}
