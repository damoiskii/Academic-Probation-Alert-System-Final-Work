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

package com.app.utils;


import java.util.ArrayList;
import java.util.List;

// This class is merely being used right now just to test how to transfer the gpa calculations to the tables...
public class GPAResultsUtils {
    // Semester One Info
    public static int s1totalCredit = 0;
    public static double s1totalPointsEarned = 0;
    public static double s1GPA = 0;

    // Semester Two Info
    public static int s2totalCredit = 0;
    public static double s2totalPointsEarned = 0;
    public static double s2GPA = 0;

    // Semester Three Info
    public static int s3totalCredit = 0;
    public static double s3totalPointsEarned = 0;
    public static double s3GPA = 0;

    // Cumulative Totals
    public static double cumulativeGPATotalForSchoolYear = 0;
    public static double cumulativeGPATotalForAllYears = 0;
    public static double cumulativeTotalCredits = 0;
    public static double cumulativeTotalPointsEarned = 0;

    // To help with the passing of data to be calculated by prolog
    private static List<Integer> totalCreditsList = new ArrayList<>();
    private static List<Double> totalGradePointsEarnedList = new ArrayList<>();


    // Update the changes to reflect in the GUI
    public static void reflectChanges(){
        // Total Credits Per Semester
        try{
            s1totalCredit = totalCreditsList.get(0); // semester 1
            s2totalCredit = totalCreditsList.get(1); // semester 2
            s3totalCredit = totalCreditsList.get(2); // semester 3
        }
        catch (Exception ignore){}

        // Total Grade Points Earned Per Semester
        try{
            s1totalPointsEarned = totalGradePointsEarnedList.get(0); // semester 1
            s2totalPointsEarned = totalGradePointsEarnedList.get(1); // semester 2
            s3totalPointsEarned = totalGradePointsEarnedList.get(2); // semester 3
        }
        catch (Exception ignore){}
    }

    // Reset all values to 0
    public static void clear(){
        s1totalCredit = 0;
        s1totalPointsEarned = 0;
        s1GPA = 0;

        // Semester Two Info
        s2totalCredit = 0;
        s2totalPointsEarned = 0;
        s2GPA = 0;

        // Semester Three Info
        s3totalCredit = 0;
        s3totalPointsEarned = 0;
        s3GPA = 0;

        // Cumulative Totals
        cumulativeGPATotalForSchoolYear = 0;
        cumulativeGPATotalForAllYears = 0;
        cumulativeTotalCredits = 0;
        cumulativeTotalPointsEarned = 0;

        totalCreditsList = new ArrayList<>();
        totalGradePointsEarnedList = new ArrayList<>();
    }

    // Adding to lists
    public static void addToTotalCredits(int totalCredits){
        totalCreditsList.add(totalCredits);
    }

    public static void addToTotalGradePointsEarned(double totalPointsEarned){
        totalGradePointsEarnedList.add(totalPointsEarned);
    }

    // Retrieve lists to do prolog calculation for the cumulative GPA
    public static List<Integer> getTotalCreditsList() {
        return totalCreditsList;
    }

    public static List<Double> getTotalGradePointsEarnedList() {
        return totalGradePointsEarnedList;
    }
}
