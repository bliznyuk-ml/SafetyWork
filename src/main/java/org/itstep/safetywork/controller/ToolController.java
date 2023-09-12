package org.itstep.safetywork.controller;

import lombok.RequiredArgsConstructor;
import org.itstep.safetywork.command.ToolCommand;
import org.itstep.safetywork.model.Tool;
import org.itstep.safetywork.repository.DepartmentRepository;
import org.itstep.safetywork.repository.ManufacturerRepository;
import org.itstep.safetywork.repository.ToolNameRepository;
import org.itstep.safetywork.repository.ToolRepository;
import org.itstep.safetywork.service.ToolService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
    private final ToolService toolService;

    @GetMapping("/tools")
    String showTools(Model model) {
        List<Tool> toolList = toolRepository.findAll();
        toolList.forEach(tool -> tool.setPeriodToNextToolChekup(Period.between(LocalDate.now(), tool.getNextTestDate())));
        model.addAttribute("tools", toolList);
        model.addAttribute("toolNameList", toolNameRepository.findAll());
        model.addAttribute("departments", departmentRepository.findAll());
        model.addAttribute("manufacturerList", manufacturerRepository.findAll());

        return "tools";
    }

    @GetMapping("/tools/edit/{idTool}")
    public String editTool(@PathVariable Integer idTool, Model model) {
        Optional<Tool> optionalTool = toolRepository.findById(idTool);
        if (optionalTool.isPresent()) {
            Tool tool = optionalTool.get();
            model.addAttribute("tool", tool);
        }
        return "editTool";
    }

    @GetMapping("/tools/delete/{idTool}")
    public String deleteTool(@PathVariable Integer idTool) {
        Optional<Tool> optionalTool = toolRepository.findById(idTool);
        optionalTool.ifPresent(tool -> toolRepository.deleteById(idTool));
        return "redirect:/tools";
    }

    @PostMapping("/tools/edit/{idTool}")
    public String editTool(@PathVariable Integer idTool, ToolCommand command, RedirectAttributes model) {
        toolService.editToolService(idTool, command, model);
        return "redirect:/tools/edit/" + idTool;
    }

    @PostMapping("/tools")
    String createTool(ToolCommand command, RedirectAttributes model) {
        toolService.addToolServise(command, model);
        return "redirect:/tools";
    }
}
