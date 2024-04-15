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
import com.app.repository.ModuleDetailRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Transactional
public class ModuleDetailServiceImpl implements ModuleDetailService{
    private final ModuleDetailRepository moduleDetailRepository;

    @Override
    public ModuleDetail addDetail(ModuleDetail detail) {
        return moduleDetailRepository.saveAndFlush(detail);
    }

    @Override
    public ModuleDetail updateDetail(ModuleDetail detail) throws ModuleDetailNotFoundException {
        Optional<ModuleDetail> detailOptional = moduleDetailRepository.findById(detail.getId());

        if(detailOptional.isEmpty()) throw new ModuleDetailNotFoundException("Module detail not found!");

        return moduleDetailRepository.saveAndFlush(detail);
    }

    @Override
    public List<ModuleDetail> findAllModuleDetails() {
        return moduleDetailRepository.findAll();
    }

    @Override
    public List<ModuleDetail> findAllModuleDetailsByYear(String year) {
        return moduleDetailRepository.findAllByYearContainingIgnoreCase(year);
    }

    @Override
    public List<ModuleDetail> findAllModuleDetailsByStudentID(String studentID) {
        return moduleDetailRepository.findAllByStudent_IdOrderByYearAsc(studentID);
    }

    @Override
    public List<ModuleDetail> findAllModuleDetails(String semester, String year, String studentID) {
        return moduleDetailRepository.findAllBySemesterContainingIgnoreCaseAndYearContainingIgnoreCaseAndStudent_Id(semester, year, studentID);
    }

    @Override
    public List<ModuleDetail> findAllModuleDetailsPerSemester(String semester, String year) {
        return moduleDetailRepository.findAllBySemesterContainingIgnoreCaseAndYearContainingIgnoreCase(semester, year);
    }

    @Override
    public List<ModuleDetail> passedModuleDetails(String studentID, String moduleCode) {
        return moduleDetailRepository.findAllByStudent_IdAndModule_CodeAndCompleteIsTrueOrderBySemesterAscYearAsc(studentID, moduleCode);
    }

    @Override
    public List<ModuleDetail> failedModuleDetails(String studentID, String moduleCode) {
        return moduleDetailRepository.findAllByStudent_IdAndModule_CodeAndCompleteIsFalseOrderBySemesterAscYearAsc(studentID, moduleCode);
    }

    @Override
    public void deleteAllModuleDetails() {
        moduleDetailRepository.deleteAll();
    }

    @Override
    public void deleteModuleDetail(Long id) throws ModuleDetailNotFoundException {
        Optional<ModuleDetail> detailOptional = moduleDetailRepository.findById(id);

        if(detailOptional.isEmpty()) throw new ModuleDetailNotFoundException("Module detail not found!");

        moduleDetailRepository.delete(detailOptional.get());
    }

    @Override
    public void deleteModuleDetail(ModuleDetail detail) throws ModuleDetailNotFoundException{
        Optional<ModuleDetail> detailOptional = moduleDetailRepository.findById(detail.getId());

        if(detailOptional.isEmpty()) throw new ModuleDetailNotFoundException("Module detail not found!");

        moduleDetailRepository.delete(detail);
    }

    @Override
    public void deleteByStudentID(String student_id) {
        moduleDetailRepository.deleteByStudent_Id(student_id);
    }

    @Override
    public void deleteAllByStudentID(String student_id) {
        moduleDetailRepository.deleteAllByStudent_Id(student_id);
    }

    @Override
    public void deleteByModuleCode(String code) {
        moduleDetailRepository.deleteByModule_Code(code);
    }

    @Override
    public void deleteAllByModuleCode(String code) {
        moduleDetailRepository.deleteAllByModule_Code(code);
    }
}
