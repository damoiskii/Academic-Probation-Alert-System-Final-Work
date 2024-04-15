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


import org.apache.commons.text.WordUtils;
import org.apache.commons.validator.routines.EmailValidator;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Random;

public class BaseUtils {
    public static String APP_NAME = "Academic Probation Application";
    public static Date PROBATION_LIST_UPDATED;

    // Custom Sleep function
    public static void sleep(int seconds){
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException ignore) {}
    }

    // Generating a random student ID number
    public static String randomlyPickStudentID(){
        Random random = new Random();

        // Grab the year the id should start with...
        int startWith = random.nextInt(10, 24);

        // Grab the remainder for the id
        int id = random.nextInt(10000, 99999);

        // Building the string format of the id to return
        return startWith + "" + id;
    }

    public static String randomlyPickIDForStudentForm(){
        Random random = new Random();

        // Grab the year the id should start with...
        String year = currentYearInString(); // 2024
        String startWith = year.charAt(year.length() - 2) + "" + year.charAt(year.length() - 1);

        // Grab the remainder for the id
        int id = random.nextInt(10000, 99999);

        // Building the string format of the id to return
        return startWith + "" + id;
    }

    // Generate random module code
    public static String generateModuleCode() {
        String capitalCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String numbers = "0123456789";

        Random random = new Random();
        StringBuilder startWith = new StringBuilder();
        StringBuilder endWith = new StringBuilder();

        // Three random letters
        for(int i = 0; i < 3; i++) {
            startWith.append(capitalCaseLetters.charAt(random.nextInt(capitalCaseLetters.length())));
        }

        // Four random numbers
        for(int i = 0; i < 4; i++) {
            endWith.append(numbers.charAt(random.nextInt(numbers.length())));
        }

        return startWith.toString() + endWith.toString();
    }

    // Convert string to title case
    public static String titleCase(String s, boolean delimiterChecker) {
        if(delimiterChecker) return WordUtils.capitalizeFully(s, ' ', '-', '_');

        return WordUtils.capitalizeFully(s);
    }

    // To check if the first character of a string is a capital letter
    public static boolean isUpperCase(String str) {
        if (str != null && !str.isEmpty()) {
            char firstChar = str.charAt(0);
            return Character.isUpperCase(firstChar);
        }
        return false; // Return false for an empty or null string
    }

    // Convert date object to the format of '2024'
    public static String currentYearInString(){
        Date date = new Date();

        // Create a LocalDateTime object representing the date and time
        // Convert Date to LocalDateTime
        Instant instant = date.toInstant();
        LocalDateTime localDateTime = instant.atZone(ZoneId.systemDefault()).toLocalDateTime();

        // Define a custom date and time format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy");

        // Format the LocalDateTime object using the formatter
        return localDateTime.format(formatter);
    }

    // Convert date object to the format of 'December 31, 2023 at 2:30 p.m.'
    public static String currentDateAndTimeInString(){
        Date date = new Date();

        // Create a LocalDateTime object representing the date and time
        // Convert Date to LocalDateTime
        Instant instant = date.toInstant();
        LocalDateTime localDateTime = instant.atZone(ZoneId.systemDefault()).toLocalDateTime();

        // Define a custom date and time format
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm a");

        // Format the LocalDateTime object using the formatter
        return localDateTime.format(dateFormatter) + " at " + localDateTime.format(timeFormatter).replace("am", "a.m.").replace("pm", "p.m.");
    }

    // Will be used to validate
    public static boolean isEmailValid(String email){
        return EmailValidator.getInstance().isValid(email);
    }


    // Get the first characters of each words
    public static String getShortenedNameCharacters(String text) {
        String[] arrays = text.split(" ");
        StringBuilder sb = new StringBuilder();

        for (String s: arrays) {
            sb.append(s.toUpperCase().charAt(0));
        }

        return sb.toString();
    }

    // Get the first two characters of a string
    public static String getFirstTwoCharactersFromString(String text) {
        return text.charAt(0) + "" + text.charAt(1);
    }

    // Capture the last time the probation list was updated
    public static void updatedProbationList(){
        PROBATION_LIST_UPDATED = new Date();
    }

    // Convert date object to the format of 'December 31, 2023 at 2:30 p.m.'
    public static String getLastTimeProbationListUpdatedDateAndTimeInString(){
        if(PROBATION_LIST_UPDATED == null) PROBATION_LIST_UPDATED = new Date();

        // Create a LocalDateTime object representing the date and time
        // Convert Date to LocalDateTime
        Instant instant = PROBATION_LIST_UPDATED.toInstant();
        LocalDateTime localDateTime = instant.atZone(ZoneId.systemDefault()).toLocalDateTime();

        // Define a custom date and time format
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm a");

        // Format the LocalDateTime object using the formatter
        return localDateTime.format(dateFormatter) + " at " + localDateTime.format(timeFormatter).replace("am", "a.m.").replace("pm", "p.m.");
    }
}
