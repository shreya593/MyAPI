package net.javaguides.springboot.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.javaguides.springboot.exception.ResourceNotFoundException;
import net.javaguides.springboot.model.Student;
import net.javaguides.springboot.repository.StudentRepository;

@RestController
@RequestMapping("/api/v1/")
public class StudentController {
@Autowired
private StudentRepository studentRepository;
//get student
@GetMapping("student")
public List<Student>getAllStudent(){
	return this.studentRepository.findAll();
}
//get student by id
@GetMapping("/student/{id}")
public ResponseEntity<Student> getStudentById(@PathVariable(value = "id") Long studentId) throws ResourceNotFoundException{
	Student student = studentRepository.findById(studentId).orElseThrow(()-> new ResourceNotFoundException("Student not found for this id ::" + studentId));
	return ResponseEntity.ok(student);
}

@PostMapping("student")
//add student
public Student createStudent(@RequestBody Student student) {
	return this.studentRepository.save(student);
}
//update Student
@PutMapping("student/{id}")
public ResponseEntity<Student> updateStudent(@PathVariable(value = "id") Long studentId, @RequestBody Student studentDetails) throws ResourceNotFoundException{
	Student student = studentRepository.findById(studentId).orElseThrow(()-> new ResourceNotFoundException("Student not found for this id ::" + studentId));
	student.setName(studentDetails.getName());
	return ResponseEntity.ok(this.studentRepository.save(student));
}
@DeleteMapping("student/{id}")
public Map<String,Boolean>deleteStudent(@PathVariable(value = "id") Long studentId) throws ResourceNotFoundException{
	Student student = studentRepository.findById(studentId).orElseThrow(()-> new ResourceNotFoundException("Student not found for this id ::" + studentId));
	
	this.studentRepository.delete(student);
	Map<String, Boolean> response = new HashMap<>();
	response.put("deleted", Boolean.TRUE);
	return response;
}
}
