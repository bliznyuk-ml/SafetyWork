package org.itstep.safetywork.controller;

import lombok.RequiredArgsConstructor;
import org.itstep.safetywork.repository.WorkRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/work")
public class WorkController {
    private final WorkRepository workRepository;

    @GetMapping
    public String showWorks(Model model) {
        model.addAttribute("worksList", workRepository.findAllByIsDone(false));
        model.addAttribute("title", "Роботи, що виконуються");
        return "works";
    }

    @GetMapping("/completed")
    public String showCompletedWorks(Model model) {
        model.addAttribute("worksList", workRepository.findAllByIsDone(true));
        model.addAttribute("title", "Архів робіт, що виконані");
        return "works";
    }

    @GetMapping("/addViolation")
    public String showWorksViolation(Model model) {
        model.addAttribute("worksList", workRepository.findAllByIsDone(false));
        model.addAttribute("title", "Обрати роботу, де виявлено порушення");
        return "works";
    }

}