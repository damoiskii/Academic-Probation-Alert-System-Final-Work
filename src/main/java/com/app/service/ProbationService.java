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

import java.util.List;


public interface ProbationService {
    Probation addProbation(Probation probation) throws ProbationFoundException;

    List<Probation> findAllStudentsOnProbation();
    void deleteProbationList();
    void deleteProbation(String studentIDFound) throws ProbationNotFoundException;

    List<Probation> findAllProbationByKeyword(String keyword);
    List<Probation> filterProbationList(String field, String by);

    boolean studentIDFound(String id);
}
