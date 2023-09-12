package org.itstep.safetywork.controller;

import lombok.RequiredArgsConstructor;
import org.itstep.safetywork.model.Npaop;
import org.itstep.safetywork.repository.NpaopRepository;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/npaop")
@RequiredArgsConstructor
public class NpaopController {
    private final NpaopRepository npaopRepository;

    @GetMapping
    String showNpaop(Model model) {
        model.addAttribute("npaopList", npaopRepository.findAll());
        return "npaop";
    }

    @PostMapping
    @Transactional
    public String create(String id, String name, String link) {
        Npaop npaop;
        if (link == null) {
            npaop = new Npaop(id, name);
        } else {
            npaop = new Npaop(id, name, link);
        }
        npaopRepository.save(npaop);
        return "redirect:/npaop";
    }
}
