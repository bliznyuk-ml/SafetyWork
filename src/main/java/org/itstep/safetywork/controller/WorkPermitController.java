package org.itstep.safetywork.controller;

import lombok.RequiredArgsConstructor;
import org.itstep.safetywork.command.WorkPermitCommand;
import org.itstep.safetywork.dao.EmployeeDaoImpl;
import org.itstep.safetywork.model.*;
import org.itstep.safetywork.repository.*;
import org.itstep.safetywork.service.EmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.Period;
import java.util.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/workpermit")
public class WorkPermitController {
    private final EmployeeDaoImpl employeeDao;
    private final EmployeeRepository employeeRepository;
    private final MachineryRepository machineryRepository;
    private final EquipmentRepository equipmentRepository;
    private final ToolRepository toolRepository;
    private final HighRiskWorkRepository highRiskWorkRepository;
    private final WorkPermitRepository workPermitRepository;
    private final WorkRepository workRepository;
    private final EmployeeService employeeService;


    @GetMapping
    public String showWorkPermit(Model model) {
        List<Employee> leadersList = employeeDao.findLeaders();
        List<Employee> employeeList = employeeDao.findWorkers();

        List<Machinery> machineryList = machineryRepository.findAll();
        Machinery machinery = new Machinery("---", "---");
        machinery.setTypeOfMachinery(new TypeOfMachinery("---"));
        machinery.setMachineryModel(new MachineryModel("---"));
        machineryList.add(machinery);
        model.addAttribute("selected", "---");

        List<Equipment> equipmentList = equipmentRepository.findAll();
        Equipment equipment = new Equipment("---", "---");
        equipment.setEquipmentName(new EquipmentName("---"));
        equipmentList.add(equipment);

        List<Tool> toolList = toolRepository.findAll();
        Tool tool = new Tool("---", "---");
        tool.setToolName(new ToolName("---"));
        tool.setManufacturer(new Manufacturer("---"));
        toolList.add(tool);

        model.addAttribute("leadersList", leadersList);
        model.addAttribute("employeeList", employeeList);
        model.addAttribute("highRiskWorkList", highRiskWorkRepository.findAll());
        model.addAttribute("machineryList", machineryList);
        model.addAttribute("equipmentList", equipmentList);
        model.addAttribute("toolList", toolList);

        return "workPermit";
    }

    @PostMapping
    public String addWorkPermit(WorkPermitCommand command, RedirectAttributes model) {

        WorkPermit workPermit = new WorkPermit(command.workPermitNumber(), command.dateWorkPermit());

        Work work = new Work(command.name(), command.dateStart(), command.dateEnd());

         List<HighRiskWork> highRiskWorkList = new ArrayList<>();
        for (Integer id : command.highRiskId()) {
            Optional<HighRiskWork> optionalHighRisk = highRiskWorkRepository.findById(id);
            if (optionalHighRisk.isPresent()) {
                HighRiskWork highRiskWork = optionalHighRisk.get();
                work.getHighRiskWorkList().add(highRiskWork);
                highRiskWork.getWorkList().add(work);
                highRiskWorkList.add(highRiskWork);
            }
        }
    //    work.setHighRiskWorkList(highRiskWorkList);

        List<Employee> employeeList = new ArrayList<>();
        Optional<Employee> optionalLeader = employeeRepository.findById(command.leader());
        Employee leader = null;
        if (optionalLeader.isPresent()) {
            leader = optionalLeader.get();
            if (leader.getWork() == null) {
               if(checkEducation(highRiskWorkList, leader.getEducationList(), leader, model)) {
                   leader.setWork(work);
                   employeeList.add(leader);
               }
            }
        }

        for (String employeeName : command.employeeName()) {
            Employee employee = employeeService.checkEmployee(employeeName, model);
            if (employee != null) {
                if (employee.getWork() == null) {
                    if (checkEducation(highRiskWorkList, employee.getEducationList(), employee, model)) {
                        employee.setWork(work);
                        employeeList.add(employee);
                    }
                }
            }
        }
        work.setEmployeeList(employeeList);

        for (Integer id : command.machineryId()) {
            Optional<Machinery> optionalMachinery = machineryRepository.findById(id);
            if(optionalLeader.isPresent()){
                Machinery machinery = optionalMachinery.get();
                if(machinery.getWork() == null){
                    machinery.setWork(work);
                }
            }
        }

        for (Integer id : command.equipmentId()) {
            Optional<Equipment> optionalEquipment = equipmentRepository.findById(id);
            if(optionalEquipment.isPresent()){
                Equipment equipment = optionalEquipment.get();
                if(equipment.getWork() == null){
                    equipment.setWork(work);
                }
            }
        }

        for (Integer id : command.toolId()) {
            Optional<Tool> optionalTool = toolRepository.findById(id);
            if(optionalTool.isPresent()){
                Tool tool = optionalTool.get();
                if(tool.getWork() == null){
                    tool.setWork(work);
                }
            }
        }

        if (leader != null && employeeList.size() > 1) {
            workRepository.save(work);
            work.setWorkPermit(workPermit);
            workPermit.setWork(work);
            workPermitRepository.save(workPermit);
        } else {
            model.addFlashAttribute("wrongPersonal",
                    "Обраний персонал вже задіяний на інших роботах, " +
                            "або він обмежений у правах на виконання даних робіт");
        }
        return "redirect:/workpermit";
    }

    public boolean checkEducation (List<HighRiskWork> highRiskList, List<Education> educationList, Employee employee, RedirectAttributes model){
        Set<String> educationNpaops = new HashSet<>();
        for (Education education : educationList) {
            educationNpaops.add(education.getNpaop().getId());
        }
        // Проверяем, что все поля из первого списка присутствуют во втором списке
        for (HighRiskWork riskWork : highRiskList) {
            if (!educationNpaops.contains(riskWork.getNpaop().getId())) {
                model.addFlashAttribute("wrongEducation", "У працівника " +
                        employee.getLastName() + ' ' + employee.getFirstName() + ' ' + employee.getSurname() +
                        " відсутнє навчання за: " + riskWork.getNpaop().getId());
                return false; // Если хоть одно поле отсутствует, возвращаем false
            }
        }
        return true; // Если все поля из первого списка присутствуют, возвращаем true
    }
}
