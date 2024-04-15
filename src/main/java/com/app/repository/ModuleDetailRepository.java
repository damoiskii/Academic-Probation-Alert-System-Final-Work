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

import com.app.model.ModuleDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ModuleDetailRepository extends JpaRepository<ModuleDetail, Long> {
    List<ModuleDetail> findAllBySemesterContainingIgnoreCaseAndYearContainingIgnoreCaseAndStudent_Id(String semester, String year, String studentID);
    List<ModuleDetail> findAllBySemesterContainingIgnoreCaseAndYearContainingIgnoreCase(String semester, String year);
    List<ModuleDetail> findAllByYearContainingIgnoreCase(String year);

    // Find all done by a student passed/failed
    List<ModuleDetail> findAllByStudent_IdAndModule_CodeAndCompleteIsTrueOrderBySemesterAscYearAsc(String studentID, String moduleCode);
    List<ModuleDetail> findAllByStudent_IdAndModule_CodeAndCompleteIsFalseOrderBySemesterAscYearAsc(String studentID, String moduleCode);
    List<ModuleDetail> findAllByStudent_IdOrderByYearAsc(String studentID);


    // Delete by Student
    void deleteByStudent_Id(String studentID);
    void deleteAllByStudent_Id(String studentID);

    // Delete by Module
    void deleteByModule_Code(String code);
    void deleteAllByModule_Code(String code);
}
