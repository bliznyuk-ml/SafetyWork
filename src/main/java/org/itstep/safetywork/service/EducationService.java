package org.itstep.safetywork.service;

import lombok.RequiredArgsConstructor;
import org.itstep.safetywork.model.Education;
import org.itstep.safetywork.model.Employee;
import org.itstep.safetywork.model.Npaop;
import org.itstep.safetywork.repository.EducationRepository;
import org.itstep.safetywork.repository.EmployeeRepository;
import org.itstep.safetywork.repository.NpaopRepository;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EducationService {
    private final NpaopRepository npaopRepository;
    private final EmployeeRepository employeeRepository;
    private final EducationRepository educationRepository;

    public void addEducationService(Integer idEmployee, Education edu, String npaopId, RedirectAttributes model) {
        Period periodOfPassage = Period.between(LocalDate.now(), edu.getDateOfPassageEducation());
        Period periodOfNextPassage = Period.between(LocalDate.now(), edu.getNextPassDateEducation());
        if ((periodOfPassage.isNegative() ||
                periodOfPassage.isZero()) &&
                (!periodOfNextPassage.isNegative() &&
                        !periodOfNextPassage.isZero())) {
            Education education = new Education(edu.getNumberOfCertificate(), edu.getDateOfPassageEducation(), edu.getNextPassDateEducation(), edu.getGroupOfEducation());
            Optional<Npaop> npaopOptional = npaopRepository.findById(npaopId);
            npaopOptional.ifPresent(education::setNpaop);
            Optional<Employee> optionalEmployee = employeeRepository.findById(idEmployee);
            if (optionalEmployee.isPresent()) {
                Employee updatedEmployee = optionalEmployee.get();
                education.setEmployee(updatedEmployee);
                educationRepository.save(education);
            }
        }
        if (!periodOfPassage.isNegative() && !periodOfPassage.isZero()) {
            model.addFlashAttribute("wrongPeriodOfPassageOfEducation", "Дата проходження навчання не може бути пізніше сьогоднішньої");
        }
        if (periodOfNextPassage.isNegative() || periodOfNextPassage.isZero()) {
            model.addFlashAttribute("wrongPeriodOfNextPassOfEducation", "Дата наступного проходження навчання не може бути рашіше за сьогоднішню, або термін наступного навчання сплинув");
        }
    }

    public void showEditEducation(Integer idEmployee, Integer idEducation, Model model) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(idEmployee);
        Employee employee = new Employee();
        optionalEmployee.ifPresent(e -> {
            employee.setFirstName(e.getFirstName());
            employee.setLastName(e.getLastName());
            employee.setSurname(e.getSurname());
        });
        Optional<Education> optionalEducation = educationRepository.findById(idEducation);
        if (optionalEducation.isPresent()) {
            Education education = optionalEducation.get();
            model.addAttribute("education", education);
            String npaopName = education.getNpaop().getName();
            model.addAttribute("npaopName", npaopName);
        }
        model.addAttribute("employee", employee);
        model.addAttribute("npaopList", npaopRepository.findAll());
    }

    public void editEducation(Integer idEmployee, Integer idEducation, Education edu, String npaopId, RedirectAttributes model) {
        Period periodOfPassage = Period.between(LocalDate.now(), edu.getDateOfPassageEducation());
        Period periodOfNextPassage = Period.between(LocalDate.now(), edu.getNextPassDateEducation());
        if ((periodOfPassage.isNegative() ||
                periodOfPassage.isZero()) &&
                (!periodOfNextPassage.isNegative() &&
                        !periodOfNextPassage.isZero())) {
            Education savedEducation = new Education(edu.getNumberOfCertificate(), edu.getDateOfPassageEducation(), edu.getNextPassDateEducation(), edu.getGroupOfEducation());
            Optional<Npaop> npaopOptional = npaopRepository.findById(npaopId);
            npaopOptional.ifPresent(savedEducation::setNpaop);
            Optional<Employee> optionalEmployee = employeeRepository.findById(idEmployee);
            if (optionalEmployee.isPresent()) {
                Employee updatedEmployee = optionalEmployee.get();
                savedEducation.setEmployee(updatedEmployee);
                Optional<Education> optionalEducation = educationRepository.findById(idEducation);
                if (optionalEducation.isPresent()) {
                    Education updatedEducation = optionalEducation.get();
                    updatedEducation.setNpaop(savedEducation.getNpaop());
                    updatedEducation.setDateOfPassageEducation(savedEducation.getDateOfPassageEducation());
                    updatedEducation.setNextPassDateEducation(savedEducation.getNextPassDateEducation());
                    updatedEducation.setGroupOfEducation(savedEducation.getGroupOfEducation());
                    updatedEducation.setNumberOfCertificate(savedEducation.getNumberOfCertificate());
                    educationRepository.save(updatedEducation);
                }
            }
        }
        if (!periodOfPassage.isNegative() && !periodOfPassage.isZero()) {
            model.addFlashAttribute("wrongPeriodOfPassageOfEducation", "Дата проходження навчання не може бути пізніше сьогоднішньої");
        }
        if (periodOfNextPassage.isNegative() || periodOfNextPassage.isZero()) {
            model.addFlashAttribute("wrongPeriodOfNextPassOfEducation", "Дата наступного проходження навчання не може бути рашіше за сьогоднішню, або термін наступного навчання сплинув");
        }
    }
}
