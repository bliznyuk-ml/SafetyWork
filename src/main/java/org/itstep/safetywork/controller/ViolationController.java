package org.itstep.safetywork.controller;

import lombok.RequiredArgsConstructor;
import org.itstep.safetywork.repository.ViolationRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/violation")
public class ViolationController {
    private final ViolationRepository violationRepository;

    @GetMapping
    public String showViolations(Model model){
        model.addAttribute("violationList", violationRepository.findAllViolationByWorkIsDone(false));
        return  "violation";
    }
}