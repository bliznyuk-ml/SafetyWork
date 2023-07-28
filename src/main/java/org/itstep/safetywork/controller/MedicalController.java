package org.itstep.safetywork.controller;

import lombok.RequiredArgsConstructor;
import org.itstep.safetywork.model.Medicine;
import org.itstep.safetywork.repository.EmployeeRepository;
import org.itstep.safetywork.repository.MedicineRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/medicine")
@RequiredArgsConstructor
public class MedicalController {
    private final EmployeeRepository employeeRepository;
    private final MedicineRepository medicineRepository;

    @GetMapping
    public String showMedicine(Model model) {
        List<Medicine> medicineList = medicineRepository.findAll();
        model.addAttribute("employeeList", employeeRepository.findAll());
        medicineList.forEach(m -> m.setPeriodToMedicalChekup(Period.between(LocalDate.now(), m.getNextPassDate())));
        model.addAttribute("medicineList", medicineList);
        return "medicine";
    }
}
