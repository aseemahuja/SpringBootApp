package com.example.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.data.model.StudentTb;
import com.example.demo.data.repoInterface.StudentRepository;

@Service
public class StudentService {
	@Autowired
	StudentRepository studentRepo;
	
	public Iterable<StudentTb> getAllStudents() {
		return studentRepo.findAll();
	}
	
	public void addStudent(StudentTb studentTb) {
		studentRepo.save(studentTb);
	}
	
	public Optional<StudentTb> findById(Integer id) {
		return studentRepo.findById(id);
	}

}
