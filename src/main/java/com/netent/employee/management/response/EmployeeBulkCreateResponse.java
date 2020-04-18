package com.netent.employee.management.response;

import java.util.List;

public class EmployeeBulkCreateResponse {
	
	private List<EmployeeCreateResponse> employees;

	public List<EmployeeCreateResponse> getEmployees() {
		return employees;
	}

	public void setEmployees(List<EmployeeCreateResponse> employees) {
		this.employees = employees;
	}

}
