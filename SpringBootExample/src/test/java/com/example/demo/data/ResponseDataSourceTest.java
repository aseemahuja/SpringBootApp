/**
 * 
 */
package com.example.demo.data;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.data.model.StudentTb;
import com.example.demo.data.repoInterface.StudentRepository;
import com.example.demo.model.Student;


@RunWith(SpringRunner.class)
class ResponseDataSourceTest {
	
	@InjectMocks
	ResponseDataSource testClass;
	
	@Mock
	StudentRepository studentRepo;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		// to initialize all the mock values
		MockitoAnnotations.initMocks(this);
		
	}

	@Test
	void test_getStudentData() {
		List<Student> studentList = testClass.getStudentData();
		assertNotNull(studentList);
		assertFalse(studentList.isEmpty());
	}
	
	@Test
	void test_getStudentDataFromDB_empty() {
		List<StudentTb> studentTbList = new ArrayList<>();
		
		Iterable<StudentTb> iterable = () -> new Iterator<StudentTb>() {
			private int index = 0;

			@Override
			public boolean hasNext() {
				return studentTbList.size() > index;
			}

			@Override
			public StudentTb next() {
				return studentTbList.get(index++);
			}
		};
		Mockito.when(studentRepo.findAll()).thenReturn(iterable);
		List<Student> studentList = testClass.getStudentDataFromDB();
		assertNotNull(studentList);
		assertTrue(studentList.isEmpty());
	}
	
	@Test
	void test_getStudentDataFromDB_NotEmpty() {
		List<StudentTb> studentTbList = new ArrayList<>();
		StudentTb studentTb = new StudentTb();
		studentTb.setStudentId(1);
		studentTb.setFirstName("FirstName1");
		studentTbList.add(studentTb );
		
		
		Iterable<StudentTb> iterable = () -> new Iterator<StudentTb>() {
			private int index = 0;

			@Override
			public boolean hasNext() {
				return studentTbList.size() > index;
			}

			@Override
			public StudentTb next() {
				return studentTbList.get(index++);
			}
		};
		Mockito.when(studentRepo.findAll()).thenReturn(iterable);
		List<Student> studentList = testClass.getStudentDataFromDB();
		assertNotNull(studentList);
		assertFalse(studentList.isEmpty());
	}

}
