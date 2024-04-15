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

import com.app.model.Probation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProbationRepository extends JpaRepository<Probation, Long> {
    Probation findByStudentIDEqualsIgnoreCase(String id);

    List<Probation> findAllByNameContainingIgnoreCaseOrStudentIDContainingIgnoreCaseOrderByGpaDescNameAsc(String name, String id);
    List<Probation> findAllBySchoolEqualsIgnoreCaseOrderByGpaDescNameAsc(String school);
    List<Probation> findAllByProgrammeEqualsIgnoreCaseOrderByGpaDescNameAsc(String programme);
}
