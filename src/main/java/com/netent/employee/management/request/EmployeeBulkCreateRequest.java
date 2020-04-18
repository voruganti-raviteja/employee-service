package com.netent.employee.management.request;

import java.util.List;

public class EmployeeBulkCreateRequest {

	List<EmployeeCreateRequest> employees;

	public List<EmployeeCreateRequest> getEmployees() {
		return employees;
	}

	public void setEmployees(List<EmployeeCreateRequest> employees) {
		this.employees = employees;
	}
	
}
