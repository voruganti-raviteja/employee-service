package com.netent.employee.management.external.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.netent.employee.management.vo.Employee;
import com.netent.employee.management.vo.EmployeeCreatePayrollResponse;
import com.netent.employee.management.vo.EmployeeDeletePayrollResopnse;
import com.netent.employee.management.vo.EmployeeRetrievePayrollResponse;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

@Component
public class PayrollServiceClient {
	
	private static Logger LOGGER = LoggerFactory.getLogger(PayrollServiceClient.class);

	@Autowired
	RestTemplate restTemplate;
	@Value("${payroll.base.url}")
	String payrollUrl;

	@HystrixCommand(fallbackMethod = "createEmployeeFallBack", commandKey = "getAllPostsCommand",
            commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000")
            })
	public EmployeeCreatePayrollResponse createEmployee(Employee employee) {
		final String uri = payrollUrl + "create";
		EmployeeCreatePayrollResponse response = null;
		try {
			response = restTemplate.postForObject(uri, employee, EmployeeCreatePayrollResponse.class);
		} catch (Exception e) {

		}
		return response;
	}

	@HystrixCommand(fallbackMethod = "deleteEmployeeFallback", commandKey = "getAllPostsCommand",
            commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000")
            })
	public EmployeeDeletePayrollResopnse deleteEmployee(long id) {
		final String uri = payrollUrl + "delete/" + id;
		EmployeeDeletePayrollResopnse response = null;
		try {
			ResponseEntity<EmployeeDeletePayrollResopnse> responseEntity = restTemplate.exchange(uri, HttpMethod.DELETE,
					null, EmployeeDeletePayrollResopnse.class);
			if (responseEntity != null) {
				response = responseEntity.getBody();
			}
		} catch (Exception e) {

		}
		return response;
	}

	@HystrixCommand(fallbackMethod = "getEmployeeFallback", commandKey = "getAllPostsCommand",
            commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000")
            })
	public EmployeeRetrievePayrollResponse getEmployee(long id) {
		final String uri = payrollUrl + "employee/" + id;
		EmployeeRetrievePayrollResponse response = null;
		try {
			response = restTemplate.getForObject(uri, EmployeeRetrievePayrollResponse.class);
		} catch (Exception e) {

		}
		return response;
	}
	
	public EmployeeDeletePayrollResopnse deleteEmployeeFallBack(Throwable commandException) {
        LOGGER.error("Error in deleting employee from payroll service. {}",commandException.getMessage());
        return null;
    }
	
	public EmployeeCreatePayrollResponse createEmployeeFallBack(Throwable commandException) {
        LOGGER.error("Error in creating employee from payroll service. {}",commandException.getMessage());
        return null;
    }
	
	public EmployeeCreatePayrollResponse getEmployeeFallback(Throwable commandException) {
        LOGGER.error("Error in fetching employee from payroll service. {}",commandException.getMessage());
        return null;
    }

}
