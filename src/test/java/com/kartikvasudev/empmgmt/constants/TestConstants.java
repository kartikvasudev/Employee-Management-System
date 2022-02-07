package com.kartikvasudev.empmgmt.constants;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.kartikvasudev.empmgmt.model.Department;
import com.kartikvasudev.empmgmt.model.Employee;
import org.springframework.http.MediaType;

public class TestConstants {
    public static final MediaType MEDIA_TYPE_JSON = MediaType.APPLICATION_JSON;
    public static final String EMAIL = "email1@email.com";
    public static final String PHONE_NO = "9999999999";
    public static final String LOCATION = "india";

    public static final Department D1 = new Department(1L,"D1","M1");
    public static final Department D2 = new Department(2L,"D2","M2");

    public static final Employee E1 = new Employee(1L,"E1",D1,EMAIL,PHONE_NO,LOCATION);
    public static final Employee E2 = new Employee(1L,"E2",D1,EMAIL,PHONE_NO,LOCATION);
    public static final Employee E3 = new Employee(1L,"E3",D2,EMAIL,PHONE_NO,LOCATION);
    public static final Employee E4 = new Employee(1L,"E4",D2,EMAIL,PHONE_NO,LOCATION);
}
