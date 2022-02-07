package com.kartikvasudev.empmgmt.model;

import com.kartikvasudev.empmgmt.request.EmployeeRequest;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
//@Table(name = "tbl_employee")
public class Employee implements Serializable {

//    @Column(name = "created_at", nullable = false, updatable = false)
//    @CreationTimestamp
//    private Date createdAt;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //@Column(name = "name") Not necessary if table field name matches the object name
    @NotNull(message = "Name should not be null")
    @NotEmpty(message = "Name should not be empty")
    @NotBlank
    private String name;

    /*@NotEmpty(message = "Department Id should not be empty")*/ // Not Empty not needed for int type
    @ManyToOne(cascade = CascadeType.ALL)
    private Department department;

    @Email(message = "Please enter valid email address")
    @NotNull(message = "Email should not be null")
    @NotEmpty(message = "Email should not be empty")
    @NotBlank
    private String email;

    @NotNull(message = "Phone number should not be null")
    @Size(min=10,max=10)
    @NotBlank
    private String phone_no;

    @NotNull(message = "location should not be null")
    @NotEmpty(message = "Location should not be empty")
    @NotBlank
    private String location;

    public Employee(Long id, String name, Department department, String email, String phone_no, String location) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.email = email;
        this.phone_no = phone_no;
        this.location = location;
    }

    public Employee(EmployeeRequest employeeRequest)
    {
        this.name = employeeRequest.getName();
        this.email = employeeRequest.getEmail();
        this.phone_no = employeeRequest.getPhone_no();
        this.location = employeeRequest.getLocation();
    }
}
//This is a Model class which is used to link entries in database to real world entities