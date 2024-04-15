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


import com.app.exception.ProbationFoundException;
import com.app.exception.ProbationNotFoundException;
import com.app.model.Probation;
import com.app.service.ProbationServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.List;


@Controller
@RequiredArgsConstructor
public class ProbationController {
    private final ProbationServiceImpl probationService;

    // Add probation
    public Probation addProbation(Probation probation) throws ProbationFoundException {
        return probationService.addProbation(probation);
    }

    // Get a list of existing probation
    public List<Probation> findAllStudentsOnProbation() {
        return probationService.findAllStudentsOnProbation();
    }

    // Delete the list of probation
    public void deleteProbationList() {
        probationService.deleteProbationList();
    }

    public void deleteProbation(String studentIDFound) throws ProbationNotFoundException {
        probationService.deleteProbation(studentIDFound);
    }

    // Find a list of probation by keyword
    public List<Probation> findAllProbationByKeyword(String keyword) {
        return probationService.findAllProbationByKeyword(keyword);
    }

    // Find a list of probation by the filter options
    public List<Probation> filterProbationList(String field, String by) {
        return probationService.filterProbationList(field, by);
    }
}
