package org.itstep.safetywork.controller;

import lombok.RequiredArgsConstructor;
import org.itstep.safetywork.model.Education;
import org.itstep.safetywork.repository.EducationRepository;
import org.itstep.safetywork.service.EducationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class EditEducationController {
    private final EducationRepository educationRepository;
    private final EducationService educationService;

    @GetMapping("addeducation/edit/{idEmployee}/delete/{idEducation}")
    public String delete(@PathVariable Integer idEmployee, @PathVariable Integer idEducation) {
        Optional<Education> optionalEducation = educationRepository.findById(idEducation);
        optionalEducation.ifPresent(education -> educationRepository.deleteById(idEducation));
        return "redirect:/addeducation/edit/" + idEmployee;
    }

    @GetMapping("addeducation/edit/{idEmployee}/edit/{idEducation}")
    public String showEdit(@PathVariable Integer idEmployee, @PathVariable Integer idEducation, Model model) {
        educationService.showEditEducation(idEmployee, idEducation, model);
        return "editEducation";
    }

    @PostMapping("addeducation/edit/{idEmployee}/edit/{idEducation}")
    public String edit(@PathVariable @ModelAttribute Integer idEmployee, @PathVariable Integer idEducation, Education edu, String npaopId, RedirectAttributes model) {
        educationService.editEducation(idEmployee, idEducation, edu, npaopId, model);
        return "redirect:/addeducation/edit/" + idEmployee;
    }
}
