package com.netent.employee.management.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.netent.employee.management.constants.ApplicationConstants;
import com.netent.employee.management.entity.EmployeeEntity;
import com.netent.employee.management.exception.CustomException;
import com.netent.employee.management.external.service.ElasticSearchClient;
import com.netent.employee.management.external.service.PayrollServiceClient;
import com.netent.employee.management.repository.EmployeeRepository;
import com.netent.employee.management.request.EmployeeBulkCreateRequest;
import com.netent.employee.management.request.EmployeeCreateRequest;
import com.netent.employee.management.response.EmployeeBulkCreateResponse;
import com.netent.employee.management.response.EmployeeCreateResponse;
import com.netent.employee.management.response.EmployeeSearchResponse;
import com.netent.employee.management.service.EmployeeService;
import com.netent.employee.management.util.ConverterUtil;
import com.netent.employee.management.vo.Employee;
import com.netent.employee.management.vo.EmployeeCreatePayrollResponse;
import com.netent.employee.management.vo.EmployeeRetrievePayrollResponse;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	private static Logger LOGGER = LoggerFactory.getLogger(EmployeeServiceImpl.class);

	@Autowired
	EmployeeRepository employeeRepository;
	@Autowired
	ElasticSearchClient elasticSearchClient;
	@Autowired
	PayrollServiceClient payrollServiceClient;

	@Override
	public EmployeeCreateResponse createEmployee(EmployeeCreateRequest employeeCreateRequest) {
		EmployeeCreateResponse employeeCreateResponse = null;
		setEmployeeName(employeeCreateRequest);
		Employee employee = ConverterUtil.convertToVO(employeeCreateRequest);
		EmployeeCreatePayrollResponse employeeCreatePayrollResponse = payrollServiceClient.createEmployee(employee);
		if (employeeCreatePayrollResponse != null
				&& ApplicationConstants.SUCCESS.equals(employeeCreatePayrollResponse.getStatus())
				&& employeeCreatePayrollResponse.getData() != null) {
			try {
				EmployeeEntity employeeEntity = ConverterUtil.convert(employeeCreateRequest,
						employeeCreatePayrollResponse.getData().getId());
				employeeEntity = employeeRepository.saveAndFlush(employeeEntity);
				employeeCreateResponse = ConverterUtil.convert(employeeEntity, employeeCreateRequest.getSalary());
				employee.setId(employeeCreatePayrollResponse.getData().getId());
				elasticSearchClient.indexEmployees(employee);
			} catch (Exception e) {
				LOGGER.error("Error in creating employee: " + e.getMessage());
				payrollServiceClient.deleteEmployee(employeeCreatePayrollResponse.getData().getId());
				throw new CustomException("Error in creating employee: " + e.getMessage(),
						HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		return employeeCreateResponse;
	}

	@Override
	public EmployeeSearchResponse searchEmployees(String searchKey, String searchValue) {
		EmployeeSearchResponse employeeSearchResponse = new EmployeeSearchResponse();
		List<Employee> employees = elasticSearchClient.searchEmployees(searchKey, searchValue);
		List<Employee> finalEmployees = new ArrayList<Employee>();
		// To ensure employee present in both payroll and employee management system
		employees.forEach(employee -> {
			EmployeeRetrievePayrollResponse retrieveResponse = payrollServiceClient.getEmployee(employee.getId());
			if (retrieveResponse != null && ApplicationConstants.SUCCESS.equals(retrieveResponse.getStatus())
					&& retrieveResponse.getEmployee() != null) {
				finalEmployees.add(employee);
			}
		});
		employeeSearchResponse.setEmployees(finalEmployees);
		return employeeSearchResponse;
	}

	@Override
	public EmployeeBulkCreateResponse createEmployees(EmployeeBulkCreateRequest employeeBulkCreateRequest) {
		EmployeeBulkCreateResponse employeeBulkCreateResponse = new EmployeeBulkCreateResponse();
		List<EmployeeCreateResponse> employees = new ArrayList<EmployeeCreateResponse>();
		employeeBulkCreateRequest.getEmployees().forEach(employeeCreateRequest -> {
			try {
				if (!StringUtils.isEmpty(employeeCreateRequest.getName()) && employeeCreateRequest.getAge() > 0
						&& employeeCreateRequest.getSalary() >= 0)
					employees.add(createEmployee(employeeCreateRequest));
			} catch (Exception e) {
				LOGGER.error("Exception occured while creating employee: " + employeeCreateRequest.getName()
						+ "with exception: " + e.getMessage());
			}
		});
		employeeBulkCreateResponse.setEmployees(employees);
		return employeeBulkCreateResponse;
	}

	private void setEmployeeName(EmployeeCreateRequest employeeCreateRequest) {
		EmployeeSearchResponse employeeSearchResponse = searchEmployees(ApplicationConstants.NAME_SEARCH_KEY,
				employeeCreateRequest.getName());
		if (employeeSearchResponse != null && !CollectionUtils.isEmpty(employeeSearchResponse.getEmployees())) {
			int count = employeeSearchResponse.getEmployees().size();
			String finalName = employeeCreateRequest.getName();
			List<String> employeeNames = new ArrayList<String>();
			for (Employee emp : employeeSearchResponse.getEmployees()) {
				if (emp.getName().startsWith(finalName)) {
					employeeNames.add(emp.getName());
				} else
					count--;
			}
			for (int i = count - 1; i >= 0; i--) {
				if (employeeNames.contains(finalName + i)) {
					finalName = finalName + (i + 1);
					employeeCreateRequest.setName(finalName);
					break;
				} else if (i == 0 && employeeNames.contains(finalName)) {
					finalName = finalName + 1;
					employeeCreateRequest.setName(finalName);
					break;
				}
			}
		}
	}

}
