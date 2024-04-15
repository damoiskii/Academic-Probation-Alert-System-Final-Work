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

package com.app.view.tabs.students;

import com.app.exception.StudentNotFoundException;
import com.app.model.Student;
import com.app.utils.BaseUtils;
import com.app.utils.IconUtils;
import com.app.utils.SchoolUtils;
import com.app.view.customs.CTextField;
import com.app.view.listeners.BlurPaneListener;
import com.app.view.listeners.FilterListener;
import com.app.view.listeners.StudentTableMenuItemListener;
import com.app.view.listeners.StudentTabListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;


public class StudentTabPanel extends JPanel {
    private static final Logger LOGGER = LoggerFactory.getLogger(StudentTabPanel.class);

    private JPanel northPanel;

    private JButton searchBtn, addStudentBtn, filterBtn, generatorBtn, refreshBtn, clearTableBtn;
    // private JTextField searchField;
    private CTextField searchField;
    private JLabel infoLabel;

    // Dialogs
    private FilterDialog filterDialog;
    private StudentFormDialog studentFormDialog;
    private StudentInfoDialog studentInfoDialog;

    private StudentTablePanel studentTablePanel;

    // Listeners
    private StudentTabListener studentTabListener;
    private Map<String, Set<String>> filterByDialogOption;
    private BlurPaneListener blurPaneListener;

    private volatile String generatedStudentID;


    public StudentTabPanel(){
        super();

        settingPanelProperties();
        initializeComponents();
        addComponentsToPanels();
        registerListeners();

        focus();
    }

    // Set Student Tab Listener
    public void setStudentTabListener(StudentTabListener studentTabListener){
        this.studentTabListener = studentTabListener;
        studentFormDialog.setStudentTabListener(studentTabListener);
        studentInfoDialog.setStudentTabListener(studentTabListener);

        // Starting the id generator thread...
        generateStudentIDNumber();
    }

    public void setBlurPaneListener(BlurPaneListener blurPaneListener) {
        this.blurPaneListener = blurPaneListener;
        filterDialog.setBlurPaneListener(blurPaneListener);
        studentFormDialog.setBlurPaneListener(blurPaneListener);
        studentInfoDialog.setBlurPaneListener(blurPaneListener);
        studentTablePanel.setBlurPaneListener(blurPaneListener);
    }

    // Loading the table data.
    public void loadTableData(List<Student> dataset, String action) {
        // Load the latest years accumulated
        if(studentInfoDialog != null) studentInfoDialog.loadData();

        if(dataset.size() > 0) clearTableBtn.setVisible(true);

        studentTablePanel.loadTableData(dataset, action);

        loadFilterData(dataset);
        loadStudentFormOptions();
    }

    // This method will populate the dialog filter form with the necessary data...
    public void loadFilterData(List<Student> dataset){
        filterByDialogOption = new HashMap<>();

        // Update filter dialog
        Thread thread = new Thread(() -> {
            for(Student student : dataset){
                // Setup schools info
                Set<String> schools = filterByDialogOption.getOrDefault("schools", null);
                if(schools == null) schools = new HashSet<>();

                // Add school to the schools set
                schools.add(student.getSchool());
                filterByDialogOption.put("schools", schools);

                // Setup programme info
                Set<String> programmes = filterByDialogOption.getOrDefault("programmes", null);
                if(programmes == null) programmes = new HashSet<>();

                // Add programme to the programmes set
                programmes.add(student.getProgramme());
                filterByDialogOption.put("programmes", programmes);
            }

            filterDialog.setFilterByDialogOption(filterByDialogOption);
            filterDialog.loadData();
        });

        thread.setName("s-tab-loader-f");
        thread.start();
    }

    // This method will populate the dialog form of the add student with the necessary data...
    public void loadStudentFormOptions(){
        // Update filter dialog
        Thread thread = new Thread(() -> {
            // Setup schools info
            studentFormDialog.setStudentFormDialogOption(SchoolUtils.schools());
            studentFormDialog.loadData();
        });

        thread.setName("s-tab-loader-s");
        thread.start();
    }

    // Generate a student id number in the background while the program loads or closes the form
    public void generateStudentIDNumber(){
        if(studentTabListener != null) {
            Thread thread = new Thread(() -> {
                while (true){
                    generatedStudentID = BaseUtils.randomlyPickIDForStudentForm();

                    boolean found = studentTabListener.isIDFound(generatedStudentID);

                    // If the id is not in the database then break the loop...
                    if (!found) break;
                }
            });

            thread.setName("id-gen-thread");
            thread.start();
        }
    }

    // Initialize the frame's components
    private void initializeComponents() {
        // Labels
        infoLabel = new JLabel("Results for keyword '...'", SwingConstants.LEFT);

        // Panels
        northPanel = new JPanel(new BorderLayout());

        // Button
        addStudentBtn = new JButton();
        searchBtn = new JButton("Search");

        filterBtn = new JButton();
        refreshBtn = new JButton();
        clearTableBtn = new JButton();
        generatorBtn = new JButton("Generate Students");

        // Visibility
        refreshBtn.setVisible(false);
        clearTableBtn.setVisible(false);
        infoLabel.setVisible(false);

        // Text field
        searchField = new CTextField("Search by name, email or id #...", 50);

        // Table
        studentTablePanel = new StudentTablePanel();


        // Tooltips
        addStudentBtn.setToolTipText("Click to add a new student");
        searchBtn.setToolTipText("Click to search for students");
        filterBtn.setToolTipText("Click to apply table filter");
        refreshBtn.setToolTipText("Click to refresh table data");
        generatorBtn.setToolTipText("Click to generate students data");
        clearTableBtn.setToolTipText("Click to delete all students information");

        // Icons
        IconUtils.setIcon(addStudentBtn, "add");
        IconUtils.setIcon(searchBtn, "search");
        IconUtils.setIcon(filterBtn, "filter");
        IconUtils.setIcon(refreshBtn, "refresh");
        IconUtils.setIcon(generatorBtn, "generate");
        IconUtils.setIcon(clearTableBtn, "clear");


        // Filter Dialog
        filterDialog = new FilterDialog(null, "Filter Students");

        // Add Student Form Dialog
        studentFormDialog = new StudentFormDialog(null, "Add Student Form");

        // Add Student Info Dialog
        studentInfoDialog = new StudentInfoDialog(null, "Student Information");
    }

    // Setting the panel properties
    private void settingPanelProperties() {
        setLayout(new BorderLayout());
        //setBackground(Color.GRAY);
    }

    // Adding the components to panel
    private void addComponentsToPanels() {
        JPanel subNorthPanel1 = new JPanel(new FlowLayout());
        JPanel subNorthPanel2 = new JPanel(new FlowLayout());
        JPanel subNorthPanel3 = new JPanel(new FlowLayout());

        subNorthPanel1.add(searchField);
        subNorthPanel1.add(searchBtn);
        subNorthPanel1.add(filterBtn);

        subNorthPanel2.add(addStudentBtn);
        subNorthPanel2.add(refreshBtn);
        subNorthPanel2.add(clearTableBtn);
        subNorthPanel2.add(generatorBtn);

        subNorthPanel3.add(infoLabel);

        northPanel.add(subNorthPanel1, BorderLayout.CENTER);
        northPanel.add(subNorthPanel2, BorderLayout.EAST);
        northPanel.add(subNorthPanel3, BorderLayout.WEST);

        add(northPanel, BorderLayout.NORTH);
        add(studentTablePanel, BorderLayout.CENTER);
    }

    // Registering listeners
    private void registerListeners() {
        // Generator Button
        generatorBtn.addActionListener(e -> {
            focus();

            if(blurPaneListener != null) blurPaneListener.blurBackground();

            String response = JOptionPane.showInputDialog(null, "Enter the amount of students you wish to generate: ", "Confirm Generation Amount", JOptionPane.OK_OPTION|JOptionPane.INFORMATION_MESSAGE);
            
            // If empty
            if(response == null || response.length() == 0){
                // showErrorMessage("Sorry, only positive values are allowed to be entered here!", "Number Format Error");
                if(blurPaneListener != null) blurPaneListener.unBlurBackground();
                return;
            }

            // Validate user input
            try{
                int amount = Integer.parseInt(response);

                // If the user entered a value less than one then throw an error
                if(amount < 1) {
                    showErrorMessage("Sorry, only positive values are allowed to be entered here!", "Number Format Error");
                    if(blurPaneListener != null) blurPaneListener.unBlurBackground();
                    return;
                }

                if(studentTabListener != null) {
                    infoLabel.setVisible(false);
                    infoLabel.setText("");

                    // Offload work to another thread
                    Thread thread = new Thread(() -> {
                        LOGGER.info("Running Students Generator...");

                        // Update info label
                        studentTablePanel.updateLabel("Generating students data");

                        // Emptying the table...
                        studentTablePanel.loadTableData(new ArrayList<>(), "refresh");

                        // Generate data and retrieve list of data
                        List<Student> students = studentTabListener.generateStudents(amount);

                        // Load filter data
                        loadFilterData(students);

                        // Hide or show clear button
                        clearTableBtn.setVisible(students.size() > 0);
                        searchField.reset();

                        // Update the student table with the generated information
                        studentTablePanel.loadTableData(students, "refresh");

                        // Update info label
                        studentTablePanel.updateLabel("No data available...");

                        LOGGER.info("Completed Students Generation!\n");
                    });

                    thread.setName("s-tab-add");
                    thread.start();
                }
            }
            catch (NumberFormatException ignore){
                showErrorMessage("Sorry, only numbers are allowed to be entered here!", "Number Format Error");
            }

            if(blurPaneListener != null) blurPaneListener.unBlurBackground();
        });

        // Refresh Button
        refreshBtn.addActionListener(e -> {
            focus();

            if(blurPaneListener != null) blurPaneListener.blurBackground();

            int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to refresh the table?", "Confirm Action", JOptionPane.YES_NO_OPTION);

            if(choice == JOptionPane.YES_OPTION){
                if(studentTabListener != null) {
                    refresh("Refreshing table data", 6000);
                }
            }

            if(blurPaneListener != null) blurPaneListener.unBlurBackground();
        });

        // Clear Table Button
        clearTableBtn.addActionListener(e -> {
            focus();

            if(blurPaneListener != null) blurPaneListener.blurBackground();

            int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete all students?", "Confirm Action", JOptionPane.YES_NO_OPTION);

            if(choice == JOptionPane.YES_OPTION){
                if(studentTabListener != null) {
                    infoLabel.setVisible(false);
                    infoLabel.setText("");

                    // Offload work to another thread
                    Thread thread = new Thread(() -> {
                        LOGGER.info("Running Students Deleter...");

                        // Update info label
                        studentTablePanel.updateLabel("Deleting students' data");

                        // Emptying the table...
                        studentTablePanel.loadTableData(new ArrayList<>(), "refresh");

                        // Fake worker: Temp pause to simulate system working...
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException ignore) {}

                        // Deleting the data from the database
                        studentTabListener.deleteStudents();

                        // Load filter data
                        loadFilterData(new ArrayList<>());

                        clearTableBtn.setVisible(false);
                        refreshBtn.setVisible(false);
                        searchField.reset();

                        // Update info label
                        studentTablePanel.updateLabel("No data available...");

                        LOGGER.info("Completed Students Deletion!\n");
                    });

                    thread.setName("s-tab-clear");
                    thread.start();
                }
            }

            if(blurPaneListener != null) blurPaneListener.unBlurBackground();
        });

        // Search Button
        searchBtn.addActionListener(e -> {
            focus();

            if(studentTabListener != null) {
                if(searchField.getText().trim().length()> 0 && !searchField.isTextsDifferent()) {
                    if(blurPaneListener != null) blurPaneListener.blurBackground();

                    //showErrorMessage("Sorry, you have not entered a search term!", "Search Error");
                    JOptionPane.showMessageDialog(null, "Sorry, you have not entered a search term!", "Search Error", JOptionPane.ERROR_MESSAGE);

                    if(blurPaneListener != null) blurPaneListener.unBlurBackground();
                    return;
                }

                // Offload work to another thread
                Thread thread = new Thread(() -> {
                    LOGGER.info("Running Students Searcher...");

                    // Update info label
                    studentTablePanel.updateLabel("Searching for student(s)");

                    // Emptying the table...
                    studentTablePanel.loadTableData(new ArrayList<>(), "refresh");

                    // Fake worker: Temp pause to simulate system working...
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException ignore) {}

                    // Grab the search term
                    String keyword = searchField.getText();

                    // Search and retrieve data to populate the students table
                    List<Student> students = studentTabListener.search(keyword);

                    // Set keyword to highlight in the table search results
                    studentTablePanel.setKeyword(keyword);

                    // Update the student table with the retrieved information
                    studentTablePanel.loadTableData(students, "refresh");

                    clearTableBtn.setVisible(false);
                    refreshBtn.setVisible(true);
                    searchField.reset();

                    infoLabel.setText("<html>Results for keyword '<strong style=\"background-color: yellow; font-weight: bold; text-decoration: underline;\">" + keyword + "</strong>'</html>");
                    infoLabel.setVisible(true);

                    // Set keyword to null
                    studentTablePanel.setKeyword(null);

                    // Update info label
                    studentTablePanel.updateLabel("No data available...");

                    LOGGER.info("Completed Students Search!\n");
                });

                thread.setName("s-tab-search");
                thread.start();
            }
        });

        // Filter Button
        filterBtn.addActionListener(e -> {
            focus();

            // If dialog already created but hidden
            if(filterDialog != null && !filterDialog.isVisible()) {
                if(blurPaneListener != null) blurPaneListener.blurBackground();

                filterDialog.setVisible(true);
            }
        });

        // Setup filter dialog listener
        filterDialog.setFilterListener(new FilterListener() {
            @Override
            public void filterStudents(String field, String by) {
                // Offload work to another thread
                Thread thread = new Thread(() -> {
                    LOGGER.info("Running Students Filter...");

                    // Update info label
                    studentTablePanel.updateLabel("Filtering students");

                    // Emptying the table...
                    studentTablePanel.loadTableData(new ArrayList<>(), "refresh");

                    // Fake worker: Temp pause to simulate system working...
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException ignore) {}

                    // Search and retrieve data to populate the students table
                    List<Student> students = studentTabListener.filter(field, by);

                    // Set filter by to highlight in the table search results
                    studentTablePanel.setFilterBy(by);

                    // Update the student table with the retrieved information
                    studentTablePanel.loadTableData(students, "refresh");

                    clearTableBtn.setVisible(false);
                    refreshBtn.setVisible(true);
                    searchField.reset();

                    infoLabel.setText("<html>Filtered students by " + field.toLowerCase() + " = '<strong style=\"background-color: yellow; font-weight: bold;\">" + by + "</strong>'</html>");
                    infoLabel.setVisible(true);

                    // Set keyword to null
                    studentTablePanel.setFilterBy(null);

                    // Update info label
                    studentTablePanel.updateLabel("No data available...");

                    LOGGER.info("Completed Students Filter!\n");
                });

                thread.setName("s-tab-filter");
                thread.start();
            }
        });

        // Setup the table menu item listener
        studentTablePanel.setMenuItemListener(new StudentTableMenuItemListener() {
            @Override
            public boolean deleteStudent(String student) {
                boolean success = false;

                if(studentTabListener != null){
                    LOGGER.info("Running Students Deleter...");

                    // Delete student's data from the table and the database
                    try {
                        studentTablePanel.deleteData();
                        studentTabListener.deleteStudent(student);

                        success = true;
                    } catch (StudentNotFoundException ignore) {}

                    clearTableBtn.setVisible(false);
                    refreshBtn.setVisible(true);
                    //infoLabel.setVisible(false);
                    searchField.reset();

                    LOGGER.info("Completed Student Deletion!\n");
                }

                return success;
            }

            @Override
            public void updateStudent(Student student) {
                // If dialog already created but hidden
                if(studentFormDialog != null && !studentFormDialog.isVisible()) {
                    if(blurPaneListener != null) blurPaneListener.blurBackground();

                    refreshBtn.setVisible(false);

                    // Update the form to reflect the student's information
                    studentFormDialog.turnIntoUpdateForm(student);

                    studentFormDialog.setVisible(true);
                }
            }

            @Override
            public void viewStudentInfo(Student student) {
                // If dialog already created but hidden
                if(studentInfoDialog != null && !studentInfoDialog.isVisible()) {
                    if(blurPaneListener != null) blurPaneListener.blurBackground();

                    refreshBtn.setVisible(false);

                    // Load the latest years accumulated
                    studentInfoDialog.loadData();

                    studentInfoDialog.setStudent(student);
                    studentInfoDialog.setVisible(true);
                }
            }
        });

        // Add Button
        addStudentBtn.addActionListener(e -> {
            focus();

            // If dialog already created but hidden
            if(studentFormDialog != null && !studentFormDialog.isVisible()) {
                if(blurPaneListener != null) blurPaneListener.blurBackground();

                refreshBtn.setVisible(false);

                studentFormDialog.setGeneratedIDField(generatedStudentID); // Passing the generated id# here when opening the form
                studentFormDialog.setVisible(true);
            }
        });
    }

    private void focus(){
        if(northPanel != null){
            northPanel.setFocusable(true);
            northPanel.grabFocus();
        }
    }

    /* Utility Methods */
    private void showErrorMessage(String message, String title){
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);
    }

    public int rowCount(){
        return studentTablePanel.getTableRowCount();
    }

    // Updating the table with the new student data added to the system
    public void addData(Student student){
        refreshBtn.setVisible(true);

        studentTablePanel.addData(student);
    }

    public void updateData(Student student){
        refreshBtn.setVisible(true);

        studentTablePanel.updateData(student);
    }

    // Refreshing page
    public void refresh(String text, long sleep){
        infoLabel.setVisible(false);
        infoLabel.setText("");

        // Offload work to another thread
        Thread thread = new Thread(() -> {
            LOGGER.info("Running Students Refresher...");

            // Update info label
            studentTablePanel.updateLabel(text);

            // Emptying the table...
            studentTablePanel.loadTableData(new ArrayList<>(), "refresh");

            // Fake worker: Temp pause to simulate system working...
            try {
                Thread.sleep(sleep);
            } catch (InterruptedException ignore) {}

            // Retrieve data and populate the students table
            List<Student> students = studentTabListener.refreshTable();

            // Load filter data
            loadFilterData(students);

            // Hide or show clear button
            clearTableBtn.setVisible(students.size() > 0);
            refreshBtn.setVisible(false);
            searchField.reset();

            // Update the student table with the retrieved information
            studentTablePanel.loadTableData(students, "refresh");

            // Update info label
            studentTablePanel.updateLabel("No data available...");

            LOGGER.info("Completed Students Table Refreshment!\n");
        });

        thread.setName("s-tab-fill");
        thread.start();
    }
}
