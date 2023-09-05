package org.itstep.safetywork.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import org.itstep.safetywork.model.Employee;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmployeeDaoImpl {
    @PersistenceContext
    private EntityManager entityManager;

    public List<Employee> findLeaders(){
        return entityManager.createQuery("from Employee where grade.id = 1 order by lastName").getResultList();
    }

    public List<Employee> findWorkers(){
        return entityManager.createQuery("from Employee where grade.id = 2 order by lastName").getResultList();
    }

}
