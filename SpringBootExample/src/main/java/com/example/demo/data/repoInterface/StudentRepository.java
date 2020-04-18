package com.example.demo.data.repoInterface;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.demo.data.model.StudentTb;


public interface StudentRepository extends CrudRepository<StudentTb, Integer>{
	
	@Query(value="SELECT * FROM StudentTb WHERE hasLaptop = true", nativeQuery = true)
	List<StudentTb> findByHasLaptop();
	
	@Query(value = "SELECT * FROM StudentTb", nativeQuery = true)
	List<StudentTb> findAllStudents();
	

}
