package vn.elca.training.service.impl;

import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.elca.training.model.entity.Employee;
import vn.elca.training.model.entity.QEmployee;
import vn.elca.training.repository.EmployeeRepository;
import vn.elca.training.service.EmployeeService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {
    @PersistenceContext
    private EntityManager em;
    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public List<String> getAllVisa() {
        return new JPAQuery<String>(em).distinct()
                .from(QEmployee.employee)
                .select(QEmployee.employee.visa)
                .fetch();
    }

    @Override
    public List<Employee> getEmployeeByVisa(String[] visa) {
        return new JPAQuery<Employee>(em)
                .from(QEmployee.employee)
                .where(QEmployee.employee.visa.in(visa))
                .fetch();
    }
}
