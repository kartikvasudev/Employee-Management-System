package com.kartikvasudev.empmgmt.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.kartikvasudev.empmgmt.constants.TestConstants.*;
import com.kartikvasudev.empmgmt.controllers.DepartmentController;
import com.kartikvasudev.empmgmt.controllers.EmployeeController;
import com.kartikvasudev.empmgmt.model.Department;
import com.kartikvasudev.empmgmt.model.Employee;
import com.kartikvasudev.empmgmt.repository.DepartmentRepository;
import com.kartikvasudev.empmgmt.request.EmployeeRequest;
import com.kartikvasudev.empmgmt.service.DepartmentServiceImpl;
import com.kartikvasudev.empmgmt.service.EmployeeServiceImpl;
import org.hamcrest.Matchers;
import org.hamcrest.Matchers.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.kartikvasudev.empmgmt.constants.TestConstants.*;
import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.definedParameter;
import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.RequestEntity.post;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@SpringBootTest

@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private static final ObjectWriter objectWriter = objectMapper.writer();

    @MockBean
    private EmployeeServiceImpl employeeServiceImpl;

    @MockBean
    private DepartmentServiceImpl departmentService;

    @Test
    public void getAllRecords_success() throws Exception
    {
        List<Employee> list = new ArrayList<>(Arrays.asList(E1,E2,E3,E4));
        Mockito.when(employeeServiceImpl.getEmployees()).thenReturn(list);
        mockMvc.perform(MockMvcRequestBuilders.get("/employees")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(4)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].name", Matchers.is("E3")));
    }

    @Test
    public void createEmployee_success() throws Exception
    {
        Employee employee = new Employee(1L,"E1",D1,EMAIL,PHONE_NO,LOCATION);

        Mockito.when(employeeServiceImpl.saveEmployee(Mockito.any(Employee.class))).thenReturn(employee);

        String content = objectWriter.writeValueAsString(employee);

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders.post("/employees")
                .contentType(MEDIA_TYPE_JSON)
                .accept(MEDIA_TYPE_JSON)
                .content(content);

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(print()).andReturn().getResponse().getContentAsString();
   }


}
