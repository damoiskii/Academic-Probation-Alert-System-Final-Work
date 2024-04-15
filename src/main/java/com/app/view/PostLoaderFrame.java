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

package com.app.view;

import com.app.config.PopulateData;
import com.app.controller.*;
import com.app.exception.*;
import com.app.model.Module;
import com.app.model.ModuleDetail;
import com.app.model.Probation;
import com.app.model.Student;
import com.app.service.LoadLazyCollectionServiceImpl;
import com.app.utils.BaseUtils;
import com.app.utils.SchoolUtils;
import com.app.utils.SimulationUtils;
import com.app.view.listeners.*;
import com.app.view.menubar.MenuBar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;


@Component
public class PostLoaderFrame extends JFrame {
    private final Logger LOGGER = LoggerFactory.getLogger(PostLoaderFrame.class);

    private final PrologController prologController;
    private final PopulateData populateData;
    private final StudentController studentController;
    private final ModuleController moduleController;
    private final LoadLazyCollectionServiceImpl loadLazyCollectionService;
    private final ModuleDetailController moduleDetailController;
    private final ReportController reportController;
    private final ProbationController probationController;

    private MainPanel mainPanel;
    private MenuBar menuBar;
    private BlurredPane blurredPane;

    private Timer timer;


    public PostLoaderFrame(PrologController prologController, PopulateData populateData, StudentController studentController, ModuleController moduleController, LoadLazyCollectionServiceImpl loadLazyCollectionService,  ModuleDetailController moduleDetailController, ReportController reportController, ProbationController probationController) {
        super(BaseUtils.APP_NAME);

        this.prologController = prologController;
        this.populateData = populateData;
        this.studentController = studentController;
        this.moduleController = moduleController;
        this.loadLazyCollectionService = loadLazyCollectionService;
        this.moduleDetailController = moduleDetailController;
        this.reportController = reportController;
        this.probationController = probationController;

        settingWindowProperties();
        initializeComponents();
        addComponentsToPanels();
        addPanelsToWindow();
        registerListeners();

        loadTableData();
    }

    // Loading the necessary data from the prolog knowledge base
    public void loadDataFromProlog(){
        Thread thread = new Thread(() -> {
            String gpa = prologController.getDefaultGPA();

            if(gpa == null){
                // Setting default gpa to 2.2 if none exist
                prologController.setNewGPA(2.2);

                // Try fetching again...
                gpa = prologController.getDefaultGPA();
            }

            LOGGER.info("System's default GPA was retrieved and being transferred: " + gpa);

            // Transfer default gpa data retrieved from prolog to the necessary aspect(s) of the program to use/display
            if(gpa != null) menuBar.setGpa(Double.parseDouble(gpa));
        });

        thread.setName("prolog");
        thread.start();
    }


    // Load student table data...
    public void loadTableData() {
        mainPanel.loadTableData(studentController.getAllStudents(), moduleController.getAllModules(), probationController.findAllStudentsOnProbation(), "add");
    }

    // Initialize the frame's components
    private void initializeComponents() {
        // Panels
        mainPanel = new MainPanel();

        // Set the JMenuBar of the frame
        menuBar = new MenuBar();
        setJMenuBar(menuBar);
    }

    // Setting the window properties
    private void settingWindowProperties() {
        setLayout(new BorderLayout());
        //setSize(new Dimension(550, 80));
        //setResizable(false);
        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        interceptClosingWindowEvent();

        //Image icon = FileUtils.getImage("icon.png");
        //setIconImage(icon);
    }

    // This method will be used to intercept the closing window event to hide the window on close
    private void interceptClosingWindowEvent(){
        // To clean up the code by killing every other process such as database, etc.
        // We will intercept the window closing event
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose(); // To quit the application after clean up...
                System.gc(); // Call garbage collector

                if(timer.isRunning()) timer.stop();

                System.exit(0);
            }
        });
    }

    // Adding the components to panel
    private void addComponentsToPanels() {

    }

    // Adding the panel to the window
    private void addPanelsToWindow() {
        add(mainPanel, BorderLayout.CENTER);
    }

    // Registering listeners
    private void registerListeners() {
        // Blurred Background [Dimmed]
        if(blurredPane == null){
            blurredPane = new BlurredPane();

            // Adding a mouse event to the blurred pane...
            blurredPane.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    super.mousePressed(e);

                    /*// Form The Query Form
                    if(blurredPane.isVisible() && (queryForm != null && queryForm.isVisible())){
                        queryForm.setVisible(false);
                        queryForm.formReset();

                        blurredPane.setVisible(false);

                        return;
                    }

                    // For The Preference Settings Dialog
                    if(blurredPane.isVisible() && (preferenceDialog != null && preferenceDialog.isVisible())){
                        blurredPane.setVisible(false);
                        preferenceDialog.setVisible(false);

                        return;
                    }

                    // For The Action Form
                    if(blurredPane.isVisible() && (actionForm != null && actionForm.isVisible())){
                        actionForm.setVisible(false);
                        actionForm.formReset();

                        blurredPane.setVisible(false);

                        return;
                    }

                    // For the Group Form
                    if(blurredPane.isVisible() && (groupForm != null && groupForm.isVisible())){
                        groupForm.setVisible(false);
                        groupForm.formReset();

                        blurredPane.setVisible(false);

                        return;
                    }

                    // For the Role Form
                    if(blurredPane.isVisible() && (roleForm != null && roleForm.isVisible())){
                        roleForm.setVisible(false);
                        roleForm.formReset();

                        blurredPane.setVisible(false);

                        return;
                    }

                    // For the User Form
                    if(blurredPane.isVisible() && (userForm != null && userForm.isVisible())){
                        userForm.setVisible(false);
                        userForm.formReset();

                        blurredPane.setVisible(false);

                        return;
                    }

                    // For the Leads Form
                    if(blurredPane.isVisible() && (leadsForm != null && leadsForm.isVisible())){
                        leadsForm.setVisible(false);
                        leadsForm.formReset();

                        blurredPane.setVisible(false);

                        return;
                    }*/
                }
            });

            // Setting the blurred pane to be to frame's glass pane to show the blurred background whenever needed
            setGlassPane(blurredPane);
        }

        // Menubar Listener
        menuBar.setMenuBarListener(new MenuBarListener() {
            @Override
            public boolean setNewGPA(double gpa) {
                boolean updated = prologController.setNewGPA(gpa);

                // If updated then allow the system to reflect changes
                if(updated) loadDataFromProlog();

                return updated;
            }

            @Override
            public boolean generateReport(Double gpa, String year) {
                // Show progress bar
                mainPanel.showProgressBar("Generating report", true);

                //boolean generated = true;
                boolean generated = reportController.generate(gpa, year, "table");

                // Pausing for 5 seconds to pretend as if the program is busy and working
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException ignore) {}

                // Hide progress bar
                mainPanel.showProgressBar("Generating report", false);

                return generated;
            }
        });

        // Setup blurred background listener for menu options
        menuBar.setBlurPaneListener(new BlurPaneListener() {
            @Override
            public void blurBackground() {
                if(blurredPane != null && !blurredPane.isVisible()){
                    blurredPane.setVisible(true);
                }
            }

            @Override
            public void unBlurBackground() {
                if(blurredPane != null && blurredPane.isVisible()){
                    blurredPane.setVisible(false);
                }
            }
        });

        // Student Tab Listener
        mainPanel.setStudentTabListener(new StudentTabListener() {
            @Override
            public boolean addStudent(Student student) {
                // Adding a student
                try {
                    Student s = studentController.addStudent(student);

                    // Generate new id number...
                    mainPanel.generateStudentIDNumber();

                    // Add the new student data to the students table
                    mainPanel.addData(s);

                    // Updating the footer counter
                    mainPanel.updateStudentInfoLabel();
                    return true;
                }
                catch (StudentFoundException e) {
                    showErrorMessage(e.getMessage(), "Student Creation Error");
                }
                catch (DataIntegrityViolationException e) {
                    showErrorMessage("Please select both School and Programme!", "Student Creation Error");
                }

                return false;
            }

            @Override
            public boolean updateStudent(Student student, String oldEmail) {
                // Updating student
                try {
                    Student s = studentController.updateStudent(student, oldEmail);

                    // Generate new id number...
                    mainPanel.generateStudentIDNumber();

                    // Update the student data in the students table
                    mainPanel.updateData(s);
                    return true;
                }
                catch (StudentFoundException | StudentNotFoundException e) {
                    showErrorMessage(e.getMessage(), "Student Modification Error");
                }
                catch (DataIntegrityViolationException e) {
                    showErrorMessage("Please select both School and Programme!", "Student Modification Error");
                }

                return false;
            }

            @Override
            public List<Student> generateStudents(int amount) {
                // Generate x amount of students

                // Invoke thread to run 'Now generating [x] amount of students
                // Delete all existing students...
                studentController.deleteAllStudents();

                // Retrieve students list from generator
                List<Student> students = populateData.generateStudents(amount);
                int errorCount = 0;

                // Add students to database
                for(Student student : students) {
                    try {
                        studentController.addStudent(student);
                    }
                    catch (StudentFoundException | DataIntegrityViolationException e) {
                        // Increment if student was not added to the database...
                        errorCount++;
                        LOGGER.error("Student creation error: " + e.getMessage());
                    }
                }

                // Show error message if necessary
                if(errorCount > 0){
                    showErrorMessage(errorCount + " students were not created because of an email or id# error.", "Student Creation Notice");
                }

                // Retrieve all students from the database
                List<Student> savedStudents = studentController.getAllStudents();

                // Updating the footer counter
                mainPanel.updateStudentInfoLabel(savedStudents.size());
                return savedStudents;
            }

            @Override
            public List<Student> refreshTable() {
                // Get all students
                List<Student> students = studentController.getAllStudents();

                // Updating the footer counter
                mainPanel.updateStudentInfoLabel(students.size());
                return students;
            }

            @Override
            public List<Student> search(String keyword) {
                // Fina all by keyword
                List<Student> students = studentController.getAllStudentByKeyword(keyword);

                // Updating the footer counter
                mainPanel.updateStudentInfoLabel(students.size());
                return students;
            }

            @Override
            public void deleteStudents() {
                // Delete all module details
                moduleDetailController.deleteAllDetails();

                // Delete all students
                studentController.deleteAllStudents();

                // Updating the footer counter
                mainPanel.updateStudentInfoLabel(0);

                JOptionPane.showMessageDialog(null, "Information for all students were deleted successfully!", "Deletion Success", JOptionPane.INFORMATION_MESSAGE);
            }

            @Override
            public void deleteStudent(String studentID) throws StudentNotFoundException {
                // Updating the footer counter
                mainPanel.updateStudentInfoLabel();

                // Delete all module details pertaining the student
                moduleDetailController.deleteAllByStudentID(studentID);

                // Deleting the student from the database
                studentController.delete(studentID);
            }

            @Override
            public void deleteStudent(Student student) {
                // Updating the footer counter
                mainPanel.updateStudentInfoLabel();

                // Delete all module details pertaining the student
                moduleDetailController.deleteAllByStudentID(student.getId());

                // Deleting the student from the database
                studentController.delete(student);
            }

            @Override
            public boolean isIDFound(String studentID) {
                return studentController.lookupID(studentID);
            }

            @Override
            public List<Student> filter(String field, String by) {
                // Fina all by keyword
                List<Student> students = studentController.filterStudents(field, by);

                // Updating the footer counter
                mainPanel.updateStudentInfoLabel(students.size());
                return students;
            }

            @Override
            public Map<String, List<String>> findAllModules(String studentID, String semester, String schoolYear) {
                // Querying the module details table
                List<ModuleDetail> details = moduleDetailController.findAllDetails(semester, schoolYear, studentID);

                // Return results to display in the necessary table...
                return prologController.calculate(details, semester);
            }
        });

        // Setup blurred background listener
        mainPanel.setBlurPaneListener(new BlurPaneListener() {
            @Override
            public void blurBackground() {
                if(blurredPane != null && !blurredPane.isVisible()){
                    blurredPane.setVisible(true);
                }
            }

            @Override
            public void unBlurBackground() {
                if(blurredPane != null && blurredPane.isVisible()){
                    blurredPane.setVisible(false);
                }
            }
        });

        // Module Tab Listener
        mainPanel.setModuleTabListener(new ModuleTabListener() {
            @Override
            public boolean addModule(Module module) {
                // Adding a module
                try {
                    Module m = moduleController.addModule(module);

                    // Generate new module code...
                    mainPanel.generateModuleCode();

                    // Add the new module data to the modules table
                    mainPanel.addData(m);

                    // Updating the footer counter
                    mainPanel.updateModuleInfoLabel();
                    return true;
                }
                catch (ModuleFoundException e) {
                    showErrorMessage(e.getMessage(), "Module Creation Error");
                }
                catch (DataIntegrityViolationException e) {
                    showErrorMessage("Please select a School!", "Module Creation Error");
                }

                return false;
            }

            @Override
            public boolean updateModule(Module module, String oldName) {
                // Updating module
                try {
                    Module m = moduleController.updateModule(module, oldName);

                    // Generate new module code...
                    mainPanel.generateModuleCode();

                    // Update the module data in the modules table
                    mainPanel.updateData(m);
                    return true;
                }
                catch (ModuleFoundException | ModuleNotFoundException e) {
                    showErrorMessage(e.getMessage(), "Module Modification Error");
                }
                catch (DataIntegrityViolationException e) {
                    showErrorMessage("Please select a School!", "Module Modification Error");
                }

                return false;
            }

            @Override
            public List<Module> generateModules(int amount) {
                // Generate x amount of modules

                // Invoke thread to run 'Now generating [x] amount of modules
                // Delete all existing modules...
                moduleController.deleteAllModules();

                // Retrieve modules list from generator
                List<Module> modules = populateData.generateModules(amount);
                int errorCount = 0;

                // Add modules to database
                for(Module module : modules) {
                    try {
                        moduleController.addModule(module);
                    }
                    catch (ModuleFoundException | DataIntegrityViolationException e) {
                        // Increment if module was not added to the database...
                        errorCount++;
                        LOGGER.error("Module creation error: " + e.getMessage());
                    }
                }

                // Show error message if necessary
                if(errorCount > 0){
                    showErrorMessage(errorCount + " modules were not created because of an code error.", "Module Creation Notice");
                }

                // Retrieve all modules from the database
                List<Module> savedModules = moduleController.getAllModules();

                // Updating the footer counter
                mainPanel.updateModuleInfoLabel(savedModules.size());
                return savedModules;
            }

            @Override
            public List<Module> refreshTable() {
                // Get all modules
                List<Module> modules = moduleController.getAllModules();

                // Updating the footer counter
                mainPanel.updateModuleInfoLabel(modules.size());
                return modules;
            }

            @Override
            public List<Module> search(String keyword) {
                // Fina all by keyword
                List<Module> modules = moduleController.getAllModuleByKeyword(keyword);

                // Updating the footer counter
                mainPanel.updateModuleInfoLabel(modules.size());
                return modules;
            }

            @Override
            public void deleteModules() {
                // Delete all module details
                moduleDetailController.deleteAllDetails();

                // Delete all modules
                moduleController.deleteAllModules();

                // Updating the footer counter
                mainPanel.updateModuleInfoLabel(0);

                JOptionPane.showMessageDialog(null, "Information for all modules were deleted successfully!", "Deletion Success", JOptionPane.INFORMATION_MESSAGE);
            }

            @Override
            public void deleteModule(String moduleCode) throws ModuleNotFoundException {
                // Updating the footer counter
                mainPanel.updateModuleInfoLabel();

                // Delete all module details pertaining the module
                moduleDetailController.deleteByModuleCode(moduleCode);

                // Deleting the module from the database
                moduleController.delete(moduleCode);
            }

            @Override
            public void deleteModule(Module module) {
                // Updating the footer counter
                mainPanel.updateModuleInfoLabel();

                // Delete all module details pertaining the module
                moduleDetailController.deleteByModuleCode(module.getCode());

                // Deleting the module from the database
                moduleController.delete(module);
            }

            @Override
            public boolean isCodeFound(String module) {
                return false;
            }
        });

        // Lazy Loader Listener
        mainPanel.setLoadLazyListener(new LoadLazyListener() {
            @Override
            public Student start(Student student) {
                return loadLazyCollectionService.start(student);
            }

            @Override
            public Module start(Module module) {
                return loadLazyCollectionService.start(module);
            }
        });

        // Simulation Tab Listener
        mainPanel.setSimulationTabListener(new SimulationTabListener() {
            @Override
            public void moduleSelection() {
                Random random = new Random();
                // Set<Module> selectedModules;
                Set<String> selectedModuleCodes;

                List<Student> students = studentController.getAllStudents();

                // This is where each student will be assigned modules to simulate module selection
                for (Student student: students){
                    // Variable to store selected modules - Using a set to eliminate any possible duplicates
                    // selectedModules = new HashSet<>();
                    selectedModuleCodes = new HashSet<>();

                    // System.out.println("Student selecting: " + student);

                    // Decide how many modules a student will do randomly
                    int selectionAmount = random.nextInt(3, 7);

                    // Module assignment loop
                    for(int i = 0; i < selectionAmount; i++){
                        Module module = randomModule();

                        // System.out.println("Module selected: " + module);

                        // If the module has been selected... skip
                        // if(selectedModules.contains(module)) continue;
                        if(selectedModuleCodes.contains(module.getCode())) continue;

                        // If the student already completed the module then don't add
                        if(studentCompletedModule(student.getId(), module.getCode())) continue;

                        // System.out.println("Module selected [saving]: " + module + "\n");

                        // Create module details
                        ModuleDetail moduleDetail = new ModuleDetail();
                        moduleDetail.setModule(module);
                        moduleDetail.setStudent(student);
                        moduleDetail.setSemester(SimulationUtils.getSemester());
                        moduleDetail.setYear(SimulationUtils.getFormattedAcademicYear());
                        // moduleDetail.setRedo(false);

                        // If the student already done the module but failed, allow them to redo
                        moduleDetail.setRedo(wasFailedAndAttemptingRedo(student.getId(), module.getCode()));

                        // Save the module detail to the database...
                        moduleDetail = moduleDetailController.addDetail(moduleDetail);
                        // moduleDetailController.addDetail(moduleDetail);

                        if(module != null){
                            module = loadLazyCollectionService.start(module);
                            module.getDetails().add(moduleDetail);

                            // selectedModules.add(module);
                            selectedModuleCodes.add(module.getCode());

                            // Update module with details
                            try {
                                moduleController.updateModule(module);
                            } catch (ModuleNotFoundException ignore) {}
                        }

                        // selectedModules.add(module);
                        // selectedModuleCodes.add(module.getCode());
                    }
                    // Clear the module selection set for the next student
                    // selectedModules.clear();
                    selectedModuleCodes.clear();

                    // System.out.println("\n\n");
                }
            }

            @Override
            public void moduleResults() {
                // Fetch results for the current semester
                List<ModuleDetail> details = moduleDetailController.findAllModuleDetailsPerSemester(SimulationUtils.getSemester(), SimulationUtils.getFormattedAcademicYear());

                // Setting random grade on each module
                for (ModuleDetail detail : details) {
                    //String grade = SchoolUtils.getRandomGrade();
                    //boolean failed = (grade.equalsIgnoreCase("C-") || grade.equalsIgnoreCase("D+") || grade.equalsIgnoreCase("D") || grade.equalsIgnoreCase("D-") || grade.equalsIgnoreCase("E+") || grade.equalsIgnoreCase("E") || grade.equalsIgnoreCase("E-") || grade.equalsIgnoreCase("F"));

                    double gradePoint = SchoolUtils.getRandomGradePoint();

                    // Using grade to determine if student passed the course or not
                    detail.setComplete((gradePoint >= 2.2)); // C and above
                    //detail.setGrade(grade);
                    detail.setGradePoints(gradePoint);

                    // Update the results in the database
                    try {
                        moduleDetailController.updateDetail(detail);
                    } catch (ModuleDetailNotFoundException ignore) {}
                }
            }

            @Override
            public void resetSimulation() {
                moduleDetailController.deleteAllDetails();
            }

            @Override
            public void refreshSystem() {
                menuBar.loadMenuItemsData();
                mainPanel.refreshTableData(studentController.getAllStudents(), moduleController.getAllModules());
            }
        });

        // Probation Tab Listener
        mainPanel.setProbationTabListener(new ProbationTabListener() {
            @Override
            public List<Probation> refreshTable() {
                // Get all students on probation
                List<Probation> list = probationController.findAllStudentsOnProbation();

                // Updating the footer counter
                mainPanel.updateProbationInfoLabel(list.size());

                return list;
            }

            @Override
            public List<Probation> search(String keyword) {
                // Get all students on probation
                List<Probation> list = probationController.findAllProbationByKeyword(keyword);

                // Updating the footer counter
                mainPanel.updateProbationInfoLabel(list.size());

                return list;
            }

            @Override
            public List<Probation> filter(String field, String by) {
                // Get all students on probation
                List<Probation> list = probationController.filterProbationList(field, by);

                // Updating the footer counter
                mainPanel.updateProbationInfoLabel(list.size());

                return list;
            }

            @Override
            public void deleteProbationList() {
                probationController.deleteProbationList();

                // Updating the footer counter
                mainPanel.updateStudentInfoLabel(0);

                JOptionPane.showMessageDialog(null, "Information for all students on probation were deleted successfully!", "Deletion Success", JOptionPane.INFORMATION_MESSAGE);
            }

            @Override
            public void autoChecker() {
                Thread thread = new Thread(() -> {
                    // Refresh every two minutes
                    timer = new Timer((60000 * 2), e -> {
                        mainPanel.updateProbationListFooterText(BaseUtils.getLastTimeProbationListUpdatedDateAndTimeInString());
                        mainPanel.refreshProbationList();
                    });

                    timer.start();
                });

                thread.setName("auto-checker");
                thread.start();
            }
        });
    }


    /* Utility Methods */
    private void showErrorMessage(String message, String title){
        JOptionPane.showMessageDialog(PostLoaderFrame.this, message, title, JOptionPane.ERROR_MESSAGE);
    }


    // Return a random module
    private Module randomModule(){
        List<Module> modules = moduleController.getAllModules();
        Random random = new Random();

        Module module;

        try{
            int index = random.nextInt(modules.size());
            module = modules.get(index);
        }
        catch(IllegalArgumentException e){
            module = null;
        }
        catch(Exception e){
            module = modules.get(0);
        }

        return module;
    }

    // Check if the student already completed this course/module
    private boolean studentCompletedModule(String studentID, String moduleCode){
        List<ModuleDetail> passedModuleDetails = moduleDetailController.passedModuleDetails(studentID, moduleCode);

        // Already passed this module if the count is greater than 0
        return passedModuleDetails.size() > 0;
    }

    // Check if the selected module was previously done by the student but failed
    private boolean wasFailedAndAttemptingRedo(String studentID, String moduleCode){
        List<ModuleDetail> failedModuleDetails = moduleDetailController.failedModuleDetails(studentID, moduleCode);

        // Can attempt to redo this module if the count is greater than 0
        return failedModuleDetails.size() > 0;
    }
}
