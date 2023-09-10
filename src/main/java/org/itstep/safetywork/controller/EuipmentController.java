package org.itstep.safetywork.controller;

import lombok.RequiredArgsConstructor;
import org.itstep.safetywork.command.EquipmentCommand;
import org.itstep.safetywork.model.Department;
import org.itstep.safetywork.model.Employee;
import org.itstep.safetywork.model.Equipment;
import org.itstep.safetywork.model.EquipmentName;
import org.itstep.safetywork.repository.DepartmentRepository;
import org.itstep.safetywork.repository.EmployeeRepository;
import org.itstep.safetywork.repository.EquipmentNameRepository;
import org.itstep.safetywork.repository.EquipmentRepository;
import org.itstep.safetywork.service.EmployeeService;
import org.itstep.safetywork.service.EquipmentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class EuipmentController {
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final EquipmentRepository equipmentRepository;
    private final EquipmentNameRepository equipmentNameRepository;
    private final EquipmentService equipmentService;
    private final EmployeeService employeeService;

    @GetMapping("/equipment")
    public String showEquipment(Model model) {
        List<Equipment> equipmentList = equipmentRepository.findAll();
        equipmentList.forEach(equipment -> equipment.setPeriodOfNextTest(Period.between(LocalDate.now(), equipment.getNextTestDate())));
        model.addAttribute("equipmentNameList", equipmentNameRepository.findAll());
        model.addAttribute("employeeList", employeeRepository.findAll());
        model.addAttribute("departmentList", departmentRepository.findAll());
        model.addAttribute("equipmentList", equipmentList);
        return "equipment";
    }

    @GetMapping("/equipment/delete/{idEquipment}")
    public String deleteEquipment(@PathVariable Integer idEquipment) {
        Optional<Equipment> optionalEquipment = equipmentRepository.findById(idEquipment);
        optionalEquipment.ifPresent(equipment -> equipmentRepository.deleteById(idEquipment));
        return "redirect:/equipment";
    }

    @GetMapping("/equipment/edit/{idEquipment}")
    public String editEquipment(@PathVariable Integer idEquipment, Model model) {
        model.addAttribute("employeeList", employeeRepository.findAll());
        model.addAttribute("departmentList", departmentRepository.findAll());
        Optional<Equipment> optionalEquipment = equipmentRepository.findById(idEquipment);
        if (optionalEquipment.isPresent()) {
            Equipment equipment = optionalEquipment.get();
            model.addAttribute("equipment", equipment);
            String departmentName = equipment.getDepartment().getName();
            model.addAttribute("departmentName", departmentName);
            model.addAttribute("employee", equipment.getEmployee().getLastName() + ' '
                    + equipment.getEmployee().getFirstName() + ' ' + equipment.getEmployee().getSurname());
        }
        return "editEquipment";
    }

    @PostMapping("/equipment")
    public String createEquipment(EquipmentCommand command, RedirectAttributes model) {
        Equipment equipment = Equipment.equipmentFromCommand(command);
        Optional<Department> optionalDepartment = departmentRepository.findById(command.departmentId());
        Employee employee = employeeService.checkEmployee(command.employeeName(), model);
        Optional<EquipmentName> optionalEquipmentName = equipmentNameRepository.findByName(command.equipmentName());
        optionalDepartment.ifPresent(equipment::setDepartment);
        if (optionalEquipmentName.isPresent()) {
            equipment.setEquipmentName(optionalEquipmentName.get());
        } else {
            EquipmentName equipmentName = new EquipmentName(command.equipmentName());
            equipment.setEquipmentName(equipmentName);
        }
        if (employee != null) {
            equipment.setEmployee(employee);
            equipmentService.extracted(command, model, equipment);
        } else {
            model.addFlashAttribute("wrongName", "Працівник з ПІБ " + command.employeeName() + " не зареєстровано");
        }
        return "redirect:/equipment";
    }

    @PostMapping("/equipment/edit/{idEquipment}")
    public String editEquipment(@PathVariable @ModelAttribute Integer idEquipment, EquipmentCommand command, RedirectAttributes model) {
        Optional<Equipment> optionalEquipment = equipmentRepository.findById(idEquipment);
        Optional<Department> optionalDepartment = departmentRepository.findById(command.departmentId());
        Employee employee = employeeService.checkEmployee(command.employeeName(), model);
        if (optionalEquipment.isPresent()) {
            Equipment equipment = optionalEquipment.get();
            optionalDepartment.ifPresent(equipment::setDepartment);
            if (employee != null) {
                equipment.setEmployee(employee);
            } else {
                model.addFlashAttribute("wrongName", "Працівник з таким ПІБ не зареєстровано");
            }
            equipmentService.extracted(command, model, equipment);
        }
        return "redirect:/equipment/edit/" + idEquipment;
    }
}
