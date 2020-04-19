package com.example.demo.data;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.example.demo.data.model.StudentTb;
import com.example.demo.data.repoInterface.StudentRepository;
import com.example.demo.model.Employee;
import com.example.demo.model.Student;
import com.example.demo.service.StudentService;

@Service
public class ResponseDataSource {
	
	@Autowired
	StudentRepository studentRepo;
	
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
				s.setStudentId(100+j*10+i);
				s.setHasLaptop(true);
				s.setClassId("class_"+j);
				studentList.add(s);
			}
			
		}
		return studentList;
	}
	
	public List<Student> getStudentDataFromDB(){
		List<Student> studentList = new ArrayList<>();
		Iterable<StudentTb> studentTbIterator = studentRepo.findAll();
		Student s = null;
		if(null==studentTbIterator) {
			return null;
		}
		for(StudentTb studentTb: studentTbIterator) {
			s = new Student();
			s.setFirstName(studentTb.getFirstName());
			s.setLastName(studentTb.getLastName());
			s.setAddress(studentTb.getAddress());
			s.setClassId(studentTb.getClassId());
			s.setHasLaptop(studentTb.isHasLaptop());
			s.setStudentId(studentTb.getStudentId());
			studentList.add(s);
		}
		return studentList;
	}
	
	public StudentTb addStudentToDB(Student s) {
		StudentTb studentTb = new StudentTb();
		studentTb.setFirstName(s.getFirstName());
		studentTb.setLastName(s.getLastName());
		studentTb.setHasLaptop(s.isHasLaptop());
		studentTb.setAddress(s.getAddress());
		studentTb.setClassId(s.getClassId());
		studentTb.setStudentId(s.getStudentId());
		
		return studentRepo.save(studentTb);
		
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
