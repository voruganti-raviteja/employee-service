package com.netent.employee.management.util;

import java.util.ArrayList;
import java.util.List;

import com.netent.employee.management.request.EmployeeBulkCreateRequest;
import com.netent.employee.management.request.EmployeeCreateRequest;
import com.netent.employee.management.response.EmployeeBulkCreateResponse;
import com.netent.employee.management.response.EmployeeCreateResponse;
import com.netent.employee.management.response.EmployeeSearchResponse;
import com.netent.employee.management.vo.Employee;
import com.netent.employee.management.vo.EmployeeCreatePayrollResponse;
import com.netent.employee.management.vo.EmployeeDeletePayrollResponse;
import com.netent.employee.management.vo.EmployeeRetrievePayrollResponse;
import com.netent.employee.management.vo.PayrollEmployee;

public class Util {

	public static int AGE = 26;
	public static double SALARY = 2100000;
	public static String NAME = "Test";
	public static long ID = 123;
	public static String NAME2 = "Testing";
	public static long ID2 = 1234;
	public static String SUCCESS = "success";
	public static String MESSAGE = "successfully! deleted Records";
	public static String PAYROLL_BASE_URL = "http://dummy.restapiexample.com/api/v1/";

	public static EmployeeCreateRequest getEmployeeCreateRequest() {
		EmployeeCreateRequest employeeCreateRequest = new EmployeeCreateRequest();
		employeeCreateRequest.setAge(AGE);
		employeeCreateRequest.setName(NAME);
		employeeCreateRequest.setSalary(SALARY);
		return employeeCreateRequest;
	}

	public static EmployeeCreateRequest getEmployeeCreateRequest2() {
		EmployeeCreateRequest employeeCreateRequest = new EmployeeCreateRequest();
		employeeCreateRequest.setAge(AGE);
		employeeCreateRequest.setName(NAME2);
		employeeCreateRequest.setSalary(SALARY);
		return employeeCreateRequest;
	}

	public static EmployeeCreateResponse getEmployeeCreateResponse() {
		EmployeeCreateResponse createResponse = new EmployeeCreateResponse();
		createResponse.setAge(AGE);
		createResponse.setName(NAME);
		createResponse.setSalary(SALARY);
		createResponse.setId(ID);
		return createResponse;
	}

	public static EmployeeCreateResponse getEmployeeCreateResponse2() {
		EmployeeCreateResponse createResponse = new EmployeeCreateResponse();
		createResponse.setAge(AGE);
		createResponse.setName(NAME2);
		createResponse.setSalary(SALARY);
		createResponse.setId(ID2);
		return createResponse;
	}

	public static EmployeeBulkCreateRequest getEmployeeBulkCreateRequest() {
		EmployeeBulkCreateRequest employeeBulkCreateRequest = new EmployeeBulkCreateRequest();
		List<EmployeeCreateRequest> employees = new ArrayList<EmployeeCreateRequest>();
		employees.add(getEmployeeCreateRequest());
		employees.add(getEmployeeCreateRequest2());
		employeeBulkCreateRequest.setEmployees(employees);
		return employeeBulkCreateRequest;
	}

	public static EmployeeBulkCreateResponse getEmployeeBulkCreateResponse() {
		EmployeeBulkCreateResponse employeeBulkCreateResponse = new EmployeeBulkCreateResponse();
		List<EmployeeCreateResponse> response = new ArrayList<EmployeeCreateResponse>();
		response.add(getEmployeeCreateResponse());
		response.add(getEmployeeCreateResponse2());
		employeeBulkCreateResponse.setEmployees(response);
		return employeeBulkCreateResponse;
	}

	public static EmployeeSearchResponse getEmployeeSearchResponse() {
		EmployeeSearchResponse employeeSearchResponse = new EmployeeSearchResponse();
		List<Employee> employees = new ArrayList<Employee>();
		Employee employee = new Employee();
		employee.setAge(AGE);
		employee.setName(NAME);
		employee.setSalary(SALARY);
		employee.setId(ID);
		employees.add(employee);
		Employee employee2 = new Employee();
		employee.setAge(AGE);
		employee.setName(NAME2);
		employee.setSalary(SALARY);
		employee.setId(ID2);
		employees.add(employee2);
		employeeSearchResponse.setEmployees(employees);
		return employeeSearchResponse;
	}

	public static EmployeeCreatePayrollResponse getEmployeeCreatePayrollResponse() {
		EmployeeCreatePayrollResponse employeeCreatePayrollResponse = new EmployeeCreatePayrollResponse();
		employeeCreatePayrollResponse.setStatus(SUCCESS);
		employeeCreatePayrollResponse.setData(getEmployee());
		return employeeCreatePayrollResponse;
	}

	public static Employee getEmployee() {
		Employee data = new Employee();
		data.setId(ID);
		data.setAge(AGE);
		data.setName(NAME);
		data.setSalary(SALARY);
		return data;
	}

	public static EmployeeDeletePayrollResponse getEmployeeDeletePayrollResopnse() {
		EmployeeDeletePayrollResponse employeeDeletePayrollResopnse = new EmployeeDeletePayrollResponse();
		employeeDeletePayrollResopnse.setMessage(MESSAGE);
		employeeDeletePayrollResopnse.setStatus(SUCCESS);
		return employeeDeletePayrollResopnse;
	}

	public static EmployeeRetrievePayrollResponse getEmployeeRetrievePayrollResponse() {
		EmployeeRetrievePayrollResponse employeeRetrievePayrollResponse = new EmployeeRetrievePayrollResponse();
		employeeRetrievePayrollResponse.setStatus(SUCCESS);
		PayrollEmployee employee = new PayrollEmployee();
		employee.setId(ID);
		employee.setAge(AGE);
		employee.setName(NAME);
		employee.setSalary(SALARY);
		employeeRetrievePayrollResponse.setEmployee(employee);
		return employeeRetrievePayrollResponse;
	}

}
