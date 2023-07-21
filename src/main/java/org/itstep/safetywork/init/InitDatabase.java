package org.itstep.safetywork.init;

import lombok.RequiredArgsConstructor;

import org.itstep.safetywork.command.EmployeeCommand;
import org.itstep.safetywork.model.Employee;
import org.itstep.safetywork.model.Grade;
import org.itstep.safetywork.model.Profession;
import org.itstep.safetywork.model.Tool;

import org.itstep.safetywork.repository.EmployeeRepository;
import org.itstep.safetywork.repository.GradeRepository;
import org.itstep.safetywork.repository.ProfessionRepository;
import org.itstep.safetywork.repository.ToolRepository;
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


        Employee employee = Employee.fromCommand(new EmployeeCommand("Шилов", "Максим", "Викторович", LocalDate.of(1980, 6, 12), 1));
        employee.setProfession(master);
        employee.setGrade(itr);
        int age = Employee.calculateAge(employee.getBirthdate(), LocalDate.now());
        employee.setAge(age);
        employeeRepository.save(employee);
    }
}