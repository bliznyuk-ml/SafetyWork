package org.itstep.safetywork.controller;

import lombok.RequiredArgsConstructor;
import org.itstep.safetywork.dto.ExpiringTerms;
import org.itstep.safetywork.model.*;
import org.itstep.safetywork.repository.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class StartController {
    private final ToolRepository toolRepository;
    private final EquipmentRepository equipmentRepository;
    private final MachineryRepository machineryRepository;
    private final InstructionRepository instructionRepository;
    private final MedicineRepository medicineRepository;
    private final EducationRepository educationRepository;
    private final WorkRepository workRepository;
    private final ViolationRepository violationRepository;

    @GetMapping("/")
    String showPeriods(Model model) {
        List<ExpiringTerms> expiringTermsList = new ArrayList<>();

        List<Tool> toolList = toolRepository.findAll();
        List<Tool> collectTools = toolList.stream().filter((t) ->
                        ((Period.between(LocalDate.now(), t.getNextTestDate())).getDays() < 10) &&
                                ((Period.between(LocalDate.now(), t.getNextTestDate())).getMonths() < 1) &&
                                ((Period.between(LocalDate.now(), t.getNextTestDate())).getYears() < 1))
                .toList();

        collectTools.forEach(tool ->
                expiringTermsList.add(new ExpiringTerms(
                        (tool.getToolName().getName() + ' ' + tool.getModel()),
                        (Period.between(LocalDate.now(), tool.getNextTestDate())),
                        "Спливає термін перевірки",
                        "/tools/edit/" + tool.getId()
                ))
        );

        List<Equipment> equipmentList = equipmentRepository.findAll();
        List<Equipment> collectEquipment = equipmentList.stream().filter((e) ->
                        ((Period.between(LocalDate.now(), e.getNextTestDate())).getDays() < 10) &&
                                ((Period.between(LocalDate.now(), e.getNextTestDate())).getMonths() < 1) &&
                                ((Period.between(LocalDate.now(), e.getNextTestDate())).getYears() < 1))
                .toList();

        collectEquipment.forEach(equipment ->
                expiringTermsList.add(new ExpiringTerms(
                        (equipment.getEquipmentName().getName() + ' ' + equipment.getModel()),
                        (Period.between(LocalDate.now(), equipment.getNextTestDate())),
                        "Спливає термін випробування",
                        "/equipment/edit/" + equipment.getId()
                ))
        );

        List<Machinery> machineryList = machineryRepository.findAll();
        List<Machinery> collectMachinery = machineryList.stream().filter((m) ->
                        ((Period.between(LocalDate.now(), m.getChTO())).getDays() < 10) &&
                                ((Period.between(LocalDate.now(), m.getChTO())).getMonths() < 1) &&
                                ((Period.between(LocalDate.now(), m.getChTO())).getYears() < 1))
                .toList();

        collectMachinery.forEach(machinery ->
                expiringTermsList.add(new ExpiringTerms(
                        (machinery.getTypeOfMachinery().getType() + ' ' + machinery.getMachineryModel().getModel() + ' ' + machinery.getRegistration()),
                        (Period.between(LocalDate.now(), machinery.getChTO())),
                        "Спливає термін технічного огляду",
                        "/machinery/edit/" + machinery.getId()
                ))
        );


        List<Instruction> instructionList = instructionRepository.findAll();
        instructionList.forEach(i ->
                i.setPeriodToInstruction(Period.between(LocalDate.now(), i.getReInstruction().plusMonths(3))));

        List<Instruction> collectInstruction = instructionList.stream().filter((i) ->
                        ((i.getPeriodToInstruction().isNegative()) ||
                                (((i.getPeriodToInstruction()).getDays() < 10) &&
                                        ((i.getPeriodToInstruction()).getMonths() < 1) &&
                                        ((i.getPeriodToInstruction()).getYears() < 1))))
                .toList();

        collectInstruction.forEach(instruction ->
                expiringTermsList.add(new ExpiringTerms(
                        (instruction.getEmployee().getLastName() + ' ' + instruction.getEmployee().getFirstName() + ' ' + instruction.getEmployee().getSurname()),
                        (instruction.getPeriodToInstruction()),
                        "Спливає термін повторного інструктажу",
                        "/instruction/edit/" + instruction.getId()
                ))
        );

        List<Medicine> medicineList = medicineRepository.findAll();
        List<Medicine> collectMedicine = medicineList.stream().filter((m) ->
                        ((Period.between(LocalDate.now(), m.getNextPassDate())).getDays() < 10) &&
                                ((Period.between(LocalDate.now(), m.getNextPassDate())).getMonths() < 1) &&
                                ((Period.between(LocalDate.now(), m.getNextPassDate())).getYears() < 1))
                .toList();

        collectMedicine.forEach(medicine ->
                expiringTermsList.add(new ExpiringTerms(
                        (medicine.getEmployee().getLastName() + ' ' + medicine.getEmployee().getFirstName() + ' ' + medicine.getEmployee().getSurname()),
                        (Period.between(LocalDate.now(), medicine.getNextPassDate())),
                        "Спливає термін проходження медичного огляду",
                        "/medicine/edit/" + medicine.getId()
                ))
        );

        List<Education> educationList = educationRepository.findAll();
        List<Education> collectEducation = educationList.stream().filter((e) ->
                        ((Period.between(LocalDate.now(), e.getNextPassDateEducation())).getDays() < 10) &&
                                ((Period.between(LocalDate.now(), e.getNextPassDateEducation())).getMonths() < 1) &&
                                ((Period.between(LocalDate.now(), e.getNextPassDateEducation())).getYears() < 1))
                .toList();

        collectEducation.forEach(education ->
                expiringTermsList.add(new ExpiringTerms(
                        (education.getEmployee().getLastName() + ' ' + education.getEmployee().getFirstName() + ' ' + education.getEmployee().getSurname()),
                        (Period.between(LocalDate.now(), education.getNextPassDateEducation())),
                        "Спливає термін проходження навчання за " + education.getNpaop().getId(),
                        "/addeducation/edit/" + education.getEmployee().getId() + "/edit/" + education.getId()
                ))
        );

        model.addAttribute("expiringTermsList", expiringTermsList);
        model.addAttribute("worksList", workRepository.findAllByIsDone(false));
        model.addAttribute("violationList", violationRepository.findAllViolationByWorkIsDone(false));
        return "homePage";
    }
}
