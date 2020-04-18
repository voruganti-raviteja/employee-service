package com.netent.employee.management.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PayrollEmployee {
	
	private long id;
	@JsonProperty("employee_name")
	private String name;
	@JsonProperty("employee_age")
	private int age;
	@JsonProperty("employee_salary")
	private double salary;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

}
