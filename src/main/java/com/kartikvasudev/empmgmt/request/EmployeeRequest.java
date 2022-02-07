package com.kartikvasudev.empmgmt.request;

import com.kartikvasudev.empmgmt.model.Department;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@Setter
public class EmployeeRequest {

    @NotNull
    @NotEmpty
    @NotBlank
    private String name;

    @NotNull
    private Department department;

    @NotNull
    @NotEmpty
    @NotBlank
    private String email;

    @NotNull
    @NotEmpty
    @NotBlank
    private String phone_no;

    @NotNull
    @NotEmpty
    @NotBlank
    private String location;

    public EmployeeRequest(String name, Department department, String email, String phone_no, String location) {
        this.name = name;
        this.department = department;
        this.email = email;
        this.phone_no = phone_no;
        this.location = location;
    }
}
