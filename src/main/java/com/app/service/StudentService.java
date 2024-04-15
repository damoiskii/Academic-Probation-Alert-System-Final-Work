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

package com.app.service;

import com.app.exception.StudentFoundException;
import com.app.exception.StudentNotFoundException;
import com.app.model.Student;

import java.util.List;


public interface StudentService {
    Student addStudent(Student student) throws StudentFoundException;
    Student updateStudent(Student student) throws StudentNotFoundException;

    List<Student> findAllStudents();
    void deleteAllStudents();
    void deleteStudent(String id) throws StudentNotFoundException;
    void deleteStudent(Student student);

    List<Student> findAllStudentsByKeyword(String keyword);
    List<Student> filterStudents(String field, String by);

    boolean emailFound(String email);
    boolean idFound(String id);
}
