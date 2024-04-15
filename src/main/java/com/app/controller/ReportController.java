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


import com.app.report.ReportServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;


@Controller
@RequiredArgsConstructor
public class ReportController {
    private final ReportServiceImpl reportService;

    // Generate the report from converting a JTable or Thymeleaf html
    public boolean generate(Double gpa, String year, String source){
        if(source.equalsIgnoreCase("table")){
            return reportService.generateReportFromJTable(gpa, year);
        }

        return false;
    }
}
