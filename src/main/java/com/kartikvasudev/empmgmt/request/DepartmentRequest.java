package com.kartikvasudev.empmgmt.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class DepartmentRequest implements Serializable {
    private String name;

    private String manager;

    public DepartmentRequest(String name, String manager) {
        this.name = name;
        this.manager = manager;
    }
}
