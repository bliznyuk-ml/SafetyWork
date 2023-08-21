package org.itstep.safetywork.controller;

import lombok.RequiredArgsConstructor;
import org.itstep.safetywork.model.Manufacturer;
import org.itstep.safetywork.repository.ManufacturerRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/manufacturer")
@RequiredArgsConstructor
public class ManufacturerContrloller {
    private final ManufacturerRepository manufacturerRepository;

    @GetMapping
    String showManufacturer(Model model){
        model.addAttribute("manufacturerList", manufacturerRepository.findAll());
        return "manufacturer";
    }

    @PostMapping
    String createManufacturer(String name){
        Manufacturer manufacturer = new Manufacturer(name);
        manufacturerRepository.save(manufacturer);
        return "redirect:/manufacturer";
    }
}
