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
import com.app.repository.StudentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Transactional
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;

    @Override
    public Student addStudent(Student student) throws StudentFoundException {
        // Check if the id exist
        if(idFound(student.getId())) throw new StudentFoundException("ID# address already assigned to a student!");

        // Check if email already
        if(emailFound(student.getEmail())) throw new StudentFoundException("Email address already assigned to a student!");

        return studentRepository.saveAndFlush(student);
    }

    @Override
    public Student updateStudent(Student student) throws StudentNotFoundException {
        Optional<Student> studentOptional = studentRepository.findById(student.getId());

        if(studentOptional.isEmpty()) throw new StudentNotFoundException("Student with the id " + student.getId() + " not found!");

        return studentRepository.saveAndFlush(student);
    }

    @Override
    public List<Student> findAllStudents() {
        return studentRepository.findAll(Sort.by("name").ascending().and(Sort.by("email").ascending()));
    }

    @Override
    public void deleteAllStudents() {
        studentRepository.deleteAll();
    }

    @Override
    public void deleteStudent(String id) throws StudentNotFoundException {
        Optional<Student> studentOptional = studentRepository.findById(id);

        if(studentOptional.isEmpty()) throw new StudentNotFoundException("Student with the id " + id + " not found!");

        studentRepository.deleteById(id);
    }

    @Override
    public void deleteStudent(Student student) {
        studentRepository.delete(student);
    }

    @Override
    public List<Student> findAllStudentsByKeyword(String keyword) {
        return studentRepository.findAllByNameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrIdContainingIgnoreCaseOrderByNameAscEmailAsc(keyword, keyword, keyword);
    }

    @Override
    public List<Student> filterStudents(String field, String by) {
        List<Student> students = new ArrayList<>();

        if(field.equalsIgnoreCase("school")){
            students = studentRepository.findAllBySchoolEqualsIgnoreCaseOrderByNameAscEmailAsc(by);
        }
        else if(field.equalsIgnoreCase("programme")){
            students = studentRepository.findAllByProgrammeEqualsIgnoreCaseOrderByNameAscEmailAsc(by);
        }

        return students;
    }

    @Override
    public boolean emailFound(String email) {
        Student s = studentRepository.findByEmailEqualsIgnoreCase(email);

        return s != null;
    }

    @Override
    public boolean idFound(String id) {
        Optional<Student> s = studentRepository.findById(id);

        return s.isPresent();
    }
}
