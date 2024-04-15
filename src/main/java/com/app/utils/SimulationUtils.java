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


import java.util.prefs.Preferences;


public class SimulationUtils {
    private final static Preferences PREFERENCES = Preferences.userRoot();


    // Get the current semester
    public static String getSemester(){
        String currentSemester = PREFERENCES.get("currentSemester", null);

        if(currentSemester == null){
            currentSemester = "Semester 1";
            PREFERENCES.put("currentSemester", "Semester 1");
        }

        return currentSemester;
    }

    // Get the current academic year
    public static String getAcademicYear(){
        String currentAcademicYear = PREFERENCES.get("currentAcademicYear", null);

        if(currentAcademicYear == null){
            currentAcademicYear = BaseUtils.currentYearInString();

            PREFERENCES.put("startingYearTracker", currentAcademicYear);
            PREFERENCES.put("currentAcademicYear", currentAcademicYear);
        }

        return currentAcademicYear;
    }

    // Get the very first academic year
    public static String getStartingYear(){
        String startingYearTracker = PREFERENCES.get("startingYearTracker", null);

        if(startingYearTracker == null){
            startingYearTracker = BaseUtils.currentYearInString();

            PREFERENCES.put("startingYearTracker", startingYearTracker);
        }

        return startingYearTracker;
    }

    // Inform the user that no simulation was done prior to wanting to view gpa details for a student
    public static boolean noSimulationTakenPlaceYet(){
        String currentAcademicYear = PREFERENCES.get("currentAcademicYear", null);
        String currentSemester = PREFERENCES.get("currentSemester", null);
        String freshYearTracker = getFormattedAcademicYear();

        return (currentAcademicYear != null && freshYearTracker.startsWith(currentAcademicYear) && (currentSemester == null || currentSemester.equalsIgnoreCase("semester 1")));
    }

    public static String getFormattedAcademicYear(){
        String currentAcademicYear = getAcademicYear();

        try{
            int year = Integer.parseInt(currentAcademicYear);

            // Adding the formatted string with the current year plus one; eg., 2023-2024
            return year + "-" + (year + 1);
        }
        catch (NumberFormatException e){
            return currentAcademicYear;
        }
    }

    // Get the next school year.
    public static String getNextAcademicYear(){
        String currentAcademicYear = getAcademicYear();

        try{
            int year = Integer.parseInt(currentAcademicYear);

            // Adding the formatted string with the current year plus one; eg., 2023 -> 2024
            return String.valueOf(year + 1);
        }
        catch (NumberFormatException e){
            return currentAcademicYear;
        }
    }

    // Progress to next semester
    public static void semesterProgression(){
        String currentSemester = PREFERENCES.get("currentSemester", null);

        if(currentSemester == null){
            PREFERENCES.put("currentSemester", "Semester 1");
        }
        else if(currentSemester.equalsIgnoreCase("Semester 1")){
            PREFERENCES.put("currentSemester", "Semester 2");
        }
        else if(currentSemester.equalsIgnoreCase("Semester 2")){
            PREFERENCES.put("currentSemester", "Semester 3");
        }
        else if(currentSemester.equalsIgnoreCase("Semester 3")){
            PREFERENCES.put("currentSemester", "Semester 1");

            // Move to the next school year
            PREFERENCES.put("currentAcademicYear", getNextAcademicYear());
        }
    }

    // Calculate the previous school year from the present
    public static String[] getAllSchoolYears() {
        StringBuilder sb = new StringBuilder();

        String currentAcademicYear = getAcademicYear();
        String startingYearTracker = getStartingYear();

        try{
            int cy = Integer.parseInt(currentAcademicYear);
            int syt = Integer.parseInt(startingYearTracker);

            // If the start year tracker and the current year is the same
            if(cy == syt) return new String[]{(cy + "-" + (cy + 1))};

            // Used to keep track of the years being incremented. Break loop when starting year tracker is equal to the current year.
            do{
                sb.append(syt).append("-").append((syt + 1));
                sb.append(",");

                if(cy == syt) break;
                else syt++; // Increment the start year tracker until it equals the current year
            }while(true);

            return sb.toString().split(",");
        }
        catch (NumberFormatException e){
            return new String[]{currentAcademicYear};
        }
    }

    // Remove all saved preferences for the simulation
    public static void resetSimulation(){
        PREFERENCES.remove("currentSemester");
        PREFERENCES.remove("currentAcademicYear");
        PREFERENCES.remove("startingYearTracker");
    }
}
