package com.netent.employee.management.controllers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.netent.employee.management.request.EmployeeBulkCreateRequest;
import com.netent.employee.management.request.EmployeeCreateRequest;
import com.netent.employee.management.response.EmployeeBulkCreateResponse;
import com.netent.employee.management.response.EmployeeCreateResponse;
import com.netent.employee.management.response.EmployeeSearchResponse;
import com.netent.employee.management.service.EmployeeService;
import com.netent.employee.management.util.Util;

@ExtendWith(MockitoExtension.class)
public class EmployeeControllerTest {

	@Mock
	EmployeeService employeeService;

	@InjectMocks
	EmployeeController employeeController;

	@Test
	public void createEmployeeTest() {

		EmployeeCreateRequest request = Util.getEmployeeCreateRequest();
		EmployeeCreateResponse response = Util.getEmployeeCreateResponse();
		Mockito.when(employeeService.createEmployee(request)).thenReturn(response);
		EmployeeCreateResponse actualResponse = employeeController.createEmployee(request);

		Assertions.assertEquals(response.getName(), actualResponse.getName());
		Assertions.assertEquals(actualResponse.getSalary(), actualResponse.getSalary());
		Assertions.assertEquals(actualResponse.getAge(), actualResponse.getAge());

	}

	@Test
	public void createEmployeesTest() {

		EmployeeBulkCreateRequest request = Util.getEmployeeBulkCreateRequest();
		EmployeeBulkCreateResponse response = Util.getEmployeeBulkCreateResponse();
		Mockito.when(employeeService.createEmployees(request)).thenReturn(response);
		EmployeeBulkCreateResponse actualResponse = employeeController.createEmployee(request);
		
		Assertions.assertEquals(actualResponse, response);
	}

	@Test
	public void searchEmployees() {

		EmployeeSearchResponse response = Util.getEmployeeSearchResponse();
		Mockito.when(employeeService.searchEmployeesV2("name", Util.NAME)).thenReturn(response);
		EmployeeSearchResponse actualResponse = employeeController.searchEmployeesV2("name", Util.NAME);

		Assertions.assertEquals(actualResponse, response);
	}

}
