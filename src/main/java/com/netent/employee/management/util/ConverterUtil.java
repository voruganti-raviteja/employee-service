package com.netent.employee.management.util;

import com.netent.employee.management.entity.EmployeeEntity;
import com.netent.employee.management.request.EmployeeCreateRequest;
import com.netent.employee.management.response.EmployeeCreateResponse;
import com.netent.employee.management.vo.Employee;

public class ConverterUtil {

	public static EmployeeEntity convert(EmployeeCreateRequest employee, long id) {
		if (employee == null) {
			return null;
		}
		EmployeeEntity employeeEntity = new EmployeeEntity();
		employeeEntity.setName(employee.getName());
		employeeEntity.setAge(employee.getAge());
		employeeEntity.setId(id);
		return employeeEntity;
	}

	public static EmployeeCreateResponse convert(EmployeeEntity employeeEntity, double salary) {
		if (employeeEntity == null) {
			return null;
		}
		EmployeeCreateResponse employeeCreateResponse = new EmployeeCreateResponse();
		employeeCreateResponse.setAge(employeeEntity.getAge());
		employeeCreateResponse.setName(employeeEntity.getName());
		employeeCreateResponse.setId(employeeEntity.getId());
		employeeCreateResponse.setSalary(salary);
		return employeeCreateResponse;

	}

	public static Employee convertToVO(EmployeeCreateRequest employeeCreateRequest) {
		if (employeeCreateRequest == null) {
			return null;
		}
		Employee employee = new Employee();
		employee.setName(employeeCreateRequest.getName());
		employee.setSalary(employeeCreateRequest.getSalary());
		employee.setAge(employeeCreateRequest.getAge());
		return employee;
	}

}
