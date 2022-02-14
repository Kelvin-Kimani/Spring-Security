package com.springsecurity.student;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/management/api/v1/students")
/** PreAuthorize in the class is used for Role based, or also in our case, Permission based authorization
 *
 * uses - hasRole('ROLE_USER'), hasAnyRole('ROLE_USER', 'ROLE_ADMIN', etc), hasAuthority('some_authority'), hasAnyAuthority('some_authority', etc)
 *
 * **/
public class StudentManagementController {


    private final List<Student> students = Arrays.asList(
            new Student(1, "Kimani Kelvin"),
            new Student(2, "Anna Bella"),
            new Student(3, "Faith Kimani"),
            new Student(4, "Geo Ndung'u"));


    @GetMapping()
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ADMINTRAINEE')")
    public List<Student> getStudents(){
        return students;
    }

    @PostMapping("/register")
    @PreAuthorize("hasAuthority('student:write')")
    public void registerStudent(@RequestBody Student student){
        System.out.println(student);
    }


    @PutMapping("/update/{studentId}")
    @PreAuthorize("hasAuthority('student:write')")
    public void updateStudent(@PathVariable("studentId") Integer studentId, Student student){
        System.out.println(String.format("%s %s", studentId, student));
    }

    @DeleteMapping("/delete/{studentId}")
    @PreAuthorize("hasAuthority('student:write')")
    public void deleteStudent(@PathVariable("studentId") Integer studentId){
        System.out.println(studentId);
    }
}
