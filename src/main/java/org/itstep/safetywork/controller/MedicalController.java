package org.itstep.safetywork.controller;

import lombok.RequiredArgsConstructor;
import org.itstep.safetywork.model.Employee;
import org.itstep.safetywork.model.Medicine;
import org.itstep.safetywork.repository.EmployeeRepository;
import org.itstep.safetywork.repository.MedicineRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class MedicalController {
    private final EmployeeRepository employeeRepository;
    private final MedicineRepository medicineRepository;

    @GetMapping("/medicine")
    public String showMedicine(Model model) {
        model.addAttribute("employeeList", employeeRepository.findAll());
        List<Medicine> medicineList = medicineRepository.findAll();
        medicineList.forEach(m -> m.setPeriodToMedicalChekup(Period.between(LocalDate.now(), m.getNextPassDate())));
        return "medicine";
    }

    @GetMapping("/medicine/edit/{employeeId}")
    public String showEditMedicine(@PathVariable Integer employeeId, Model model){
        Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);
        optionalEmployee.ifPresent(employee -> model.addAttribute("employee", employee));
        return "editMedicine";
    }

    @PostMapping("/medicine/edit/{employeeId}")
    public String editMedicine(@PathVariable @ModelAttribute Integer employeeId, Medicine medicine, RedirectAttributes model){
        Period periodOfPassage = Period.between(LocalDate.now(), medicine.getDateOfPassage());
        Period periodOfNextPass = Period.between(LocalDate.now(), medicine.getNextPassDate());
        if ((periodOfPassage.isNegative() || periodOfPassage.isZero()) &&
                (!periodOfNextPass.isNegative() && !periodOfNextPass.isZero())){
            Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);
            if(optionalEmployee.isPresent()){
                Employee updatedEmployee = optionalEmployee.get();
                Medicine updatedMedicine = updatedEmployee.getMedicine();
                updatedMedicine.setDateOfPassage(medicine.getDateOfPassage());
                updatedMedicine.setNextPassDate(medicine.getNextPassDate());
                updatedMedicine.setContraindications(medicine.getContraindications());
                medicineRepository.save(updatedMedicine);
            }
        }
        if (!periodOfPassage.isNegative() && !periodOfPassage.isZero()) {
            model.addFlashAttribute("wrongPeriodOfPassage", "Дата проходження медогляду не має бути пізніше за сьогоднішню");
        }
        if (periodOfNextPass.isNegative() || periodOfNextPass.isZero()) {
            model.addFlashAttribute("wrongPeriodOfNextPass", "Дата наступного медогляду не має бути раніше за сьогоднішню або термін проходження витік");
        }
        return "redirect:/medicine/edit/" + employeeId;
    }
}
