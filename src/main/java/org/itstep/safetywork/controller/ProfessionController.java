package org.itstep.safetywork.controller;

import lombok.RequiredArgsConstructor;
import org.itstep.safetywork.model.Profession;
import org.itstep.safetywork.repository.ProfessionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/profession")
@RequiredArgsConstructor
public class ProfessionController {
    private final ProfessionRepository professionRepository;

    @GetMapping
    public String showProfession(Model model){
        model.addAttribute("professions", professionRepository.findAll());
        return "professions";
    }

    @PostMapping
    @Transactional
    public String create(String name){
        Profession profession = new Profession(name);
        professionRepository.save(profession);
        return "redirect:/addemployee";
    }
}
