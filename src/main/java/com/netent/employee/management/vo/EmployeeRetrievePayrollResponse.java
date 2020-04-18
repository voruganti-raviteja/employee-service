package com.netent.employee.management.vo;

public class EmployeeRetrievePayrollResponse {

	private String status;
	private PayrollEmployee employee;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public PayrollEmployee getEmployee() {
		return employee;
	}

	public void setEmployee(PayrollEmployee employee) {
		this.employee = employee;
	}

}
