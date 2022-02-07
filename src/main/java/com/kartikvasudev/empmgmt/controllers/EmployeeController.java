package com.kartikvasudev.empmgmt.controllers;

import com.kartikvasudev.empmgmt.model.Department;
import com.kartikvasudev.empmgmt.model.Employee;
import com.kartikvasudev.empmgmt.request.DepartmentRequest;
import com.kartikvasudev.empmgmt.request.EmployeeRequest;
import com.kartikvasudev.empmgmt.service.DepartmentServiceImpl;
import com.kartikvasudev.empmgmt.service.EmployeeServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
public class EmployeeController {

    private static final Logger log = LogManager.getLogger(EmployeeController.class);
    @Autowired
    private DepartmentServiceImpl departmentService;

    @Autowired
    private EmployeeServiceImpl employeeService;

    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return new ResponseEntity<>(employeeService.getEmployees(), HttpStatus.OK);
    }

    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) throws Exception {
        return new ResponseEntity<>(employeeService.getEmployeeById(id), HttpStatus.OK);
    }

    @GetMapping("/employees/filterByName")
    public ResponseEntity<List<Employee>> findEmployeeByName(@RequestParam String name)
    {
        return new ResponseEntity<>(employeeService.getEmployeesByName(name),HttpStatus.OK);
    }

    @PostMapping("/employees")
    public ResponseEntity<Employee> saveEmployee(@Valid @RequestBody EmployeeRequest employeeRequest) throws Exception {
        String departmentName = employeeRequest.getDepartment().getName();
        Department department = departmentService.getDepartmentByName(departmentName);
        Employee e = new Employee(employeeRequest);
        if(department == null)
        {
            log.info("New Department is added");
            String departmentManager = employeeRequest.getDepartment().getManager();
            DepartmentRequest departmentRequest = new DepartmentRequest(departmentName,departmentManager);
            Department d = new Department(departmentRequest);
            department = d;
            d.getEmployees().add(e);
            departmentService.saveDepartment(d);
        }
        e.setDepartment(department);
        if(employeeService.saveEmployee(e)!=null)
        return new ResponseEntity<>(employeeService.saveEmployee(e),HttpStatus.CREATED);
        else
            throw new Exception("Unable to Save Employee");
    }

    @PutMapping("/employees/{id}")
    public ResponseEntity<Employee> updateEmployeeById(@PathVariable Long id, @Valid @RequestBody EmployeeRequest employeeRequest) throws Exception {
        ResponseEntity<Employee> responseEntity = getEmployeeById(id);
        Employee employee = new Employee(employeeRequest);
        employee.setId(id);
        if(responseEntity.getStatusCode() == HttpStatus.OK)
        {
            Employee emp = responseEntity.getBody();
            Department old = emp.getDepartment();
            Department newd = employeeRequest.getDepartment();
            if(Objects.equals(old.getName(), newd.getName()) && Objects.equals(old.getManager(), newd.getManager()))
                employee.setDepartment(old);
            else{
                //Check if it is asking to create a new Department or just Switching Department
                Department dep = departmentService.getDepartmentByName(newd.getName());
                if(dep == null) //Dep doesn't exist so created one
                    departmentService.saveDepartment(newd);
                else
                    newd.setId(dep.getId());
                employee.setDepartment(newd);
            }
            return new ResponseEntity<>(employeeService.updateEmployee(employee),HttpStatus.OK) ;
        }
        else{
             log.error("Employee does not exist");
             throw new Exception("Employee does not exist");
        }
    }

    @DeleteMapping("/employees/{id}")
    public ResponseEntity<HttpStatus> deleteEmployeeById(@PathVariable Long id) {
        employeeService.deleteEmployeeById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/employees/array")
    @Transactional
    public ResponseEntity<List<Employee>> addEmployees(@RequestBody List<EmployeeRequest> empArray) throws Exception {
        List<Employee> result = new ArrayList<>();
        for(EmployeeRequest e:empArray)
        {
            Employee resultEmployee = saveEmployee(e).getBody();
            result.add(resultEmployee);
        }
            return new ResponseEntity<>(result,HttpStatus.CREATED);
    }

}
