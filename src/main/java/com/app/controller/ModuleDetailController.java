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

import com.app.exception.ModuleDetailNotFoundException;
import com.app.model.ModuleDetail;
import com.app.service.ModuleDetailServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.List;


@Controller
@RequiredArgsConstructor
public class ModuleDetailController {
    private final ModuleDetailServiceImpl moduleDetailService;

    // Add module detail
    public ModuleDetail addDetail(ModuleDetail detail){
        return moduleDetailService.addDetail(detail);
    }

    // Find all details across the board
    public List<ModuleDetail> findAllDetails() {
        return moduleDetailService.findAllModuleDetails();
    }

    public List<ModuleDetail> findAllModuleDetailsByYear(String year) {
        return moduleDetailService.findAllModuleDetailsByYear(year);
    }

    public List<ModuleDetail> findAllDetails(String semester, String year, String studentID) {
        return moduleDetailService.findAllModuleDetails(semester, year, studentID);
    }

    public List<ModuleDetail> passedModuleDetails(String studentID, String moduleCode) {
        return moduleDetailService.passedModuleDetails(studentID, moduleCode);
    }

    public List<ModuleDetail> failedModuleDetails(String studentID, String moduleCode) {
        return moduleDetailService.failedModuleDetails(studentID, moduleCode);
    }

    // Find by semester and year to set generated results
    public List<ModuleDetail> findAllModuleDetailsPerSemester(String semester, String year) {
        return moduleDetailService.findAllModuleDetailsPerSemester(semester, year);
    }

    // Update module detail
    public ModuleDetail updateDetail(ModuleDetail detail) throws ModuleDetailNotFoundException {
        return moduleDetailService.updateDetail(detail);
    }

    // Delete module detail
    public void deleteDetail(ModuleDetail detail) throws ModuleDetailNotFoundException {
        moduleDetailService.deleteModuleDetail(detail);
    }

    // Delete all details across the board
    public void deleteAllDetails(){
        moduleDetailService.deleteAllModuleDetails();
    }

    // Delete by Student
    public void deleteByStudentID(String studentID) {
        moduleDetailService.deleteByStudentID(studentID);
    }

    public void deleteAllByStudentID(String studentID) {
        moduleDetailService.deleteAllByStudentID(studentID);
    }

    // Delete by Module
    public void deleteByModuleCode(String code) {
        moduleDetailService.deleteByModuleCode(code);
    }

    public void deleteAllByModuleCode(String code) {
        moduleDetailService.deleteAllByModuleCode(code);
    }

    public List<ModuleDetail> findAllModuleDetailsByStudentID(String id) {
        return moduleDetailService.findAllModuleDetailsByStudentID(id);
    }
}
