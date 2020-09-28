package vn.elca.training.service;

import vn.elca.training.model.entity.Employee;

import java.util.List;
import java.util.Set;

public interface EmployeeService {
    List<String> getAllVisa();
    List<Employee> getEmployeeByVisa(String[] Visa);
}
