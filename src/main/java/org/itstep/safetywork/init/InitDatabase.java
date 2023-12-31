package org.itstep.safetywork.init;

import lombok.RequiredArgsConstructor;

import org.itstep.safetywork.command.EmployeeCommand;
import org.itstep.safetywork.model.*;

import org.itstep.safetywork.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Component
@Transactional
@RequiredArgsConstructor
public class InitDatabase implements CommandLineRunner {
    private final ProfessionRepository professionRepository;
    private final EmployeeRepository employeeRepository;
    private final GradeRepository gradeRepository;
    private final DepartmentRepository departmentRepository;
    private final MedicineRepository medicineRepository;
    private final InstructionRepository instructionRepository;
    private final HighRiskWorkRepository highRiskWorkRepository;
    private final NpaopRepository npaopRepository;
    private final EducationRepository educationRepository;
    private final ToolNameRepository toolNameRepository;
    private final ManufacturerRepository manufacturerRepository;
    private final TypeOfMachineryRepository typeOfMachineryRepository;

    @Override
    public void run(String... args) throws Exception {
        toolNameRepository.save(new ToolName("Кутова шлифовальна машина"));
        toolNameRepository.save(new ToolName("Вібраційна шлифовальна машина"));
        toolNameRepository.save(new ToolName("Стрічкова шліфувальна машина"));
        toolNameRepository.save(new ToolName("Ексцентрикова шліфувальна машина"));
        toolNameRepository.save(new ToolName("Дриль"));
        toolNameRepository.save(new ToolName("Перфоратор"));
        toolNameRepository.save(new ToolName("Відбійний молот"));
        toolNameRepository.save(new ToolName("Цвяхозабивний пістолет"));
        toolNameRepository.save(new ToolName("Шурупокрут"));
        toolNameRepository.save(new ToolName("Гайкокрут"));
        toolNameRepository.save(new ToolName("Циркулярна пила"));
        toolNameRepository.save(new ToolName("Штроборіз"));
        toolNameRepository.save(new ToolName("Бетоноріз"));
        toolNameRepository.save(new ToolName("Електролобзик"));
        toolNameRepository.save(new ToolName("Електрорубанок"));
        toolNameRepository.save(new ToolName("Фрезер"));
        toolNameRepository.save(new ToolName("Електроножиці"));
        toolNameRepository.save(new ToolName("Подовжувач"));
        toolNameRepository.save(new ToolName("Зварювальний інвертор"));
        toolNameRepository.save(new ToolName("Термоклейовий пістолет"));
        toolNameRepository.save(new ToolName("Будівельний фен"));
        toolNameRepository.save(new ToolName("Електричний паяльник"));

        manufacturerRepository.save(new Manufacturer("Makita"));
        manufacturerRepository.save(new Manufacturer("Bosch"));
        manufacturerRepository.save(new Manufacturer("Metabo"));
        manufacturerRepository.save(new Manufacturer("Dnipro-M"));
        manufacturerRepository.save(new Manufacturer("DeWalt"));
        manufacturerRepository.save(new Manufacturer("DWT"));
        manufacturerRepository.save(new Manufacturer("InterTool"));
        manufacturerRepository.save(new Manufacturer("Kress"));
        manufacturerRepository.save(new Manufacturer("No Name"));


        Profession master = new Profession("Майстер");
        professionRepository.save(master);
        Profession electric = new Profession("Електромонтер");
        professionRepository.save(electric);
        professionRepository.save(new Profession("Газоелектрозварювальник"));
        professionRepository.save(new Profession("Стропальник"));
        professionRepository.save(new Profession("Машиніст автопідйомника"));
        professionRepository.save(new Profession("Машиніст крана"));

        Grade itr = new Grade("ІТР");
        gradeRepository.save(itr);
        Grade worker = new Grade("Робочі");
        gradeRepository.save(worker);

        Department remdpt = new Department("Ремонтне управління");
        departmentRepository.save(remdpt);
        departmentRepository.save(new Department("Монтажне управління"));
        departmentRepository.save(new Department("Цех виготовлення низьковольтного обладнання"));
        departmentRepository.save(new Department("Цех виготовлення високовольтного обладнання"));
        departmentRepository.save(new Department("Управління транспорту"));

        Npaop height = new Npaop("НПАОП 0.00-1.15-07", "Правила охорони праці під час виконання робіт на висоті", "https://zakon.rada.gov.ua/laws/show/z0573-07#Text");
        npaopRepository.save(height);
        Npaop oxygen = new Npaop("НПАОП 0.00-1.65-88", "Правила безпеки при виробництві та споживанні продуктів розділення повітря", "https://pdf.sop.zp.ua/npaop_0_00-1_65-88.pdf");
        npaopRepository.save(oxygen);
        Npaop fire = new Npaop("НАПБ А.01.001-2014", "Правила пожежної безпеки в Україні", "https://zakon.rada.gov.ua/laws/show/z0252-15#Text");
        npaopRepository.save(fire);
        Npaop electricalInstalations = new Npaop("НПАОП 40.1-1.01-97", "Правила безпечної експлуатації електроустановок", "https://zakon.rada.gov.ua/laws/show/z0011-98#Text");
        npaopRepository.save(electricalInstalations);
        Npaop gasHazardous = new Npaop("НПАОП 0.00-5.11-85", "Типова інструкція з організації безпечного ведення газонебезпечних робіт", "https://education.profiteh.kiev.ua/pluginfile.php/267/mod_page/content/115/%D0%9D%D0%9F%D0%90%D0%9E%D0%9F%200.00-5.11-85%20%D0%A2%D0%B8%D0%BF%D0%BE%D0%B2%D0%B0%20%D1%96%D0%BD%D1%81%D1%82%D1%80%D1%83%D0%BA%D1%86%D1%96%D1%8F%20%D0%B7%20%D0%BE%D1%80%D0%B3%D0%B0%D0%BD%D1%96%D0%B7%D0%B0%D1%86%D1%96%D1%97%20%D0%B1%D0%B5%D0%B7%D0%BF%D0%B5%D1%87%D0%BD%D0%BE%D0%B3%D0%BE%20%D0%BF%D1%80%D0%BE%D0%B2%D0%B5%D0%B4%D0%B5%D0%BD%D0%BD%D1%8F%20%D0%B3%D0%B0%D0%B7%D0%BE%D0%BD%D0%B5%D0%B1%D0%B5%D0%B7%D0%BF%D0%B5%D1%87%D0%BD%D0%B8%D1%85%20%D1%80%D0%BE%D0%B1%D1%96%D1%82.pdf");
        npaopRepository.save(gasHazardous);
        Npaop crane = new Npaop("НПАОП 0.00-1.80-18", "Правила охорони праці під час експлуатації вантажопідіймальних кранів, підіймальних пристроїв і відповідного обладнання", "https://zakon.rada.gov.ua/laws/show/z0244-18#Text");
        npaopRepository.save(gasHazardous);
        Npaop loader = new Npaop("НПАОП 0.00-1.83-18", "Правила охорони праці під час експлуатації навантажувачів", "https://zakon.rada.gov.ua/laws/show/z1082-18#Text");
        npaopRepository.save(loader);


        HighRiskWork welding = new HighRiskWork(1, "Електрозварювальні, газополум'яні, наплавочні і паяльні роботи");
        welding.setNpaop(fire);
        highRiskWorkRepository.save(welding);
        HighRiskWork electrical = new HighRiskWork(2, "Роботи в охоронних зонах ліній електропередач");
        electrical.setNpaop(electricalInstalations);
        highRiskWorkRepository.save(electrical);
        HighRiskWork cable = new HighRiskWork(3, "Роботи на кабельних лініях і діючих електроустановках");
        cable.setNpaop(electricalInstalations);
        highRiskWorkRepository.save(cable);
        HighRiskWork ignition = new HighRiskWork(10, "Роботи з легкозаймистими, займистими та вибухонебезпечними речовинами");
        ignition.setNpaop(fire);
        highRiskWorkRepository.save(ignition);
        HighRiskWork balloon = new HighRiskWork(12, "Транспортування балонів, із газами, їх заповнення та ремонт");
        balloon.setNpaop(oxygen);
        highRiskWorkRepository.save(balloon);
        HighRiskWork gas = new HighRiskWork(15, "Виконання газонебезпечних робіт");
        gas.setNpaop(gasHazardous);
        highRiskWorkRepository.save(gas);
        HighRiskWork airProducts = new HighRiskWork(54, "Роботи із застосуванням продуктів розділення повітря, природного та іншіх газів");
        airProducts.setNpaop(oxygen);
        highRiskWorkRepository.save(airProducts);
        HighRiskWork ground = new HighRiskWork(89, "Земляні роботи, що виконуються в зоні розташування підземних комунікацій");
        ground.setNpaop(electricalInstalations);
        highRiskWorkRepository.save(ground);
        HighRiskWork heightWork = new HighRiskWork(94, "Роботи верхолазні та на висоті");
        heightWork.setNpaop(height);
        highRiskWorkRepository.save(heightWork);
        HighRiskWork straightening = new HighRiskWork(95, "Роботи з підйомних і підвісних колисок і рихтувань на висоті");
        straightening.setNpaop(height);
        highRiskWorkRepository.save(straightening);
        HighRiskWork craneWork = new HighRiskWork(100, "Робота на конструкціях мостових, баштових та козлових кранів");
        craneWork.setNpaop(crane);
        highRiskWorkRepository.save(craneWork);
        HighRiskWork loading = new HighRiskWork(101, "Вантажно-розвантажувальні роботи за допомогою машин і механізмів");
        loading.setNpaop(crane);
        highRiskWorkRepository.save(loading);
        HighRiskWork craneRepair = new HighRiskWork(104, "Монтаж, ТО, ремонт вантажопідіймальних машин, ліфтів, конвеєрів, електроустановок та ліній електропередач");
        craneRepair.setNpaop(crane);
        highRiskWorkRepository.save(craneRepair);


        EmployeeCommand employeeCommand = new EmployeeCommand("Шилов", "Максим", "Викторович",
                LocalDate.of(1980, 6, 12), 1, 2,
                LocalDate.of(2022, 6, 15),
                LocalDate.of(2024, 6, 15), "Заборона виконання робіт на висоті",
                LocalDate.of(2017, 9, 4),
                LocalDate.of(2022, 9, 1));

        Employee employee = Employee.fromCommand(employeeCommand);

        employee.setProfession(master);
        employee.setGrade(itr);
        employee.setDepartment(remdpt);
        employeeRepository.save(employee);
        Medicine medicine = new Medicine(employeeCommand.dateOfPassage(), employeeCommand.nextPassDate(), employeeCommand.contraindications());
        Instruction instruction = new Instruction(employeeCommand.introduction(), employeeCommand.reInstruction());
        employee.setMedicine(medicine);
        medicine.setEmployee(employee);
        medicineRepository.save(medicine);
        employee.setInstruction(instruction);
        instruction.setEmployee(employee);
        instructionRepository.save(instruction);

        Education educationHeight = new Education("AX4573567", LocalDate.of(2023, 8, 3), LocalDate.of(2024, 8, 3));
        Optional<Npaop> heightOptional = npaopRepository.findById("НПАОП 0.00-1.15-07");
        heightOptional.ifPresent(educationHeight::setNpaop);
        educationHeight.setEmployee(employee);
        educationRepository.save(educationHeight);

        Education educationOxygen = new Education("3567", LocalDate.of(2023, 8, 3), LocalDate.of(2024, 8, 3));
        Optional<Npaop> oxygenOptional = npaopRepository.findById("НПАОП 0.00-1.65-88");
        oxygenOptional.ifPresent(educationOxygen::setNpaop);
        educationOxygen.setEmployee(employee);
        educationRepository.save(educationOxygen);

        Education educationCrane = new Education("4758", LocalDate.of(2023, 8, 3), LocalDate.of(2024, 8, 3));
        Optional<Npaop> craneOptional = npaopRepository.findById("НПАОП 0.00-1.80-18");
        craneOptional.ifPresent(educationCrane::setNpaop);
        educationCrane.setEmployee(employee);
        educationRepository.save(educationCrane);

        Education educationLoader = new Education("4758", LocalDate.of(2023, 8, 3), LocalDate.of(2024, 8, 3));
        Optional<Npaop> loaderOptional = npaopRepository.findById("НПАОП 0.00-1.83-18");
        loaderOptional.ifPresent(educationLoader::setNpaop);
        educationLoader.setEmployee(employee);
        educationRepository.save(educationLoader);


        EmployeeCommand employeeCommand2 = new EmployeeCommand("Ломикін", "Валерій", "Олексійович",
                LocalDate.of(1971, 9, 19), 2, 1,
                LocalDate.of(2022, 6, 15),
                LocalDate.of(2024, 6, 15), "",
                LocalDate.of(2017, 9, 4),
                LocalDate.of(2022, 9, 1));

        Employee employee2 = Employee.fromCommand(employeeCommand2);


        employee2.setProfession(electric);
        employee2.setGrade(worker);
        employee2.setDepartment(remdpt);
        employeeRepository.save(employee2);
        Medicine medicine2 = new Medicine(employeeCommand2.dateOfPassage(), employeeCommand2.nextPassDate(), employeeCommand2.contraindications());
        Instruction instruction2 = new Instruction(employeeCommand2.introduction(), employeeCommand2.reInstruction());
        employee2.setMedicine(medicine2);
        medicine2.setEmployee(employee2);
        medicineRepository.save(medicine2);
        employee2.setInstruction(instruction2);
        instruction2.setEmployee(employee2);
        instructionRepository.save(instruction2);

        Education educationHeight2 = new Education("567", LocalDate.of(2023, 8, 3), LocalDate.of(2024, 8, 3));
        Optional<Npaop> heightOptional2 = npaopRepository.findById("НПАОП 0.00-1.15-07");
        heightOptional2.ifPresent(educationHeight2::setNpaop);
        educationHeight2.setEmployee(employee2);
        educationRepository.save(educationHeight2);


        TypeOfMachinery truckCrane = new TypeOfMachinery("Автомобільний кран");
        TypeOfMachinery carLift = new TypeOfMachinery("Автомобільний підйомник");
        TypeOfMachinery carLoader = new TypeOfMachinery("Автонавантажувач");
        TypeOfMachinery excavator = new TypeOfMachinery("Екскаватор");

        typeOfMachineryRepository.save(truckCrane);
        typeOfMachineryRepository.save(carLift);
        typeOfMachineryRepository.save(carLoader);
        typeOfMachineryRepository.save(excavator);

        Optional<Npaop> craneOptional1 = npaopRepository.findById("НПАОП 0.00-1.80-18");
        if (craneOptional1.isPresent()) {
            truckCrane.setNpaop(craneOptional1.get());
            carLift.setNpaop(craneOptional1.get());
        }
        Optional<Npaop> loaderOptional1 = npaopRepository.findById("НПАОП 0.00-1.83-18");
        if (loaderOptional1.isPresent()) {
            carLoader.setNpaop(loaderOptional1.get());
            excavator.setNpaop(loaderOptional1.get());
        }

    }
}