package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.data.ResponseDataSource;
import com.example.demo.exception.ServiceException;
import com.example.demo.model.GetStudentByIdResponse;
import com.example.demo.model.GetStudentListResponse;
import com.example.demo.model.Student;
import com.example.demo.model.StudentDataRequest;

@RestController
@RequestMapping("/student")
public class StudentController {
	private static final Logger LOGGER = LoggerFactory.getLogger(StudentController.class);
	
	@Autowired
	ResponseDataSource responseDataSource;
	
	@Value("${student.application.switch}")
	boolean applicationSwitch;
	
	@Value("${student.application.name}")
	String applicationName;
	
	@Value("#{'${student.application.studentIds}'.split(',')}")
	List<Integer> studentIdList;
	
	@RequestMapping("/data")
	public String studentData() {
		System.out.println("I'm in studentData...................");
		LOGGER.info("I'm in studentData..applicationSwitch:{}... applicationName:{}...",applicationSwitch, applicationName);
		LOGGER.error("I'm in studentData...................");
		if(applicationSwitch) {
			return "Greeting Students!! From:" +applicationName + studentIdList.get(1);
		} else {
			return "Greetings Guests!! From:" + applicationName + studentIdList.get(1);
		}
	}
	
	@RequestMapping(value="/list", method = RequestMethod.GET)
	public ResponseEntity<GetStudentListResponse> studentList()
	{
		GetStudentListResponse response = new GetStudentListResponse();
		response.setStudentList(responseDataSource.getStudentDataFromDB());
		
		if(null!=response.getStudentList() && !response.getStudentList().isEmpty()) {
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			throw new ServiceException("DATA_NOT_FOUND");
		}
	}
	
	@RequestMapping(value="/byId/{studentId}", method= RequestMethod.GET)
	public ResponseEntity<GetStudentByIdResponse> studentDataById(@PathVariable(value="studentId") int studentId){
		GetStudentByIdResponse response = new GetStudentByIdResponse();
		if(StringUtils.isEmpty(studentId)) {
			throw new ServiceException("STUDENT_ID_NOT_FOUND");
		}
		
		List<Student> studentList = ResponseDataSource.getStudentData();
		Optional<Student> studentOptional = studentList
				.stream()
				.filter(student -> studentId==student.getStudentId())
				.findFirst();
		
		if(!studentOptional.isPresent()) {
			response.setErrorMessage("Student Id not found");
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		} else {
			response.setStudent(studentOptional.get());
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		
	}
	
	@RequestMapping(value="/add", method= RequestMethod.POST)
	public ResponseEntity<GetStudentListResponse> addStudent(@RequestBody StudentDataRequest studentDataRequest){
		GetStudentListResponse response = new GetStudentListResponse();
		if(null== studentDataRequest
				|| null== studentDataRequest.getStudent()
				|| StringUtils.isEmpty(studentDataRequest.getStudent().getFirstName())) {
			response.setErrorMessage("Student details not found in request.");
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
		
		responseDataSource.addStudentToDB(studentDataRequest.getStudent());
		List<Student> studentList = responseDataSource.getStudentDataFromDB();
		//List<Student> studentList = ResponseDataSource.getStudentData();
		//studentList.add(studentDataRequest.getStudent());
		response.setStudentList(studentList);
		return new ResponseEntity<>(response, HttpStatus.OK);
		
		
		
		
	}
	
	
	
	
	

}
