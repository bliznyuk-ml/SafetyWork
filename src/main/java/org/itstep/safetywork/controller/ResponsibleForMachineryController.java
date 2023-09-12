package org.itstep.safetywork.controller;

import lombok.RequiredArgsConstructor;
import org.itstep.safetywork.command.MachineryCommand;
import org.itstep.safetywork.model.ResponsibleForMachinery;
import org.itstep.safetywork.repository.EmployeeRepository;
import org.itstep.safetywork.repository.ResponsibleForMachineryRepository;
import org.itstep.safetywork.service.MachineryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/responsible")
@RequiredArgsConstructor
public class ResponsibleForMachineryController {
    private final ResponsibleForMachineryRepository responsibleForMachineryRepository;
    private final EmployeeRepository employeeRepository;
    private final MachineryService machineryService;

    @GetMapping
    public String showResponsible(Model model) {
        model.addAttribute("employeeList", employeeRepository.findAll());
        model.addAttribute("responsibleList", responsibleForMachineryRepository.findAll());
        return "responsible";
    }

    @PostMapping
    public String createResponsible(MachineryCommand command, RedirectAttributes model) {
        machineryService.addResponsibleForMachinery(command, model);
        return "redirect:/responsible";
    }

    @GetMapping("/delete/{idResponsible}")
    public String deleteResponsible(@PathVariable Integer idResponsible) {
        Optional<ResponsibleForMachinery> optionalResponsible = responsibleForMachineryRepository.findById(idResponsible);
        optionalResponsible.ifPresent(equipment -> responsibleForMachineryRepository.deleteById(idResponsible));
        return "redirect:/responsible";
    }
}
