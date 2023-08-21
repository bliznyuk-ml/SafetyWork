package org.itstep.safetywork.controller;

import lombok.RequiredArgsConstructor;
import org.itstep.safetywork.command.ToolCommand;
import org.itstep.safetywork.model.Department;
import org.itstep.safetywork.model.Manufacturer;
import org.itstep.safetywork.model.Tool;
import org.itstep.safetywork.model.ToolName;
import org.itstep.safetywork.repository.DepartmentRepository;
import org.itstep.safetywork.repository.ManufacturerRepository;
import org.itstep.safetywork.repository.ToolNameRepository;
import org.itstep.safetywork.repository.ToolRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ToolController {
    private final ToolRepository toolRepository;
    private final ToolNameRepository toolNameRepository;
    private final DepartmentRepository departmentRepository;
    private final ManufacturerRepository manufacturerRepository;


    @GetMapping("/tools")
    String showTools(Model model){
        List<Tool> toolList = toolRepository.findAll();
        toolList.forEach(tool -> tool.setPeriodToNextToolChekup(Period.between(LocalDate.now(), tool.getNextTestDate())));
        model.addAttribute("tools", toolList);
        model.addAttribute("toolNameList", toolNameRepository.findAll());
        model.addAttribute("departments", departmentRepository.findAll());
        model.addAttribute("manufacturerList", manufacturerRepository.findAll());

        return "tools";
    }

    @GetMapping("/tools/edit/{idTool}")
    public String editTool(@PathVariable Integer idTool, Model model){
        Optional<Tool> optionalTool = toolRepository.findById(idTool);
        if(optionalTool.isPresent()){
            Tool tool = optionalTool.get();
            model.addAttribute("tool", tool);
        }
        return "editTool";
    }

    @GetMapping("/tools/delete/{idTool}")
    public String deleteTool(@PathVariable Integer idTool, Model model){
        Optional<Tool> optionalTool = toolRepository.findById(idTool);
        optionalTool.ifPresent(tool -> toolRepository.deleteById(idTool));
        return "redirect:/tools";
    }

    @PostMapping("/tools/edit/{idTool}")
    public String editTool(@PathVariable Integer idTool, ToolCommand command, RedirectAttributes model){
        Optional<Tool> optionalTool = toolRepository.findById(idTool);
        if(optionalTool.isPresent()){
            Tool tool = optionalTool.get();
            Period periodOfNextTest = Period.between(LocalDate.now(), command.nextTestDate());
            if((!periodOfNextTest.isNegative() || !periodOfNextTest.isZero()) && periodOfNextTest.getMonths() < 6 && periodOfNextTest.getYears() < 1){
                tool.setNextTestDate(command.nextTestDate());
                toolRepository.save(tool);
            }
            if(periodOfNextTest.isNegative() || periodOfNextTest.isZero()){
                model.addFlashAttribute("wrongPeriodOfNextTest", "Дата наступної перевірки не може бути раніше за сьогоднішню або термін перевірки виплив");
            }
            if(periodOfNextTest.getMonths() >= 6 || periodOfNextTest.getYears() >=1){
                model.addFlashAttribute("wrongPeriodOfNextTest", "Термін перевірки інструменту не може перевищувати 6 місяців");
            }
        }
        return "redirect:/tools/edit/" + idTool;
    }

    @PostMapping("/tools")
    String createTool(ToolCommand command, RedirectAttributes model){
        Tool tool = Tool.toolFromCommand(command);
        Optional<Manufacturer> optionalManufacturer = manufacturerRepository.findById(command.manufacturerId());
        Optional<ToolName> optionalToolName = toolNameRepository.findById(command.toolNameId());
        Optional<Department> optionalDepartment = departmentRepository.findById(command.departmentId());
        optionalManufacturer.ifPresent(tool::setManufacturer);
        optionalToolName.ifPresent(tool::setToolName);
        optionalDepartment.ifPresent(tool::setDepartment);
        Period periodOfNextTest = Period.between(LocalDate.now(), command.nextTestDate());
        if((!periodOfNextTest.isNegative() || !periodOfNextTest.isZero()) && periodOfNextTest.getMonths() < 6 && periodOfNextTest.getYears() < 1){
            toolRepository.save(tool);
        }
        if(periodOfNextTest.isNegative() || periodOfNextTest.isZero()){
            model.addFlashAttribute("wrongPeriodOfNextTest", "Дата наступної перевірки не може бути раніше за сьогоднішню або термін перевірки виплив");
        }
        if(periodOfNextTest.getMonths() >= 6 || periodOfNextTest.getYears() >=1){
            model.addFlashAttribute("wrongPeriodOfNextTest", "Термін перевірки інструменту не може перевищувати 6 місяців");
        }
        return "redirect:/tools";
    }

}
