package com.kartikvasudev.empmgmt.service;

import com.kartikvasudev.empmgmt.model.Employee;
import com.kartikvasudev.empmgmt.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository eRepository;

    @Override
    public List<Employee> getEmployees() {
        return eRepository.findAll();
    }

    @Override
    public Employee saveEmployee(Employee employee) {
        return eRepository.save(employee);
    }

    @Override
    public Employee getEmployeeById(Long id) throws Exception {
        Optional<Employee> optionalEmployee = eRepository.findById(id);
        if (optionalEmployee.isPresent())
            return optionalEmployee.get();
        else
            throw new Exception("EMPLOYEE NOT FOUND");
    }

    @Override
    public void deleteEmployeeById(Long id) {
        eRepository.deleteById(id);
    }

    @Override
    public Employee updateEmployee(Employee employee) {
        return eRepository.save(employee);
    }

    @Override
    public List<Employee> getEmployeesByName(String name) {
        return eRepository.findByName(name);
    }
}
