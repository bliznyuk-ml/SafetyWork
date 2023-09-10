package org.itstep.safetywork.controller;

import lombok.RequiredArgsConstructor;
import org.itstep.safetywork.command.MachineryCommand;
import org.itstep.safetywork.model.Employee;
import org.itstep.safetywork.model.ResponsibleForMachinery;
import org.itstep.safetywork.repository.EmployeeRepository;
import org.itstep.safetywork.repository.ResponsibleForMachineryRepository;
import org.itstep.safetywork.service.EmployeeService;
import org.itstep.safetywork.service.EquipmentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

@Controller
@RequestMapping("/responsible")
@RequiredArgsConstructor
public class ResponsibleForMachineryController {
    private final ResponsibleForMachineryRepository responsibleForMachineryRepository;
    private final EmployeeRepository employeeRepository;
    private final EquipmentService equipmentService;
    private final EmployeeService employeeService;
    private final static Integer GRADE_RESPONSIBLE = 1;

    @GetMapping
    public String showResponsible(Model model) {
        model.addAttribute("employeeList", employeeRepository.findAll());
        model.addAttribute("responsibleList", responsibleForMachineryRepository.findAll());
        return "responsible";
    }

    @PostMapping
    public String createResponsible(MachineryCommand command, RedirectAttributes model) {
        Period periodOfOrder = Period.between(LocalDate.now(), command.dateOrder());
        if (periodOfOrder.isNegative() || periodOfOrder.isZero()) {
            ResponsibleForMachinery responsible = new ResponsibleForMachinery(command.orderNumber(), command.dateOrder());
            Employee foundedEmployee = employeeService.checkEmployee(command.employeeName(), model);
            if (foundedEmployee != null) {
                Optional<Employee> optionalEmployee = employeeRepository.findById(foundedEmployee.getId());
                optionalEmployee.ifPresent(responsible::setEmployee);
                if (foundedEmployee.getGrade().getId().equals(GRADE_RESPONSIBLE)) {
                    boolean b = foundedEmployee.getEducationList().stream().anyMatch(education -> education.getNpaop().getId().equals("НПАОП 0.00-1.80-18"));
                    boolean c = foundedEmployee.getEducationList().stream().anyMatch(education -> education.getNpaop().getId().equals("НПАОП 0.00-1.83-18"));
                    if (b && c) {
                        responsibleForMachineryRepository.save(responsible);
                    } else {
                        model.addFlashAttribute("responsibleEmployeeEducation", "Відповідальна особа не пройшла відповідного навчання");
                    }
                } else {
                    model.addFlashAttribute("responsibleEmployeeGrade", "Відповідальна особа має бути із складу інженерно-технічних працівників");
                }
            } else {
                model.addFlashAttribute("wrongName", "Працівник з таким ПІБ не зареєстровано");
            }
        } else {
            model.addFlashAttribute("wrongPeriodOfOrder", "Дата наказу не  має бути майбутнім числом");
        }
        return "redirect:/responsible";
    }

    @GetMapping("/delete/{idResponsible}")
    public String deleteResponsible(@PathVariable Integer idResponsible) {
        Optional<ResponsibleForMachinery> optionalResponsible = responsibleForMachineryRepository.findById(idResponsible);
        optionalResponsible.ifPresent(equipment -> responsibleForMachineryRepository.deleteById(idResponsible));
        return "redirect:/responsible";
    }
}
