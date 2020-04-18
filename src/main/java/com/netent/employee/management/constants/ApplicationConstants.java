package com.netent.employee.management.constants;

public interface ApplicationConstants {

	String EMPLOYEE_ES_INDEX = "employee";
	String SUCCESS = "success";
	String FAILURE = "failure";
	String INVALID_PARAMETERS = "Cannot create employee because of invalid parameters";
	String NAME_SEARCH_KEY = "name";
	String AGE_SEARCH_KEY = "age";
	
	String SEARCH_API = "/employee/search";
	String CREATE_EMPLOYEE = "/employee";
	String BULK_CREATE_EMPLOYEE = "/employees";
	String SEARCH_KEY_PARAMETER = "search_key";
	String SEARCH_VALUE_PARAMETER = "search_value";
	
	String BASE_URL = "employee_management/api/v1";
}
