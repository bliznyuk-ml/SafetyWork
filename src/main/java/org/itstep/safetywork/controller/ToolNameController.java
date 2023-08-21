package org.itstep.safetywork.controller;

import lombok.RequiredArgsConstructor;
import org.itstep.safetywork.model.ToolName;
import org.itstep.safetywork.repository.ToolNameRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/toolName")
@RequiredArgsConstructor
public class ToolNameController {
    private final ToolNameRepository toolNameRepository;

    @GetMapping
    String showToolName(Model model){
        model.addAttribute("toolNameList", toolNameRepository.findAll());
        return "toolName";
    }

    @PostMapping
    String createToolName(String name){
        ToolName toolName = new ToolName(name);
        toolNameRepository.save(toolName);
        return "redirect:/toolName";
    }
}
