package org.itstep.safetywork.controller;

import lombok.RequiredArgsConstructor;
import org.itstep.safetywork.command.HighRiskWorkCommand;
import org.itstep.safetywork.model.HighRiskWork;
import org.itstep.safetywork.model.Npaop;
import org.itstep.safetywork.repository.HighRiskWorkRepository;
import org.itstep.safetywork.repository.NpaopRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.Optional;

@Controller
@RequestMapping("/highrisk")
@RequiredArgsConstructor
public class HighRiskWorkController {
    private final HighRiskWorkRepository highRiskWorkRepository;
    private final NpaopRepository npaopRepository;
    @GetMapping
    String showHighRiskWorks(Model model){
        model.addAttribute("highRiskList", highRiskWorkRepository.findAll());
        model.addAttribute("npaopList", npaopRepository.findAll());
        return "highrisk";
    }

    @PostMapping
    String createHighRiskWork(HighRiskWorkCommand command){
        Optional<Npaop> npaopOptional = npaopRepository.findById(command.npaop());
        HighRiskWork highRiskWork = HighRiskWork.fromCommand(command);
        npaopOptional.ifPresent(highRiskWork::setNpaop);
        highRiskWorkRepository.save(highRiskWork);
        return "redirect:/highrisk";
    }
}
