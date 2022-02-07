package com.kartikvasudev.empmgmt.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kartikvasudev.empmgmt.request.DepartmentRequest;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@ToString
@NoArgsConstructor
@Table(uniqueConstraints = {@UniqueConstraint(columnNames={"name"})})
public class Department implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    @NotEmpty
    private String name;

    @NotNull
    @NotBlank
    @NotEmpty
    private String manager;

    @OneToMany(mappedBy = "department",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Employee> employees;

    public Department(Long id, String name, String manager) {
        this.id = id;
        this.name = name;
        this.manager = manager;
    }

    public Department(DepartmentRequest departmentRequest) {
        this.name = departmentRequest.getName();
        this.manager = departmentRequest.getManager();
        this.employees = new ArrayList<>();
    }
}
