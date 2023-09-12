package org.itstep.safetywork.service;

import lombok.RequiredArgsConstructor;
import org.itstep.safetywork.command.ToolCommand;
import org.itstep.safetywork.model.Department;
import org.itstep.safetywork.model.Manufacturer;
import org.itstep.safetywork.model.Tool;
import org.itstep.safetywork.model.ToolName;
import org.itstep.safetywork.repository.DepartmentRepository;
import org.itstep.safetywork.repository.ManufacturerRepository;
import org.itstep.safetywork.repository.ToolNameRepository;
import org.itstep.safetywork.repository.ToolRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ToolService {
    private final ToolRepository toolRepository;
    private final ToolNameRepository toolNameRepository;
    private final DepartmentRepository departmentRepository;
    private final ManufacturerRepository manufacturerRepository;

    public void editToolService(Integer idTool, ToolCommand command, RedirectAttributes model) {
        Optional<Tool> optionalTool = toolRepository.findById(idTool);
        if (optionalTool.isPresent()) {
            Tool tool = optionalTool.get();
            Period periodOfNextTest = Period.between(LocalDate.now(), command.nextTestDate());
            if (periodOfNextTest.isNegative() || periodOfNextTest.isZero()) {
                model.addFlashAttribute("wrongPeriodOfNextTest", "Дата наступної перевірки не може бути раніше за сьогоднішню або термін перевірки виплив");
            } else if (periodOfNextTest.getMonths() >= 6 || periodOfNextTest.getYears() >= 1) {
                model.addFlashAttribute("wrongPeriodOfNextTest", "Термін перевірки інструменту не може перевищувати 6 місяців");
            } else {
                tool.setNextTestDate(command.nextTestDate());
                toolRepository.save(tool);
            }
        }
    }

    public void addToolServise(ToolCommand command, RedirectAttributes model) {
        Tool tool = Tool.toolFromCommand(command);
        Optional<Manufacturer> optionalManufacturer = manufacturerRepository.findById(command.manufacturerId());
        Optional<ToolName> optionalToolName = toolNameRepository.findById(command.toolNameId());
        Optional<Department> optionalDepartment = departmentRepository.findById(command.departmentId());
        optionalManufacturer.ifPresent(tool::setManufacturer);
        optionalToolName.ifPresent(tool::setToolName);
        optionalDepartment.ifPresent(tool::setDepartment);
        Period periodOfNextTest = Period.between(LocalDate.now(), command.nextTestDate());
        if (periodOfNextTest.isNegative() || periodOfNextTest.isZero()) {
            model.addFlashAttribute("wrongPeriodOfNextTest", "Дата наступної перевірки не може бути раніше за сьогоднішню або термін перевірки виплив");
        } else if (periodOfNextTest.getMonths() >= 6 || periodOfNextTest.getYears() >= 1) {
            model.addFlashAttribute("wrongPeriodOfNextTest", "Термін перевірки інструменту не може перевищувати 6 місяців");
        } else {
            toolRepository.save(tool);
        }
    }
}
