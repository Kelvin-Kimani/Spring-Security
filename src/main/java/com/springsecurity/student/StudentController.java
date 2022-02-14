package com.springsecurity.student;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1/students")
public class StudentController {

    private final List<Student> students = Arrays.asList(
            new Student(1, "Kimani Kelvin"),
            new Student(2, "Anna Bella"),
            new Student(3, "Faith Kimani"),
            new Student(4, "Geo Ndung'u"));


    @GetMapping()
    public List<Student> getStudents(){
        return students;
    }

    @GetMapping("/{studentId}")
    public Student getStudent(@PathVariable("studentId") Integer studentId){
        return students.stream()
                .filter(student -> studentId.equals(student.getStudentId()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("User with id doesn't exist"));
    }
}
