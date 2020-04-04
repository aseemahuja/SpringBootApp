package com.example.demo.data;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.model.Employee;
import com.example.demo.model.Student;

public class ResponseDataSource {
	
	public static List<Student> getStudentData(){
		List<Student> studentList = new ArrayList<>();
		Student s = null;
		for(int j=0;j<5;j++) {
			for(int i=0;i<j+4;i++) {
				s = new Student();
				s.setFirstName("firstName"+i);
				s.setLastName("lastName"+i);
				s.setCity("city"+i);
				s.setState("state"+i);
				s.setStudentId(100+i);
				s.setHasLaptop(true);
				s.setClassId("class_"+j);
				studentList.add(s);
			}
			
		}
		
		
		return studentList;
		
		
	}
	
	public static List<Employee> getEmployeeData(){
		List<Employee> employeeList = new ArrayList<>();
		Employee s = null;
		
		for(int i=0;i<5;i++) {
			s = new Employee();
			s.setFirstName("firstName"+i);
			s.setLastName("lastName"+i);
			s.setCity("city"+i);
			s.setState("state"+i);
			s.setEmpId(200+i);
			s.setHasLaptop(true);
			s.setClassId("class_"+i);
			employeeList.add(s);
		}
		
		return employeeList;
		
		
	}

}
