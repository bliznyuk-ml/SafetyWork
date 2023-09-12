package org.itstep.safetywork.service;

import lombok.RequiredArgsConstructor;
import org.itstep.safetywork.command.MachineryCommand;
import org.itstep.safetywork.model.*;
import org.itstep.safetywork.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MachineryService {
    private final MachineryRepository machineryRepository;
    private final ResponsibleForMachineryRepository responsibleForMachineryRepository;
    private final TypeOfMachineryRepository typeOfMachineryRepository;
    private final MachineryModelRepository machineryModelRepository;
    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;
    private final EmployeeService employeeService;

    private final static Integer GRADE_RESPONSIBLE = 1;

    public void addMachinery(MachineryCommand command, RedirectAttributes model) {
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
    }

    public void editMachineryService(Integer idMachinery, MachineryCommand command, RedirectAttributes model) {
        Period periodChTO = Period.between(LocalDate.now(), command.ChTO());
        Period periodPTO = Period.between(LocalDate.now(), command.PTO());
        Optional<Machinery> optionalMachinery = machineryRepository.findById(idMachinery);
        if (optionalMachinery.isPresent()) {
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
    }

    public void addResponsibleForMachinery(MachineryCommand command, RedirectAttributes model) {
        Period periodOfOrder = Period.between(LocalDate.now(), command.dateOrder());
        if (periodOfOrder.isNegative() || periodOfOrder.isZero()) {
            ResponsibleForMachinery responsible = new ResponsibleForMachinery(command.orderNumber(), command.dateOrder());
            Employee foundedEmployee = employeeService.checkEmployee(command.employeeName(), model);
            if (foundedEmployee != null) {
                Optional<Employee> optionalEmployee = employeeRepository.findById(foundedEmployee.getId());
                optionalEmployee.ifPresent(responsible::setEmployee);
                if (foundedEmployee.getGrade().getId().equals(GRADE_RESPONSIBLE)) {
                    boolean b = foundedEmployee.getEducationList().stream().anyMatch(education -> education.getNpaop().getId().equals("НПАОП 0.00-1.80-18"));
                    boolean c = foundedEmployee.getEducationList().stream().anyMatch(education -> education.getNpaop().getId().equals("НПАОП 0.00-1.83-18"));
                    if (b && c) {
                        responsibleForMachineryRepository.save(responsible);
                    } else {
                        model.addFlashAttribute("responsibleEmployeeEducation", "Відповідальна особа не пройшла відповідного навчання");
                    }
                } else {
                    model.addFlashAttribute("responsibleEmployeeGrade", "Відповідальна особа має бути із складу інженерно-технічних працівників");
                }
            } else {
                model.addFlashAttribute("wrongName", "Працівник з таким ПІБ не зареєстровано");
            }
        } else {
            model.addFlashAttribute("wrongPeriodOfOrder", "Дата наказу не  має бути майбутнім числом");
        }
    }
}
