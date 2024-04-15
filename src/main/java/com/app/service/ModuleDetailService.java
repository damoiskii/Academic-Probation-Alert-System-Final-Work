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

import com.app.exception.ModuleDetailNotFoundException;
import com.app.model.ModuleDetail;

import java.util.List;


public interface ModuleDetailService {
    ModuleDetail addDetail(ModuleDetail detail);
    ModuleDetail updateDetail(ModuleDetail detail) throws ModuleDetailNotFoundException;

    List<ModuleDetail> findAllModuleDetails();
    List<ModuleDetail> findAllModuleDetailsByYear(String year);
    List<ModuleDetail> findAllModuleDetailsByStudentID(String studentID);
    List<ModuleDetail> findAllModuleDetails(String semester, String year, String studentID);
    List<ModuleDetail> findAllModuleDetailsPerSemester(String semester, String year);
    List<ModuleDetail> passedModuleDetails(String studentID, String moduleCode);
    List<ModuleDetail> failedModuleDetails(String studentID, String moduleCode);

    void deleteAllModuleDetails();
    void deleteModuleDetail(Long id) throws ModuleDetailNotFoundException;
    void deleteModuleDetail(ModuleDetail detail) throws ModuleDetailNotFoundException;

    // Delete by Student
    void deleteByStudentID(String student_id);
    void deleteAllByStudentID(String student_id);

    // Delete by Module
    void deleteByModuleCode(String code);
    void deleteAllByModuleCode(String code);
}
