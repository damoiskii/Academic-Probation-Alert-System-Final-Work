/*
----------------------------------------------------------------------------------
                    Module: Artificial Intelligence (CMP4011)
                    Lab Tutor: Mr. Howard James
                    Class Group: Tuesdays @6pm
                    Year: 2023/2024 Semester 2
                    Assessment: Programming Group Project
                    Group Members:
                        Damoi Myers - 1703236
----------------------------------------------------------------------------------
*/

package com.app.controller;


import com.app.exception.StudentFoundException;
import com.app.exception.StudentNotFoundException;
import com.app.model.Student;
import com.app.service.StudentServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.List;


@Controller
@RequiredArgsConstructor
public class StudentController {
    private final StudentServiceImpl studentService;

    // Add student
    public Student addStudent(Student student) throws StudentFoundException {
        return studentService.addStudent(student);
    }

    // Update student
    public Student updateStudent(Student student, String oldEmail) throws StudentFoundException, StudentNotFoundException {
        // If old email is not the same as the new email
        if(!student.getEmail().trim().equalsIgnoreCase(oldEmail)){
            if(studentService.emailFound(student.getEmail())){
                throw new StudentFoundException("You cannot use this email address! Please choose another...");
            }
        }

        return studentService.updateStudent(student);
    }

    // Get all students
    public List<Student> getAllStudents(){
        return studentService.findAllStudents();
    }

    // Delete all students
    public void deleteAllStudents(){
        studentService.deleteAllStudents();
    }

    // Search by keyword
    public List<Student> getAllStudentByKeyword(String keyword){
        return studentService.findAllStudentsByKeyword(keyword);
    }

    // Filter by keyword
    public List<Student> filterStudents(String field, String by){
        return studentService.filterStudents(field, by);
    }

    // Delete student
    public void delete(String id) throws StudentNotFoundException {
        studentService.deleteStudent(id);
    }

    public void delete(Student student) {
        studentService.deleteStudent(student);
    }

    // Lookup id number
    public boolean lookupID(String id){
        return studentService.idFound(id);
    }
}
