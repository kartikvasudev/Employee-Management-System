package com.kartikvasudev.empmgmt.controllers;

import com.kartikvasudev.empmgmt.model.Department;
import com.kartikvasudev.empmgmt.model.Employee;
import com.kartikvasudev.empmgmt.request.DepartmentRequest;
import com.kartikvasudev.empmgmt.service.DepartmentServiceImpl;
import com.kartikvasudev.empmgmt.service.EmployeeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class DepartmentController {
    @Autowired
    private DepartmentServiceImpl departmentService;

    @Autowired
    private EmployeeServiceImpl employeeService;

    @GetMapping("/dept")
    public ResponseEntity<List<Department>> getAllDept() {
        return new ResponseEntity<>(departmentService.getDepartments(), HttpStatus.OK);
    }

    @GetMapping("/dept/name/{name}")
    ResponseEntity<Department> getDepartmentByName(@PathVariable String name){
        Department department = null;
        try {
            department = departmentService.getDepartmentByName(name);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(department!=null)
            return new ResponseEntity<>(department,HttpStatus.OK);
        else return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
    }

    @GetMapping("/dept/{id}")
    ResponseEntity<Department> getDepartmentById(@PathVariable Long id)  {
        Department department = null;
        try {
            department = departmentService.getDepartmentById(id);
            } catch (Exception e) {
            e.printStackTrace();
            }
            if(department!=null)
            return new ResponseEntity<>(department,HttpStatus.OK);
            else return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/dept")
    public ResponseEntity<String> saveDepartment(@Valid @RequestBody Department departmentRequest) throws Exception {
//        Department department = new Department(departmentRequest);
        if(departmentService.saveDepartment(departmentRequest)!=null)
        return ResponseEntity.status(HttpStatus.CREATED).
                contentType(MediaType.APPLICATION_JSON).
                body("SAVED "+departmentRequest.getName());
        else throw new Exception("SOME ERROR");
    }

    @DeleteMapping("/dept/{id}")
    public ResponseEntity<HttpStatus> deleteDepartmentById(@PathVariable Long id)
    {
        try {
           Department department = departmentService.getDepartmentById(id);
            departmentService.deleteDepartmentById(id);
            return new ResponseEntity<>(HttpStatus.GONE);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/dept/{id}")
    public ResponseEntity<Department> updateDepartmentById(@PathVariable Long id,@Valid @RequestBody Department department)
    {
        department.setId(id);
        return new ResponseEntity<>(departmentService.updateDepartment(department),HttpStatus.OK);
    }

    //Function used to add a pre-existing employee to a pre-existing department
    @PutMapping("/dept/addEmployee/{id}")
    public void addEmployeeToDepartment(@PathVariable Long id,@RequestParam String name) throws Exception {
        Department department = getDepartmentByName(name).getBody();
        Employee employee = employeeService.getEmployeeById(id);
        if(department == null)
            throw new Exception("Department doesn't Exist");
        if(employee == null)
            throw new Exception("Employee doesn't Exist");
        employee.setId(id);
        employee.setDepartment(department);
        employeeService.updateEmployee(employee);
    }

}
