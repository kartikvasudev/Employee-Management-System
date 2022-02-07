package com.kartikvasudev.empmgmt.service;

import com.kartikvasudev.empmgmt.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import java.util.List;

public interface EmployeeService {
    List<Employee> getEmployees();

    Employee saveEmployee(Employee employee);

    Employee getEmployeeById(Long id) throws Exception;

    void deleteEmployeeById(Long id);

    Employee updateEmployee(Employee employee);

    List<Employee> getEmployeesByName(String name);
}
