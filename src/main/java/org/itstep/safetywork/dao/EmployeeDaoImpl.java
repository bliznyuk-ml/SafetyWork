package org.itstep.safetywork.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.itstep.safetywork.model.Department;
import org.itstep.safetywork.model.Employee;
import org.itstep.safetywork.model.Profession;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmployeeDaoImpl {
    @PersistenceContext
    private EntityManager entityManager;

    public List<Employee> findLeaders(){
        return entityManager.createQuery("from Employee where grade.id = 1 order by lastName").getResultList();
    }
//
//    public List<Department> findLeadersDepartment(){
//        return entityManager.createQuery("select distinct department from Employee where grade.id = 1").getResultList();
//    }
//
//    public List<Profession> findLeadersProfession(){
//        return entityManager.createQuery("select distinct profession from Employee where grade.id = 1").getResultList();
//    }

    public List<Employee> findWorkers(){
        return entityManager.createQuery("from Employee where grade.id = 2 order by lastName").getResultList();
    }

//    public List<Department> findWorkersDepartment(){
//        return entityManager.createQuery("select distinct department from Employee where grade.id = 2").getResultList();
//    }
//
//    public List<Profession> findWorkersProfession(){
//        return entityManager.createQuery("select distinct profession from Employee where grade.id = 2").getResultList();
//    }
}
