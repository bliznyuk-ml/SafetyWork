package org.itstep.safetywork.service;

import lombok.RequiredArgsConstructor;
import org.itstep.safetywork.model.Education;
import org.itstep.safetywork.model.Employee;
import org.itstep.safetywork.model.HighRiskWork;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class WorkPermitService {

    public boolean checkEducation (List<HighRiskWork> highRiskList, List<Education> educationList, Employee employee, RedirectAttributes model){
        Set<String> educationNpaops = new HashSet<>();
        for (Education education : educationList) {
            educationNpaops.add(education.getNpaop().getId());
        }
        // Проверяем, что все поля из первого списка присутствуют во втором списке
        for (HighRiskWork riskWork : highRiskList) {
            if (!educationNpaops.contains(riskWork.getNpaop().getId())) {
                model.addFlashAttribute("wrongEducation", "У працівника " +
                        employee.getLastName() + ' ' + employee.getFirstName() + ' ' + employee.getSurname() +
                        " відсутнє навчання за: " + riskWork.getNpaop().getId());
                return false; // Если хоть одно поле отсутствует, возвращаем false
            }
        }
        return true; // Если все поля из первого списка присутствуют, возвращаем true
    }
}
