package org.itstep.safetywork.init;

import lombok.RequiredArgsConstructor;

import org.itstep.safetywork.command.EmployeeCommand;
import org.itstep.safetywork.model.*;

import org.itstep.safetywork.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Component
@Transactional
@RequiredArgsConstructor
public class InitDatabase implements CommandLineRunner {
    private final ToolRepository toolRepository;
    private final ProfessionRepository professionRepository;
    private final EmployeeRepository employeeRepository;
    private final GradeRepository gradeRepository;
    private final DepartmentRepository departmentRepository;
    private final MedicineRepository medicineRepository;
    @Override
    public void run(String... args) throws Exception {
        toolRepository.save(new Tool("Болгарка", "DFEG346FG"));
        toolRepository.save(new Tool("Дрель", "GFDGFC987JHGJHG"));

        Profession master = new Profession("Майстер");
        professionRepository.save(master);
        professionRepository.save(new Profession("Електромонтер"));
        professionRepository.save(new Profession("Газоелектрозварювальник"));
        professionRepository.save(new Profession("Стропальник"));
        professionRepository.save(new Profession("Машиніст автопідйомника"));
        professionRepository.save(new Profession("Машиніст крана"));

        Grade itr = new Grade("ІТР");
        gradeRepository.save(itr);
        gradeRepository.save(new Grade("Робочі"));

        Department remdpt = new Department("Ремонтне управління");
        departmentRepository.save(remdpt);
        departmentRepository.save(new Department("Монтажне управління"));
        departmentRepository.save(new Department("Цех виготовлення низьковольтного обладнання"));
        departmentRepository.save(new Department("Цех виготовлення високовольтного обладнання"));
        departmentRepository.save(new Department("Управління транспорту"));

        EmployeeCommand employeeCommand = new EmployeeCommand("Шилов", "Максим", "Викторович", LocalDate.of(1980, 6, 12), 1, 2, LocalDate.of(2022, 6, 15), LocalDate.of(2024, 6, 15), "Заборона виконання робіт на висоті");

        Employee employee = Employee.fromCommand(employeeCommand);
        employee.setProfession(master);
        employee.setGrade(itr);
        employee.setDepartment(remdpt);
        employeeRepository.save(employee);
        Medicine medicine = new Medicine(employeeCommand.dateOfPassage(), employeeCommand.nextPassDate(), employeeCommand.contraindications());

        employee.setMedicine(medicine);
        medicine.setEmployee(employee);

        medicineRepository.save(medicine);
    }
}