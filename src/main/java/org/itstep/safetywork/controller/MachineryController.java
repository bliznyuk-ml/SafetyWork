package org.itstep.safetywork.controller;

import lombok.RequiredArgsConstructor;
import org.itstep.safetywork.command.MachineryCommand;
import org.itstep.safetywork.model.*;
import org.itstep.safetywork.repository.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class MachineryController {
    private final MachineryRepository machineryRepository;
    private final ResponsibleForMachineryRepository responsibleForMachineryRepository;
    private final TypeOfMachineryRepository typeOfMachineryRepository;
    private final MachineryModelRepository machineryModelRepository;
    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;
    private final NpaopRepository npaopRepository;

    @GetMapping("/machinery")
    public String showMachinery(Model model) {
        model.addAttribute("typeOfMachineryList", typeOfMachineryRepository.findAll());
        model.addAttribute("machineryModelList", machineryModelRepository.findAll());
        model.addAttribute("departmentList", departmentRepository.findAll());
        model.addAttribute("employeeList", employeeRepository.findAll());
        model.addAttribute("machineryList", machineryRepository.findAll());
        model.addAttribute("responsibleList", responsibleForMachineryRepository.findAll());
        return "machinery";
    }

    @PostMapping("/machinery")
    public String createMachinery(MachineryCommand command, RedirectAttributes model) {
        Period periodChTO = Period.between(LocalDate.now(), command.ChTO());
        Period periodPTO = Period.between(LocalDate.now(), command.PTO());
        Machinery machinery = Machinery.fromCommand(command);
        Optional<TypeOfMachinery> optionalTypeOfMachinery = typeOfMachineryRepository.findById(command.typeOfMachineryId());
        optionalTypeOfMachinery.ifPresent(machinery::setTypeOfMachinery);
        Optional<Department> optionalDepartment = departmentRepository.findById(command.departmentId());
        optionalDepartment.ifPresent(machinery::setDepartment);
        Optional<MachineryModel> optionalMachineryModel = machineryModelRepository.findByModel(command.machineryModel());
        if (optionalMachineryModel.isPresent()) {
            machinery.setMachineryModel(optionalMachineryModel.get());
        } else {
            MachineryModel machineryModel = new MachineryModel(command.machineryModel());
            machineryModelRepository.save(machineryModel);
            machinery.setMachineryModel(machineryModel);
        }
        Optional<ResponsibleForMachinery> optionalResponsible = responsibleForMachineryRepository.findById(command.responsibleId());
        optionalResponsible.ifPresent(machinery::setResponsibleForMachinery);
        if (periodPTO.isNegative() || periodPTO.isZero()) {
            model.addFlashAttribute("wrongPeriodPTO", "Дата наступного ПТО не може бути раніше за сьогоднішню або термін перевірки виплив");
        } else if (periodChTO.isZero() || periodChTO.isNegative()) {
            model.addFlashAttribute("wrongPeriodСhTO", "Дата наступного ЧТО не може бути раніше за сьогоднішню або термін перевірки виплив");
        } else if (periodPTO.getYears() >= 2) {
            model.addFlashAttribute("wrongPeriodPTO", "Термін проведення ПТО не може перевищувати 2 роки");
        } else if (periodChTO.getYears() >= 1) {
            model.addFlashAttribute("wrongPeriodChTO", "Термін проведення ЧТО не може перевищувати 1 року");
        } else {
            machineryRepository.save(machinery);
        }
        return "redirect:/machinery";
    }

    @GetMapping("/addTypeOfMachinery")
    public String showTypeOfMachinery(Model model){
        model.addAttribute("typeOfMachineryList", typeOfMachineryRepository.findAll());
        List<Npaop> npaopList = new ArrayList<>();
        Optional<Npaop> craneOptional = npaopRepository.findById("НПАОП 0.00-1.80-18");
        craneOptional.ifPresent(npaopList::add);
        Optional<Npaop> loaderOptional = npaopRepository.findById("НПАОП 0.00-1.83-18");
        loaderOptional.ifPresent(npaopList::add);
        model.addAttribute("npaopList", npaopList);
        return "typeOfMachinery";
    }

    @PostMapping("/addTypeOfMachinery")
    public String addTypeOfMachinery(MachineryCommand command){
        TypeOfMachinery type = new TypeOfMachinery(command.typeOfMachinery());
        Optional<Npaop> optionalNpaop = npaopRepository.findById(command.npaopId());
        optionalNpaop.ifPresent(type::setNpaop);
        typeOfMachineryRepository.save(type);
        return "redirect:/addTypeOfMachinery";
    }

    @GetMapping("/machinery/edit/{idMachinery}")
    public String showEditMachinery(@PathVariable Integer idMachinery, Model model){
        model.addAttribute("departmentList", departmentRepository.findAll());
        model.addAttribute("responsibleList", responsibleForMachineryRepository.findAll());
        Optional<Machinery> optionalMachinery = machineryRepository.findById(idMachinery);
        if(optionalMachinery.isPresent()){
            Machinery machinery = optionalMachinery.get();
            model.addAttribute("machineryEdit", machinery);
            model.addAttribute("departmentName", machinery.getDepartment().getName());
            model.addAttribute("responsibleId", machinery.getResponsibleForMachinery().getId());
        }
        return "editMachinery";
    }

    @PostMapping("/machinery/edit/{idMachinery}")
    public String editMachinery(@PathVariable @ModelAttribute Integer idMachinery, MachineryCommand command, RedirectAttributes model){
        Period periodChTO = Period.between(LocalDate.now(), command.ChTO());
        Period periodPTO = Period.between(LocalDate.now(), command.PTO());
        Optional<Machinery> optionalMachinery = machineryRepository.findById(idMachinery);
        if (optionalMachinery.isPresent()){
            Machinery machinery = optionalMachinery.get();
            Optional<Department> optionalDepartment = departmentRepository.findById(command.departmentId());
            optionalDepartment.ifPresent(machinery::setDepartment);
            Optional<ResponsibleForMachinery> optionalResponsible = responsibleForMachineryRepository.findById(command.responsibleId());
            optionalResponsible.ifPresent(machinery::setResponsibleForMachinery);
            machinery.setChTO(command.ChTO());
            machinery.setPTO(command.PTO());
            if (periodPTO.isNegative() || periodPTO.isZero()) {
                model.addFlashAttribute("wrongPeriodPTO", "Дата наступного ПТО не може бути раніше за сьогоднішню або термін перевірки виплив");
            } else if (periodChTO.isZero() || periodChTO.isNegative()) {
                model.addFlashAttribute("wrongPeriodСhTO", "Дата наступного ЧТО не може бути раніше за сьогоднішню або термін перевірки виплив");
            } else if (periodPTO.getYears() >= 2) {
                model.addFlashAttribute("wrongPeriodPTO", "Термін проведення ПТО не може перевищувати 2 роки");
            } else if (periodChTO.getYears() >= 1) {
                model.addFlashAttribute("wrongPeriodChTO", "Термін проведення ЧТО не може перевищувати 1 року");
            } else {
                machineryRepository.save(machinery);
            }
        }
        return "redirect:/machinery/edit/" + idMachinery;
    }

    @GetMapping("/machinery/delete/{idMachinery}")
    public String deleteMachinery(@PathVariable Integer idMachinery){
        Optional<Machinery> optionalMachinery = machineryRepository.findById(idMachinery);
        optionalMachinery.ifPresent(machinery -> machineryRepository.delete(machinery));
        return "redirect:/machinery";
    }
}

