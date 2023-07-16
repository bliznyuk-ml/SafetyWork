package org.itstep.safetywork.controller;

import lombok.RequiredArgsConstructor;
import org.itstep.safetywork.command.ToolCommand;
import org.itstep.safetywork.model.Tool;
import org.itstep.safetywork.repository.ToolRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/tools")
@RequiredArgsConstructor
public class ToolController {
    private final ToolRepository toolRepository;

    @GetMapping
    String showTools(Model model){
        model.addAttribute("tools", toolRepository.findAll());
        return "tools";
    }

    @PostMapping
    String createTool(ToolCommand command){
        Tool tool = Tool.fromCommand(command);
        toolRepository.save(tool);
        return "redirect:/tools";
    }

}
