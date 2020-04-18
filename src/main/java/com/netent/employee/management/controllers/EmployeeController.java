package com.netent.employee.management.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.netent.employee.management.constants.ApplicationConstants;
import com.netent.employee.management.exception.CustomException;
import com.netent.employee.management.request.EmployeeBulkCreateRequest;
import com.netent.employee.management.request.EmployeeCreateRequest;
import com.netent.employee.management.response.EmployeeBulkCreateResponse;
import com.netent.employee.management.response.EmployeeCreateResponse;
import com.netent.employee.management.response.EmployeeSearchResponse;
import com.netent.employee.management.service.EmployeeService;

@RestController
@RequestMapping(value = ApplicationConstants.BASE_URL)
public class EmployeeController {

	@Autowired
	EmployeeService employeeService;

	@PostMapping(value = ApplicationConstants.CREATE_EMPLOYEE)
	@ResponseStatus(value = HttpStatus.CREATED)
	public EmployeeCreateResponse createEmployee(@RequestBody EmployeeCreateRequest employeeCreateRequest) {
		if (employeeCreateRequest == null || employeeCreateRequest.getAge() <= 0
				|| employeeCreateRequest.getSalary() <= 0 || StringUtils.isEmpty(employeeCreateRequest.getName())) {
			throw new CustomException(ApplicationConstants.INVALID_PARAMETERS, HttpStatus.BAD_REQUEST);
		}
		return employeeService.createEmployee(employeeCreateRequest);
	}

	@PostMapping(value = ApplicationConstants.BULK_CREATE_EMPLOYEE)
	@ResponseStatus(value = HttpStatus.CREATED)
	public EmployeeBulkCreateResponse createEmployee(@RequestBody EmployeeBulkCreateRequest employeeBulkCreateRequest) {
		if (employeeBulkCreateRequest == null || CollectionUtils.isEmpty(employeeBulkCreateRequest.getEmployees())) {
			throw new CustomException(ApplicationConstants.INVALID_PARAMETERS, HttpStatus.BAD_REQUEST);
		}
		return employeeService.createEmployees(employeeBulkCreateRequest);
	}

	@GetMapping(value = ApplicationConstants.SEARCH_API)
	@ResponseStatus(value = HttpStatus.OK)
	public EmployeeSearchResponse searchEmployees(
			@RequestParam(name = ApplicationConstants.SEARCH_KEY_PARAMETER) String searchKey,
			@RequestParam(name = ApplicationConstants.SEARCH_VALUE_PARAMETER) String searchValue) {
		return employeeService.searchEmployees(searchKey, searchValue);
	}
}
