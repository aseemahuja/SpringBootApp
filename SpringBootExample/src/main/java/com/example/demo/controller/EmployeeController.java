package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.data.ResponseDataSource;
import com.example.demo.exception.ServiceException;
import com.example.demo.model.Employee;
import com.example.demo.model.EmployeeDataRequest;
import com.example.demo.model.GetEmployeeByIdResponse;
import com.example.demo.model.GetEmployeeListResponse;

@RestController
public class EmployeeController {
	
	
	@RequestMapping(value="/employeeList", method = RequestMethod.GET)
	public ResponseEntity<GetEmployeeListResponse> employeeList()
	{
		GetEmployeeListResponse response = new GetEmployeeListResponse();
		response.setEmployeeList(ResponseDataSource.getEmployeeData());
		
		if(null!=response.getEmployeeList() && !response.getEmployeeList().isEmpty()) {
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			throw new ServiceException("DATA_NOT_FOUND");
		}
	}
	
	@RequestMapping(value="/employeeDataById/{empId}", method= RequestMethod.GET)
	public ResponseEntity<GetEmployeeByIdResponse> employeeDataById(@PathVariable(value="empId") int empId){
		GetEmployeeByIdResponse response = new GetEmployeeByIdResponse();
		if(StringUtils.isEmpty(empId)) {
			throw new ServiceException("EMP_ID_NOT_FOUND");
		}
		
		List<Employee> empList = ResponseDataSource.getEmployeeData();
		Optional<Employee> employeeOptional = empList
				.stream()
				.filter(employee -> empId==employee.getEmpId())
				.findFirst();
		
		if(!employeeOptional.isPresent()) {
			response.setErrorMessage("Employee Id not found");
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		} else {
			response.setEmployee(employeeOptional.get());
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		
	}
	
	@RequestMapping(value="/addEmployee", method= RequestMethod.POST)
	public ResponseEntity<GetEmployeeListResponse> addEmployee(@RequestBody EmployeeDataRequest employeeDataRequest){
		GetEmployeeListResponse response = new GetEmployeeListResponse();
		if(null== employeeDataRequest
				|| null== employeeDataRequest.getEmployee()
				|| StringUtils.isEmpty(employeeDataRequest.getEmployee().getFirstName())) {
			response.setErrorMessage("Employee details not found in request.");
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
		
		List<Employee> employeeList = ResponseDataSource.getEmployeeData();
		employeeList.add(employeeDataRequest.getEmployee());
		response.setEmployeeList(employeeList);
		return new ResponseEntity<>(response, HttpStatus.OK);
		
		
		
		
	}
}
