package com.netent.employee.management.service;

import com.netent.employee.management.request.EmployeeBulkCreateRequest;
import com.netent.employee.management.request.EmployeeCreateRequest;
import com.netent.employee.management.response.EmployeeBulkCreateResponse;
import com.netent.employee.management.response.EmployeeCreateResponse;
import com.netent.employee.management.response.EmployeeSearchResponse;

public interface EmployeeService {

	public EmployeeCreateResponse createEmployee(EmployeeCreateRequest employeeCreateRequest);

	public EmployeeSearchResponse searchEmployees(String searchKey, String searchValue);

	public EmployeeBulkCreateResponse createEmployees(EmployeeBulkCreateRequest employeeBulkCreateRequest);
	
	public EmployeeSearchResponse searchEmployeesV2(String searchKey, String searchValue);

}
