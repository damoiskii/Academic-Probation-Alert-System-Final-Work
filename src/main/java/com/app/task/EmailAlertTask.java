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

package com.app.task;


import com.app.controller.ModuleDetailController;
import com.app.controller.ProbationController;
import com.app.controller.PrologController;
import com.app.controller.StudentController;
import com.app.email.Email;
import com.app.email.EmailServiceImpl;
import com.app.exception.ProbationFoundException;
import com.app.exception.ProbationNotFoundException;
import com.app.model.ModuleDetail;
import com.app.model.Probation;
import com.app.model.Student;
import com.app.utils.BaseUtils;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
@RequiredArgsConstructor
public class EmailAlertTask {
    private final Logger logger = LoggerFactory.getLogger(EmailAlertTask.class);

    private final PrologController prologController;
    private final ModuleDetailController moduleDetailController;
    private final StudentController studentController;
    private final ProbationController probationController;
    private final EmailServiceImpl emailService;

    private Double gpa;
    private boolean capturedGPA = false;


    // Method that will search for students whose GPA is less or equal to the default GPA in the system
    @Scheduled(fixedDelayString = "PT05M") // delay for 5 minutes   -> sub @Scheduled(cron = "${TESTING_ALERT_INTERVAL}")
    private void search(){
        getDefaultGPA();

        // GPA is not captured as yet so return...
        if(!capturedGPA) return;

        // Retrieve a list of all existing students
        List<Student> students = studentController.getAllStudents();

        // If no students are in the system then return
        if(students.size() < 1) return;

        // Data structures to capture unique data
        Map<String, String> studentTracker = new HashMap<>();
        Map<String, List<Integer>> cumulativeTotalCredits = new HashMap<>();
        Map<String, List<Double>> cumulativeTotalPointsEarned = new HashMap<>();
        Map<String, List<Double>> allScores = new HashMap<>();

        boolean canCalculate = false;

        // Iterating over the list of students
        for(Student student: students){
            // Keeping track of the student's ID # and name
            studentTracker.put(student.getId(), BaseUtils.titleCase(student.getName(), true));

            // Retrieve a list of all modules the current student attempted
            List<ModuleDetail> details = moduleDetailController.findAllModuleDetailsByStudentID(student.getId());

            int count = 0;

            // Loop over list of module details found for the student
            for(ModuleDetail detail: details){
                List<Double> scores = allScores.getOrDefault(student.getId(), new ArrayList<>());
                List<Integer> credits = cumulativeTotalCredits.getOrDefault(student.getId(), new ArrayList<>());

                // Add score to the list for the student received for the module
                scores.add(detail.getGradePoints());

                // Add credits for the module
                if(detail.getModule() != null) credits.add(detail.getModule().getCredits());

                // Update the maps
                allScores.put(student.getId(), scores);
                cumulativeTotalCredits.put(student.getId(), credits);

                canCalculate = true;
                count++;
            }

            if(canCalculate){
                List<Double> scores = allScores.get(student.getId());
                List<Integer> credits = cumulativeTotalCredits.get(student.getId());

                for(int i = 0; i < count; i++){
                    List<Double> pointsEarned = cumulativeTotalPointsEarned.getOrDefault(student.getId(), new ArrayList<>());

                    // Allow prolog to calculate the total points earned and return the amount to the pointsEarned list
                    String earned = prologController.getPointsEarned(credits.get(i), scores.get(i));

                    pointsEarned.add((earned.equalsIgnoreCase("-") ? 0.0 : Double.parseDouble(earned)));

                    // Update the maps
                    cumulativeTotalPointsEarned.put(student.getId(), pointsEarned);
                }

                // Using prolog to calculate the semester's and cumulative gpa.
                // Calculating total credits using prolog
                double overallTotalCredits = prologController.totalCredits(credits);

                // Calculating total points earned using prolog
                double overallTotalPointsEarned = prologController.totalPointsEarned(cumulativeTotalPointsEarned.getOrDefault(student.getId(), new ArrayList<>()));

                // Calculating cumulative GPA using prolog
                double overallGPA = prologController.getCumulativeGPA(overallTotalPointsEarned, overallTotalCredits);


                // If cumulative GPA is less than or equal to the default GPA then add to list
                if(overallGPA <= gpa){
                    // Add to a probation list
                    Probation probation = new Probation();

                    probation.setName(student.getName());
                    probation.setStudentID(student.getId());
                    probation.setSchool(student.getSchool());
                    probation.setProgramme(student.getProgramme());
                    probation.setGpa(overallGPA);

                    // Add probation information to the database
                    try {
                        probationController.addProbation(probation);

                        // Indication that the probation list was last updated at x date and time
                        BaseUtils.updatedProbationList();
                    }
                    catch (ProbationFoundException e) {
                        logger.error("Error adding probation [ProbationFoundException]: " + e.getMessage());
                    }
                    catch (Exception e) {
                        logger.error("Error adding probation [Exception]: " + e.getMessage());
                    }

                    try{
                        sendAlert(student);
                    }
                    catch (Exception e) {
                        logger.error("Error sending email [Exception]: " + e.getMessage());
                    }
                }

                // Clear credits
                cumulativeTotalCredits.clear();

                // Clear scores
                allScores.clear();
                cumulativeTotalPointsEarned.clear();

                canCalculate = false;
            }
        }

        System.out.println("\n\n");
    }


    // Send alert to students whose GPA is less than or equal to the default GPA
    private void sendAlert(Student student){
        // Email student and the relevant personnel's
        String[] advisor = prologController.getAdvisor(student.getSchool(), BaseUtils.getFirstTwoCharactersFromString(student.getId()));
        String[] programmeDirector = prologController.getProgrammeDirector(student.getSchool(), student.getProgramme());
        String[] facultyAdministrator = prologController.getFacultyAdministrator(student.getSchool());

        String[] copyingOnEmail = {facultyAdministrator[1], advisor[1]};
        String[] recipients = {programmeDirector[1], student.getEmail()};

        String emailBody = "Dear " + programmeDirector[0] + ",\n\n" +
                "I hope this email finds you well.\n\n" +

                "I am writing to inform you about an important update regarding our system's monitoring of " +
                "students' academic performance. Our program now includes a feature that periodically " +
                "checks the system for students whose GPA falls below or equals the default GPA threshold.\n\n" +

                "As of the latest check, we have identified the following student who require immediate " +
                "attention due to his/her GPA status:\n\n" +

                "\t- " + student.getName() + " [" + student.getId() + "]: Attending the " + student.getSchool() + ", and enrolled in the " + student.getProgramme() + ".\n\n" +

                "These alerts have been automatically sent to the respective students, their academic " +
                "advisors, the program directors, and the faculty administrators to ensure timely " +
                "support and intervention.\n\n" +

                "We kindly request your prompt assistance in reaching out to this student to provide " +
                "necessary guidance and support to help improve their academic performance.\n\n" +

                "Should you have any questions or require further information, please feel free to contact " +
                "me directly.\n\n" +

                "Thank you for your attention to this matter.\n\n" +

                "Best regards,\n" +
                "AI Alert System\n\n";

        Email email = new Email();
        email.setSubject("Alert: Students with Low GPA");
        email.setContent(emailBody);
        email.setCc(Arrays.toString(copyingOnEmail));
        email.setRecipient(Arrays.toString(recipients));

        // Sending email
        try {
            emailService.sendAlertEmail(email);
            logger.info("Alert sent regarding student: " + student.getName() + " [" + student.getId() + "].");
        }
        catch (MessagingException e) {
            logger.error("Error sending email [MessagingException]: " + e.getMessage());
        }
        catch (Exception e) {
            logger.error("Error sending email [Exception]: " + e.getMessage());
        }
    }


    // Fetch from Prolog the default GPA value
    private void getDefaultGPA(){
        // Get default value from prolog
        String gpaString = prologController.getDefaultGPA();

        if(gpaString == null){
            boolean newGPASet = prologController.setNewGPA(2.2);

            if(newGPASet){
                gpaString = prologController.getDefaultGPA();
            }
            else{
                capturedGPA = false;
                return;
            }
        }

        if(gpaString != null){
            gpa = Double.valueOf(gpaString);
            capturedGPA = true;
        }
    }


    @Scheduled(fixedDelayString = "PT05M") // delay for 5 minutes   -> sub @Scheduled(cron = "${TESTING_ALERT_INTERVAL}")
    private void deleteFromProbationList(){
        getDefaultGPA();

        // GPA is not captured as yet so return...
        if(!capturedGPA) return;

        // Retrieve a list of all existing students
        List<Probation> list = probationController.findAllStudentsOnProbation();

        // Iterating over the list of students
        for(Probation probationStudent: list){
            if(probationStudent.getGpa() > gpa) {
                try {
                    probationController.deleteProbation(probationStudent.getStudentID());
                }
                catch (ProbationNotFoundException ignore) {}
            }
        }
    }
}
