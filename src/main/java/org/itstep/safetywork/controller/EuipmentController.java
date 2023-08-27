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

    @GetMapping("/equipment")
    public String showEquipment(Model model){
        List<Equipment> equipmentList = equipmentRepository.findAll();
        equipmentList.forEach(equipment -> equipment.setPeriodOfNextTest(Period.between(LocalDate.now(), equipment.getNextTestDate())));
        model.addAttribute("equipmentNameList", equipmentNameRepository.findAll());
        model.addAttribute("employeeList", employeeRepository.findAll());
        model.addAttribute("departmentList", departmentRepository.findAll());
        model.addAttribute("equipmentList", equipmentList);
        return "equipment";
    }

    @GetMapping("/equipment/delete/{idEquipment}")
    public String deleteEquipment(@PathVariable Integer idEquipment){
        Optional<Equipment> optionalEquipment = equipmentRepository.findById(idEquipment);
        optionalEquipment.ifPresent(equipment -> equipmentRepository.deleteById(idEquipment));
        return "redirect:/equipment";
    }

    @GetMapping("/equipment/edit/{idEquipment}")
    public String editEquipment(@PathVariable Integer idEquipment, Model model){
        model.addAttribute("employeeList", employeeRepository.findAll());
        model.addAttribute("departmentList", departmentRepository.findAll());
        Optional<Equipment> optionalEquipment = equipmentRepository.findById(idEquipment);
        if(optionalEquipment.isPresent()){
            Equipment equipment = optionalEquipment.get();
            model.addAttribute("equipment", equipment);
            String departmentName = equipment.getDepartment().getName();
            model.addAttribute("departmentName", departmentName);
            model.addAttribute("employee", equipment.getEmployee().getLastName() + ' ' + equipment.getEmployee().getFirstName() + ' ' + equipment.getEmployee().getSurname());
        }
        return "editEquipment";
    }

    @PostMapping("/equipment")
    public String createEquipment(EquipmentCommand command, RedirectAttributes model){
        Equipment equipment = Equipment.equipmentFromCommand(command);
        Optional<Department> optionalDepartment = departmentRepository.findById(command.departmentId());
        Employee employee = equipmentService.getEmployee(command, model);
        //Optional<Employee> optionalEmployee = employeeRepository.findById(command.employeeId());
        Optional<EquipmentName> optionalEquipmentName = equipmentNameRepository.findByName(command.equipmentName());
        optionalDepartment.ifPresent(equipment::setDepartment);
        //optionalEmployee.ifPresent(equipment::setEmployee);
        if(optionalEquipmentName.isPresent()){
            equipment.setEquipmentName(optionalEquipmentName.get());
        } else {
            EquipmentName equipmentName = new EquipmentName(command.equipmentName());
            equipment.setEquipmentName(equipmentName);
        }
        if(employee != null) {
            equipment.setEmployee(employee);
            equipmentService.extracted(command, model, equipment);
        }else {
            model.addFlashAttribute("wrongName", "Працівник з таким ПІБ не зареєстровано");
        }
        return "redirect:/equipment";
    }

    @PostMapping("/equipment/edit/{idEquipment}")
    public String editEquipment(@PathVariable @ModelAttribute Integer idEquipment, EquipmentCommand command, RedirectAttributes model){
        Optional<Equipment> optionalEquipment = equipmentRepository.findById(idEquipment);
        Optional<Department> optionalDepartment = departmentRepository.findById(command.departmentId());
        Employee employee = equipmentService.getEmployee(command, model);
        if(optionalEquipment.isPresent()){
            Equipment equipment = optionalEquipment.get();
            optionalDepartment.ifPresent(equipment::setDepartment);
            if(employee != null) {
                equipment.setEmployee(employee);
            }else {
                model.addFlashAttribute("wrongName", "Працівник з таким ПІБ не зареєстровано");
            }
            equipmentService.extracted(command, model, equipment);
        }
        return "redirect:/equipment/edit/" + idEquipment;
    }

//    private Employee getEmployee(EquipmentCommand command, RedirectAttributes model) {
//        List<Employee> employeeList = employeeRepository.findAll();
//        Employee[] employeeArray = new Employee[1];
//        String[] name = command.employeeName().split("\\s+");
//        String lastName;
//        String firstName;
//        String surname;
//        if(name.length == 3) {
//            lastName = name[0];
//            firstName = name[1];
//            surname = name[2];
//        } else {
//            surname = "";
//            firstName = "";
//            lastName = "";
//            model.addFlashAttribute("wrongName", "Невірно введено ПІБ");
//        }
//        employeeList.forEach(e -> {
//            if(e.getLastName().equals(lastName) && e.getFirstName().equals(firstName) && e.getSurname().equals(surname)){
//                employeeArray[0] = e;
//            }
//        });
//        Employee employee = null;
//        if(employeeArray[0] != null){
//            employee = employeeArray[0];
//        }
//        return employee;
//    }
//
//    private void extracted(EquipmentCommand command, RedirectAttributes model, Equipment equipment) {
//        Period periodOfNextTest = Period.between(LocalDate.now(), command.nextTestDate());
//        if(periodOfNextTest.isNegative() || periodOfNextTest.isZero()){
//            model.addFlashAttribute("wrongPeriodOfNextTest", "Дата наступної перевірки не може бути раніше за сьогоднішню або термін перевірки виплив");
//        }
//        else if(periodOfNextTest.getMonths() >= 6 || periodOfNextTest.getYears() >=1){
//            model.addFlashAttribute("wrongPeriodOfNextTest", "Термін перевірки інструменту не може перевищувати 6 місяців");
//        } else if (equipment.getDepartment().getId().equals(equipment.getEmployee().getDepartment().getId())) {
//            equipmentRepository.save(equipment);
//        } else {
//            model.addFlashAttribute("responsibleEmployee", "Відповідальна особа за обладнання має працювати у підрозділі, де зареєстровано обладнання");
//        }
//    }
}
