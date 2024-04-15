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

package com.app.prolog;

import com.app.utils.FileUtils;
import lombok.RequiredArgsConstructor;
import org.projog.api.Projog;
import org.projog.api.QueryResult;
import org.projog.core.ProjogException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;


@Component
@RequiredArgsConstructor
public class PrologEngine {
    private final Logger LOGGER = LoggerFactory.getLogger(PrologEngine.class);

    private final PrologHelper prologHelper;
    private Projog prolog;

    private final String defaultGPALine = "default_gpa";
    private boolean defaultGPALineFound = false;



    // Set new GPA value in prolog file
    public boolean setNewGPA(double gpa){
        defaultGPALineFound = false;

        // Files to be used
        File prologFile = new File("src/main/resources/prolog/" + FileUtils.PrologFilename);
        File tempFile = new File("src/main/resources/prolog/temp.pl");

        // Create files if it does not exist
        FileUtils.createFile(prologFile.getPath());
        FileUtils.createFile(tempFile.getPath());

        // Get file path
        Path path = Paths.get(prologFile.getPath());

        // Retrieve all lines in the prolog file
        try (Stream<String> lines = Files.lines(path)) {
            // Loop over each lines in the file
            lines.forEachOrdered(line -> {
                // If the line contains default_gpa then update this line with the new gpa value in the temp file
                if(line.toLowerCase().contains(defaultGPALine.toLowerCase())){
                    // Write line to replace the updates in the new temp file...
                    FileUtils.writeToFile(tempFile, "\n\n% Default GPA value that will be used throughout the system");
                    FileUtils.writeToFile(tempFile, defaultGPALine + "(" + String.format("%.2f", gpa) + ").\n\n\n");

                    // FileUtils.writeToFile(tempFile, defaultGPALine + "(" + String.format("%.2f", gpa) + ").");

                    defaultGPALineFound = true;
                }
                else if(!line.toLowerCase().contains("% Default GPA value that will be used throughout the system".toLowerCase())){
                    // Copy line to temp file here...
                    FileUtils.writeToFile(tempFile, line);
                }
                /*else{
                    // Copy line to temp file here...
                    FileUtils.writeToFile(tempFile, line);
                }*/
            });

            // If default line found then update was made. Therefore, rename the temp file to knowledge_base.pl
            if(defaultGPALineFound){
                FileUtils.renameTo(tempFile, FileUtils.PrologFilename);
            }
            else{
                // Write default gpa to the temp file because it was not found
                FileUtils.writeToFile(tempFile, "\n\n% Default GPA value that will be used throughout the system");
                FileUtils.writeToFile(tempFile, defaultGPALine + "(" + String.format("%.2f", gpa) + ").");
                FileUtils.renameTo(tempFile, FileUtils.PrologFilename);

                // FileUtils.deleteFile(tempFile);
                // LOGGER.error("Setting Default GPA Error: Line not found!");
            }
        }
        catch (IOException e) {
            LOGGER.error("Setting Default GPA Error [IOException]: " + e.getMessage());
            return false;
        }
        catch (Exception e) {
            LOGGER.error("Setting Default GPA Error [Other Exception]: " + e.getMessage());
            return false;
        }

        // Allow prolog to consult with updated file to access the latest information
        if(defaultGPALineFound) reconsult();

        return true;
    }


    // Allow prolog to reconsult with the updated version of the file [the latest information]
    private void reconsult(){
        try{
            // Clear the knowledge base system by instantiating a new prolog object
            prolog = prologHelper.recreate();
        }
        catch (ProjogException e) {
            LOGGER.error("Error re-consulting file: " + e.getMessage());
        }
    }


    // Fetching default GPA from prolog file
    public String getDefaultGPA(){
        // If the prolog object is not instantiated as yet, instantiate it...
        if(prolog == null) prolog = prologHelper.create();

        // Execute query for the default gpa
        QueryResult query = prolog.executeQuery(defaultGPALine + "(X).");

        if(query.next()) return String.valueOf(query.getTerm("X"));

        return null;
    }


    // Get the total number credits
    public int totalCredits(List<?> creditsList) {
        if(prolog == null) prolog = prologHelper.create();

        List<?> list = new ArrayList<>(creditsList);

        // Removing this item from the list that was added from the parent function (calling function)...
        list.remove("Credits");

        // Pass list to Prolog predicate
        QueryResult query = prolog.executeQuery("sum_list(" + list + ", Total).");

        try{
            if(query.next()) return Integer.parseInt(query.getTerm("Total").toString());
        }
        catch (Exception ignore){}

        return 0;
    }


    // Get the grade letter
    public String getGradeLetter(double points) {
        if(prolog == null) prolog = prologHelper.create();

        // Run query
        QueryResult query = prolog.executeQuery("grade_letter(Grade, " + points + ").");

        if(query.next()) return query.getTerm("Grade").toString();

        return "-";
    }


    // Get the points earned for a module
    public String getPointsEarned(int credits, double points) {
        if(prolog == null) prolog = prologHelper.create();

        // Run query
        QueryResult query = prolog.executeQuery("points_earned(" + credits + ", " + points + ", PointsEarned).");

        if(query.next()) {
            double PointsEarned = Double.parseDouble(query.getTerm("PointsEarned").toString());

            return String.format("%.2f", PointsEarned);
        }

        return "-";
    }


    // Get the total points earned for a semester
    public double totalPointsEarned(List<?> pointsList) {
        if(prolog == null) prolog = prologHelper.create();

        List<?> list = new ArrayList<>(pointsList);

        // Removing this item from the list that was added from the parent function (calling function)...
        list.remove("Grade Points Earned");

        // Pass list to Prolog predicate
        QueryResult query = prolog.executeQuery("sum_list(" + list + ", Total).");

        try{
            if(query.next()) return Double.parseDouble(query.getTerm("Total").toString());
        }
        catch (Exception ignore){}

        return 0;
    }

    // Get the total from all elements in random list of numbers
    public double getSum(List<Double> listD, List<Integer> listI) {
        if(prolog == null) prolog = prologHelper.create();

        List<?> list = (listD != null ? listD: listI);

        // Pass list to Prolog predicate
        QueryResult query = prolog.executeQuery("sum_list(" + list + ", Total).");

        try{
            if(query.next()) return Double.parseDouble(query.getTerm("Total").toString());
        }
        catch (Exception ignore){
            if(query.next()) return Integer.parseInt(query.getTerm("Total").toString());
        }

        return 0;
    }


    // Get the total cumulative GPA
    public double getCumulativeGPA(double totalGradePointsEarned, double totalCredits) {
        if(prolog == null) prolog = prologHelper.create();

        // Pass list to Prolog predicate
        QueryResult query = prolog.executeQuery("cumulative_gpa(" + totalGradePointsEarned + ", " + totalCredits + ", GPA).");

        try{
            if(query.next()) return Double.parseDouble(query.getTerm("GPA").toString());
        }
        catch (Exception ignore){}

        return 0;
    }


    // Get faculty administrator
    public String[] getFacultyAdministrator(String school) {
        if(prolog == null) prolog = prologHelper.create();

        // Pass school to Prolog predicate
        QueryResult query = prolog.executeQuery("faculty_admin('" + school + "', Name, Email).");

        try{
            if(query.next()) return new String[]{query.getTerm("Name").toString(), query.getTerm("Email").toString()};
        }
        catch (Exception ignore){}

        return new String[]{"Default Admin", "default-admin@utech.edu.jm"};
    }

    // Get programme director
    public String[] getProgrammeDirector(String school, String programme) {
        if(prolog == null) prolog = prologHelper.create();

        // Pass school & programme to Prolog predicate
        QueryResult query = prolog.executeQuery("programme_director('" + school + "', '" + programme + "', Name, Email).");

        try{
            if(query.next()) return new String[]{query.getTerm("Name").toString(), query.getTerm("Email").toString()};
        }
        catch (Exception ignore){}

        return new String[]{"Default PD", "default-pd@utech.edu.jm"};
    }

    // Get advisor
    public String[] getAdvisor(String school, String firstTwoCharsFromStudentID) {
        if(prolog == null) prolog = prologHelper.create();

        // Pass school & first two characters of student's ID# to Prolog predicate
        QueryResult query = prolog.executeQuery("advisor('" + school + "', '" + firstTwoCharsFromStudentID + "', Name, Email).");

        try{
            if(query.next()) return new String[]{query.getTerm("Name").toString(), query.getTerm("Email").toString()};
        }
        catch (Exception ignore){}

        return new String[]{"Default Advisor", "default-advisor@utech.edu.jm"};
    }
}
