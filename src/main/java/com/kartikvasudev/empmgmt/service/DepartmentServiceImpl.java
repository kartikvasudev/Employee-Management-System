package com.kartikvasudev.empmgmt.service;

import com.kartikvasudev.empmgmt.model.Department;
import com.kartikvasudev.empmgmt.model.Employee;
import com.kartikvasudev.empmgmt.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentServiceImpl implements DepartmentService{

    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public List<Department> getDepartments() {
        return departmentRepository.findAll();
    }

    public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    @CachePut(value = "department",key = "#department.getId()")
    public Department saveDepartment(Department department) {
        System.out.println("PUT DEPARTMENT DB CALL");
        return departmentRepository.save(department);
    }

    @Override
    @CacheEvict(value = "department",key = "#id")
    public void deleteDepartmentById(Long id) {
        System.out.println("DELETE DEPARTMENT DB CALL");
        departmentRepository.deleteById(id);
    }

    @Override
    @CachePut(value = "department",key = "#department.getId()")
    public Department updateDepartment(Department department) {
        System.out.println("UPDATE DEPARTMENT DB CALL");
        return departmentRepository.save(department);
    }

    @Override
    public Department getDepartmentByName(String name) {
        return departmentRepository.findByName(name);
    }

    @Override
    @Cacheable(value = "department",key = "#id")
    public Department getDepartmentById(Long id) throws Exception {
        System.out.println("GET DEPARTMENT DB CALL");
        Optional<Department> byId = departmentRepository.findById(id);
        if(byId.isPresent())
            return byId.get();
        else throw new Exception("NO SUCH DEPARTMENT FOUND");
    }
}
