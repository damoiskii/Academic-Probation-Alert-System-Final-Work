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

package com.app.view.listeners;


import com.app.model.Probation;

import java.util.List;


public interface ProbationTabListener {
    List<Probation> refreshTable();
    List<Probation> search(String keyword);
    List<Probation> filter(String field, String by);

    void deleteProbationList();
    void autoChecker();
}
