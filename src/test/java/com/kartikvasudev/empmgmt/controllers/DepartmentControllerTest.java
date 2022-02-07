package com.kartikvasudev.empmgmt.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.kartikvasudev.empmgmt.model.Department;
import com.kartikvasudev.empmgmt.model.Employee;
import com.kartikvasudev.empmgmt.repository.DepartmentRepository;
import com.kartikvasudev.empmgmt.request.DepartmentRequest;
import com.kartikvasudev.empmgmt.service.DepartmentServiceImpl;
import com.kartikvasudev.empmgmt.service.EmployeeServiceImpl;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.kartikvasudev.empmgmt.constants.TestConstants.*;
import static com.kartikvasudev.empmgmt.constants.TestConstants.E4;
import static org.hamcrest.Matchers.aMapWithSize;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DepartmentController.class)
class DepartmentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    public static final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public static final ObjectWriter objectWriter = objectMapper.writer();

    @MockBean
    private EmployeeServiceImpl employeeServiceImpl;

    @MockBean
    private DepartmentServiceImpl departmentServiceImpl;

    @Test
    void getAllDept() throws Exception {
        List<Department> list = new ArrayList<>(Arrays.asList(D1,D2));
        Mockito.when(departmentServiceImpl.getDepartments()).thenReturn(list);
        mockMvc.perform(MockMvcRequestBuilders.get("/dept")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name", Matchers.is("D2")));
    }

    @Test
    void getDepartmentByName() throws Exception {
        Mockito.when(departmentServiceImpl.getDepartmentByName("D1")).thenReturn(D1);
        mockMvc.perform(MockMvcRequestBuilders.get("/dept/name/D1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", aMapWithSize(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("D1")));
    }

    @Test
    void getDepartmentById() throws Exception {
        Mockito.when(departmentServiceImpl.getDepartmentById(1L)).thenReturn(D1);
        mockMvc.perform(MockMvcRequestBuilders.get("/dept/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", aMapWithSize(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("D1")));
    }

    @Test
    void saveDepartment() throws Exception {
        Mockito.when(departmentServiceImpl.saveDepartment(Mockito.any(Department.class))).thenReturn(D1);

        String content = objectWriter.writeValueAsString(D1);

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders.post("/dept")
                .contentType(MEDIA_TYPE_JSON)
                .accept(MEDIA_TYPE_JSON)
                .characterEncoding("UTF-8")
                .content(content);

        String s = mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status().isCreated())
                .andDo(print()).andReturn().getResponse().getContentAsString();

        assertEquals(s,"SAVED D1");
    }

    @Test
    void deleteDepartmentById() throws Exception {
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.
                        delete("/dept/1")).
                andExpect(status().isGone()).andDo(print());

    }

    @Test
    void updateDepartmentById() throws Exception{
//        Mockito.when(departmentServiceImpl.saveDepartment(Mockito.any(Department.class))).thenReturn(D1);
    }

    @Test
    void addEmployeeToDepartment() {

    }
}