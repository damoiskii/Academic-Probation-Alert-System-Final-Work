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

import com.app.exception.ProbationFoundException;
import com.app.exception.ProbationNotFoundException;
import com.app.model.Probation;
import com.app.repository.ProbationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ProbationServiceImpl implements ProbationService {
    private final ProbationRepository probationRepository;

    @Override
    public Probation addProbation(Probation probation) throws ProbationFoundException {
        if(studentIDFound(probation.getStudentID())) throw new ProbationFoundException("Student already on probation!");

        return probationRepository.saveAndFlush(probation);
    }

    @Override
    public List<Probation> findAllStudentsOnProbation() {
        Sort sortBy = Sort.by("gpa").descending().and(Sort.by("name").ascending());

        return probationRepository.findAll(sortBy);
    }

    @Override
    public void deleteProbationList() {
        probationRepository.deleteAll();
    }

    @Override
    public void deleteProbation(String studentIDFound) throws ProbationNotFoundException{
        Probation probation = probationRepository.findByStudentIDEqualsIgnoreCase(studentIDFound);

        if(probation == null) throw new ProbationNotFoundException("Student not found on the probation list!");

        probationRepository.delete(probation);
    }

    @Override
    public List<Probation> findAllProbationByKeyword(String keyword) {
        return probationRepository.findAllByNameContainingIgnoreCaseOrStudentIDContainingIgnoreCaseOrderByGpaDescNameAsc(keyword, keyword);
    }

    @Override
    public List<Probation> filterProbationList(String field, String by) {
        List<Probation> list = new ArrayList<>();

        if(field.equalsIgnoreCase("school")){
            list = probationRepository.findAllBySchoolEqualsIgnoreCaseOrderByGpaDescNameAsc(by);
        }
        else if(field.equalsIgnoreCase("programme")){
            list = probationRepository.findAllByProgrammeEqualsIgnoreCaseOrderByGpaDescNameAsc(by);
        }

        return list;
    }

    @Override
    public boolean studentIDFound(String id) {
        Probation probation = probationRepository.findByStudentIDEqualsIgnoreCase(id);

        return probation != null;
    }
}
