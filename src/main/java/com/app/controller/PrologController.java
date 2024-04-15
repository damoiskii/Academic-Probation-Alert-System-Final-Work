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

import com.app.model.ModuleDetail;
import com.app.prolog.PrologEngine;
import com.app.utils.GPAResultsUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequiredArgsConstructor
public class PrologController {
    private final PrologEngine prologEngine;


    // Set new GPA value in prolog file
    public boolean setNewGPA(double gpa){
        return prologEngine.setNewGPA(gpa);
    }

    // Fetching default GPA from prolog file
    public String getDefaultGPA(){
        return prologEngine.getDefaultGPA();
    }

    // Calculate points earned
    public String calculatePointsEarned(int credits, double score){
        return prologEngine.getPointsEarned(credits, score);
    }

    // Get the total number credits
    public int totalCredits(List<?> creditsList) {
        return prologEngine.totalCredits(creditsList);
    }

    // Get the grade letter
    public String getGradeLetter(double points) {
        return prologEngine.getGradeLetter(points);
    }

    // Get the points earned for a module
    public String getPointsEarned(int credits, double points) {
        return prologEngine.getPointsEarned(credits, points);
    }

    // Get the total points earned for a semester
    public double totalPointsEarned(List<?> pointsList) {
        return prologEngine.totalPointsEarned(pointsList);
    }

    // Get the total from all elements in random list of numbers
    public double getSum(List<Double> listD, List<Integer> listI) {
        return prologEngine.getSum(listD, listI);
    }

    // Get the total cumulative GPA
    public double getCumulativeGPA(double totalGradePointsEarned, double totalCredits) {
        return prologEngine.getCumulativeGPA(totalGradePointsEarned, totalCredits);
    }

    // Get faculty administrator
    public String[] getFacultyAdministrator(String school) {
        return prologEngine.getFacultyAdministrator(school);
    }

    // Get programme director
    public String[] getProgrammeDirector(String school, String programme) {
        return prologEngine.getProgrammeDirector(school, programme);
    }

    // Get advisor
    public String[] getAdvisor(String school, String firstTwoCharsFromStudentID) {
        return prologEngine.getAdvisor(school, firstTwoCharsFromStudentID);
    }

    // This method is responsible for calculating all module details info
    public Map<String, List<String>> calculate(List<ModuleDetail> details, String semester){
        // Data structure to capture the data format that the table will receive to display
        Map<String, List<String>> data = new HashMap<>();

        // Loop through the results returned from the database
        for(ModuleDetail detail: details){
            // Capture Modules
            List<String> modules = data.getOrDefault("modules", null);
            if(modules == null) {
                modules = new ArrayList<>();
                modules.add("Modules");
            }

            String redoIndicator = (detail.getRedo() ? " (Redo)" : "");
            modules.add(detail.getModule() != null ? (detail.getModule().getName() + redoIndicator) : "-");
            data.put("modules", modules);


            // Capture Credits
            List<String> credits = data.getOrDefault("credits", null);
            if(credits == null) {
                credits = new ArrayList<>();
                credits.add("Credits");
            }
            String c = detail.getModule() != null ? String.valueOf(detail.getModule().getCredits()) : "-";
            credits.add(c);
            data.put("credits", credits);


            // Capture Grades
            List<String> grades = data.getOrDefault("grades", null);
            if(grades == null) {
                grades = new ArrayList<>();
                grades.add("Grades");
                data.put("grades", grades);
            }
            grades.add(prologEngine.getGradeLetter(detail.getGradePoints()));
            data.put("grades", grades);


            // Capture Grades Points
            List<String> gradePoints = data.getOrDefault("points", null);
            if(gradePoints == null) {
                gradePoints = new ArrayList<>();
                gradePoints.add("Grade Points");
            }
            gradePoints.add(String.format("%.2f", detail.getGradePoints()));
            data.put("points", gradePoints);


            // Capture Grades Points
            List<String> gradePointsEarned = data.getOrDefault("earned", null);
            if(gradePointsEarned == null) {
                gradePointsEarned = new ArrayList<>();
                gradePointsEarned.add("Grade Points Earned");
            }

            // Using the credits and grade points to send to prolog to calculate the points earned
            String gpe = "-";

            // If a module is attached
            if(detail.getModule() != null) gpe = prologEngine.getPointsEarned(detail.getModule().getCredits(), detail.getGradePoints());

            gradePointsEarned.add(gpe);
            data.put("earned", gradePointsEarned);
        }

        // If data generated then change the gpa score
        if(data.size() > 0) {
            int totalCredits = 0;
            double totalPointsEarned = 0;

            // Using Prolog to do GPA calculations here [Credits, Grades, Grade Points, Grade Points Earned]
            List<String> modules = data.getOrDefault("modules", null);

            for(int i = 0; i < modules.size(); i++){
                // Capture Credits
                final List<String> CREDITS = data.getOrDefault("credits", null);
                if(CREDITS != null && totalCredits == 0) {
                    // Calculating total credits using prolog
                    totalCredits = prologEngine.totalCredits(CREDITS);
                }

                // Capture Grade Point Earned
                final List<String> EARNED = data.getOrDefault("earned", null);
                if(EARNED != null && totalPointsEarned == 0) {
                    // Calculating total credits using prolog
                    totalPointsEarned = prologEngine.totalPointsEarned(EARNED);
                }
            }

            // Save the results from prolog for ease of access
            if(semester.equalsIgnoreCase("semester 1") || semester.equalsIgnoreCase("semester one")){
                GPAResultsUtils.s1GPA = prologEngine.getCumulativeGPA(totalPointsEarned, totalCredits);
            }
            else if(semester.equalsIgnoreCase("semester 2") || semester.equalsIgnoreCase("semester two")){
                GPAResultsUtils.s2GPA = prologEngine.getCumulativeGPA(totalPointsEarned, totalCredits);
            }
            else if(semester.equalsIgnoreCase("semester 3") || semester.equalsIgnoreCase("semester three")){
                GPAResultsUtils.s3GPA = prologEngine.getCumulativeGPA(totalPointsEarned, totalCredits);
            }

            // Recording the total credits and total points earned
            GPAResultsUtils.addToTotalCredits(totalCredits);
            GPAResultsUtils.addToTotalGradePointsEarned(totalPointsEarned);

            // Retrieve both lists to do the cumulative calculation by prolog
            GPAResultsUtils.cumulativeTotalCredits = prologEngine.getSum(null, GPAResultsUtils.getTotalCreditsList());
            GPAResultsUtils.cumulativeTotalPointsEarned = prologEngine.getSum(GPAResultsUtils.getTotalGradePointsEarnedList(), null);
            GPAResultsUtils.cumulativeGPATotalForSchoolYear = prologEngine.getCumulativeGPA(GPAResultsUtils.cumulativeTotalPointsEarned, GPAResultsUtils.cumulativeTotalCredits);

            // Applying the necessary values to relative variables that will be used to get values elsewhere in the program
            GPAResultsUtils.reflectChanges();

            // If cumulative gpa is less than the default gpa score then add to a special list for probation...

        }

        return data;
    }
}
