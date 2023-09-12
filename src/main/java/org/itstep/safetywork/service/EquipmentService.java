package org.itstep.safetywork.service;

import lombok.RequiredArgsConstructor;
import org.itstep.safetywork.command.EquipmentCommand;
import org.itstep.safetywork.model.Equipment;
import org.itstep.safetywork.repository.EquipmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.Period;

@Service
@RequiredArgsConstructor
public class EquipmentService {
    private final EquipmentRepository equipmentRepository;

    public void extracted(EquipmentCommand command, RedirectAttributes model, Equipment equipment) {
        Period periodOfNextTest = Period.between(LocalDate.now(), command.nextTestDate());
        if(periodOfNextTest.isNegative() || periodOfNextTest.isZero()){
            model.addFlashAttribute("wrongPeriodOfNextTest", "Дата наступної перевірки не може бути раніше за сьогоднішню або термін перевірки виплив");
        }
        else if(periodOfNextTest.getMonths() >= 6 || periodOfNextTest.getYears() >=1){
            model.addFlashAttribute("wrongPeriodOfNextTest", "Термін перевірки інструменту не може перевищувати 6 місяців");
        } else if (equipment.getDepartment().getId().equals(equipment.getEmployee().getDepartment().getId())) {
            equipmentRepository.save(equipment);
        } else {
            model.addFlashAttribute("responsibleEmployee", "Відповідальна особа за обладнання має працювати у підрозділі, де зареєстровано обладнання");
        }
    }
}
