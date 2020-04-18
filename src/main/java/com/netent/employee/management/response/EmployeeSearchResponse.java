package com.netent.employee.management.response;

import java.util.List;

import com.netent.employee.management.vo.Employee;

public class EmployeeSearchResponse {

	private List<Employee> employees;

	public List<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}

}
