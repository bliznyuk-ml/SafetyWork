package org.itstep.safetywork.controller;

import lombok.RequiredArgsConstructor;

import org.itstep.safetywork.dao.EmployeeDaoImpl;
import org.itstep.safetywork.model.Employee;
import org.itstep.safetywork.repository.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/workpermit")
public class WorkController {
    private final EmployeeRepository employeeRepository;
    private final EmployeeDaoImpl employeeDao;
    private final MachineryRepository machineryRepository;
    private final EquipmentRepository equipmentRepository;
    private final ToolRepository toolRepository;
    private final HighRiskWorkRepository highRiskWorkRepository;

    @GetMapping
    public String showWork(Model model){
        List<Employee> ld = employeeRepository.findAllByGradeId(1);
        List<Employee> leadersList = employeeDao.findLeaders();
        List<Employee> employeeList = employeeDao.findWorkers();
        model.addAttribute("leadersList", leadersList);
        model.addAttribute("employeeList", employeeList);
        model.addAttribute("highRiskWorkList", highRiskWorkRepository.findAll());
        model.addAttribute("machineryList", machineryRepository.findAll());
        model.addAttribute("equipmentList", equipmentRepository.findAll());
        model.addAttribute("toolList", toolRepository.findAll());

        return "workPermit";
    }
}
