package org.itstep.safetywork.controller;

import lombok.RequiredArgsConstructor;
import org.itstep.safetywork.model.Employee;
import org.itstep.safetywork.model.Instruction;
import org.itstep.safetywork.repository.EmployeeRepository;
import org.itstep.safetywork.repository.InstructionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class InstructionController {
    private final EmployeeRepository employeeRepository;
    private final InstructionRepository instructionRepository;

    @GetMapping("/instruction")
    public String showInstructions(Model model){
        model.addAttribute("employeeList", employeeRepository.findAll());
        List<Instruction> instructionList = instructionRepository.findAll();
        instructionList.forEach(i ->
                i.setPeriodToInstruction(Period.between(LocalDate.now(), i.getReInstruction().plusMonths(3))));
        return "instruction";
    }

    @GetMapping("/instruction/edit/{employeeId}")
    public String showEditInstruction(@PathVariable Integer employeeId, Model model){
        Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);
        if(optionalEmployee.isPresent()){
            model.addAttribute("employee", optionalEmployee.get());
        }
        return "editInstruction";
    }

    @PostMapping("/instruction/edit/{employeeId}")
    public String editInstruction(@PathVariable @ModelAttribute Integer employeeId, Instruction instruction, RedirectAttributes model){
        Period periodOfNextPassage = Period.between(LocalDate.now(), instruction.getReInstruction());
        if(periodOfNextPassage.isNegative() || periodOfNextPassage.isZero()) {
            Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);
            if (optionalEmployee.isPresent()) {
                Employee updatedEmployee = optionalEmployee.get();
                Instruction updatedInstruction = updatedEmployee.getInstruction();
                updatedInstruction.setReInstruction(instruction.getReInstruction());
                instructionRepository.save(updatedInstruction);
            }
        }
        if(!periodOfNextPassage.isNegative() && !periodOfNextPassage.isZero()){
            model.addFlashAttribute("wrongPeriodOfIntroduction", "дата проходження інструктажу не може бути пізніше за сьогоднішню");
        }
        return "redirect:/instruction/edit/" + employeeId;
    }
}
