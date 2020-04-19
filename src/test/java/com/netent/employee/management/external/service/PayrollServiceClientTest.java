package com.netent.employee.management.external.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import com.netent.employee.management.util.Util;
import com.netent.employee.management.vo.Employee;
import com.netent.employee.management.vo.EmployeeCreatePayrollResponse;
import com.netent.employee.management.vo.EmployeeDeletePayrollResponse;
import com.netent.employee.management.vo.EmployeeRetrievePayrollResponse;

@ExtendWith(MockitoExtension.class)
public class PayrollServiceClientTest {

	@Mock
	private RestTemplate restTemplate;

	@InjectMocks
	private PayrollServiceClient payrollServiceClient;

	@Test
	public void createEmployeeTest() {
		Employee employee = Util.getEmployee();
		EmployeeCreatePayrollResponse response = Util.getEmployeeCreatePayrollResponse();
		String url = Util.PAYROLL_BASE_URL + "create";
		ReflectionTestUtils.setField(payrollServiceClient, "payrollUrl", Util.PAYROLL_BASE_URL);
		Mockito.when(restTemplate.postForObject(url, employee, EmployeeCreatePayrollResponse.class))
				.thenReturn(response);
		EmployeeCreatePayrollResponse expectedResponse = payrollServiceClient.createEmployee(employee);

		Assertions.assertEquals(expectedResponse, response);
	}

	@Test
	public void deleteEmployeeTest() {
		EmployeeDeletePayrollResponse response = Util.getEmployeeDeletePayrollResopnse();
		ResponseEntity<EmployeeDeletePayrollResponse> responseEntity = new ResponseEntity<EmployeeDeletePayrollResponse>(
				response, HttpStatus.OK);
		String url = Util.PAYROLL_BASE_URL + "delete/" + Util.ID;
		ReflectionTestUtils.setField(payrollServiceClient, "payrollUrl", Util.PAYROLL_BASE_URL);
		Mockito.when(restTemplate.exchange(url, HttpMethod.DELETE, null, EmployeeDeletePayrollResponse.class))
				.thenReturn(responseEntity);
		EmployeeDeletePayrollResponse expectedResponse = payrollServiceClient.deleteEmployee(Util.ID);
		Assertions.assertEquals(expectedResponse, responseEntity.getBody());
	}

	@Test
	public void getEmployeeTest() {
		EmployeeRetrievePayrollResponse response = Util.getEmployeeRetrievePayrollResponse();
		String url = Util.PAYROLL_BASE_URL + "employee/" + Util.ID;
		ReflectionTestUtils.setField(payrollServiceClient, "payrollUrl", Util.PAYROLL_BASE_URL);
		Mockito.when(restTemplate.getForObject(url, EmployeeRetrievePayrollResponse.class)).thenReturn(response);
		EmployeeRetrievePayrollResponse expectedResponse = payrollServiceClient.getEmployee(Util.ID);
		Assertions.assertEquals(expectedResponse, response);
	}

}
