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

package com.app.report;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {
    private final DataConverter dataConverter;

    @Override
    public boolean generateReportFromJTable(Double gpa, String year) {
        dataConverter.setGpa(gpa);
        dataConverter.setYear(year);

        return dataConverter.generateTable();
    }
}
