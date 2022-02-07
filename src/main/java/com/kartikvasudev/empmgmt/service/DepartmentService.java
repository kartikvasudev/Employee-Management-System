package com.kartikvasudev.empmgmt.service;

import com.kartikvasudev.empmgmt.model.Department;

import java.util.List;

public interface DepartmentService {
    List<Department> getDepartments();

    Department saveDepartment(Department department);

    void deleteDepartmentById(Long id);

    Department updateDepartment(Department department);

    Department getDepartmentByName(String name);

    Department getDepartmentById(Long id) throws Exception;
}
