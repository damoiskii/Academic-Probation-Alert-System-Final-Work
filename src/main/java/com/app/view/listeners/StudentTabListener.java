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

package com.app.view.listeners;


import com.app.exception.StudentNotFoundException;
import com.app.model.Student;

import java.util.List;
import java.util.Map;


public interface StudentTabListener {
    boolean addStudent(Student student);
    boolean updateStudent(Student student, String oldEmail);

    List<Student> generateStudents(int amount);
    List<Student> refreshTable();
    List<Student> search(String keyword);

    void deleteStudents();
    void deleteStudent(String studentID) throws StudentNotFoundException;
    void deleteStudent(Student student);

    boolean isIDFound(String studentID);

    List<Student> filter(String field, String by);

    Map<String, List<String>> findAllModules(String studentID, String semester, String schoolYear);
}
