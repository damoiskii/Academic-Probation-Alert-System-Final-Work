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

package com.app.repository;

import com.app.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface StudentRepository extends JpaRepository<Student, String> {
    Student findByEmailEqualsIgnoreCase(String email);

    List<Student> findAllByNameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrIdContainingIgnoreCaseOrderByNameAscEmailAsc(String name, String email, String id);
    List<Student> findAllBySchoolEqualsIgnoreCaseOrderByNameAscEmailAsc(String school);
    List<Student> findAllByProgrammeEqualsIgnoreCaseOrderByNameAscEmailAsc(String programme);

}
