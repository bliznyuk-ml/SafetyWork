package org.itstep.safetywork.controller;

import lombok.RequiredArgsConstructor;
import org.itstep.safetywork.model.*;
import org.itstep.safetywork.repository.*;
import org.itstep.safetywork.service.EmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/work/edit")
public class EditWorkController {
    private final WorkRepository workRepository;
    private final EmployeeService employeeService;
    private final ViolationRepository violationRepository;

    @GetMapping("/{id}")
    public String showEditWork(@PathVariable Integer id, Model model) {
        Optional<Work> optionalWork = workRepository.findById(id);
        optionalWork.ifPresent(work -> model.addAttribute("work", work));
        return "editWork";
    }

    @PostMapping("/{id}")
    public String addViolation(@PathVariable @ModelAttribute Integer id, String violation, String employeeName, RedirectAttributes model) {
        Employee employee = employeeService.checkEmployee(employeeName, model);
        Optional<Work> optionalWork = workRepository.findById(id);
        if (employee != null) {
            Violation addViolation = new Violation(violation);
            addViolation.setDate(LocalDate.now());
            addViolation.setEmployee(employee);
            optionalWork.ifPresent(addViolation::setWork);
            violationRepository.save(addViolation);
        } else {
            model.addFlashAttribute("wrongName", "Працівник з таким ПІБ не зареєстрований");
        }
        return "redirect:/work/edit/" + id;
    }

    @GetMapping("/complited/{id}")
    public String completeWork(@PathVariable Integer id) {
        Optional<Work> optionalWork = workRepository.findById(id);
        if (optionalWork.isPresent()) {
            Work work = optionalWork.get();
            work.setDone(true);

            List<Employee> employeeList = work.getEmployeeList();
            for (Employee e : employeeList) {
                e.setWork(null);
            }
            employeeList.clear();
            work.setEmployeeList(employeeList);

            List<Machinery> machineryList = work.getMachineryList();
            if (!machineryList.isEmpty()) {
                for (Machinery m : machineryList) {
                    m.setWork(null);
                }
                machineryList.clear();
                work.setMachineryList(machineryList);
            }

            List<Equipment> equipmentList = work.getEquipmentList();
            if (!equipmentList.isEmpty()) {
                for (Equipment e : equipmentList) {
                    e.setWork(null);
                }
                equipmentList.clear();
                work.setEquipmentList(equipmentList);
            }

            List<Tool> toolList = work.getToolList();
            if (!toolList.isEmpty()) {
                for (Tool t : toolList) {
                    t.setWork(null);
                }
                toolList.clear();
                work.setToolList(toolList);
            }

            workRepository.save(work);
        }
        return "redirect:/work";
    }
}