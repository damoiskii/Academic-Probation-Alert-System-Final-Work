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


import com.app.model.Student;
import com.app.utils.IconUtils;
import com.app.utils.SimulationUtils;
import com.app.view.customs.CJScrollPane;
import com.app.view.listeners.BlurPaneListener;
import com.app.view.listeners.StudentTabListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.*;
import java.util.List;


public class StudentInfoDialog extends JDialog {
    private JLabel selectedYearLabel, desiredYearLabel;
    private JComboBox<String> yearCB;

    private JPanel panel, northPanel, buttonPanel;
    private JButton proceedBtn, closeBtn, resetBtn;

    private final String DEFAULT_OPTION_STRING = "---------------------------";
    private String selectedYear;

    private CJScrollPane scrollPane;
    private GPATablePanel gpaTablePanel;
    private Student student;

    // Listener
    private StudentTabListener studentTabListener;
    private BlurPaneListener blurPaneListener;


    public StudentInfoDialog(){
        super();

        focus();
    }

    public StudentInfoDialog(JFrame frame, String title){
        super(frame, title);

        settingWindowProperties();
        initializeComponents();
        addComponentsToPanels();
        addPanelsToWindow();
        registerListeners();

        focus();
    }

    // Setup student tab listener
    public void setStudentTabListener(StudentTabListener studentTabListener) {
        this.studentTabListener = studentTabListener;
    }

    public void setBlurPaneListener(BlurPaneListener blurPaneListener) {
        this.blurPaneListener = blurPaneListener;
    }

    // Set the student selected from the table
    public void setStudent(Student student) {
        this.student = student;
        setTitle("Now viewing information for " + student.getName() + " [" + student.getId() + "]");
    }

    // Load combo dialog data...
    public void loadData() {
        // Setup year combo box info
        if(yearCB != null) {
            // Get all school years simulated so far
            String[] allExistingSchoolYears = SimulationUtils.getAllSchoolYears();

            // Add year to the years list
            yearCB.removeAllItems();
            yearCB.addItem(DEFAULT_OPTION_STRING);
            yearCB.setPrototypeDisplayValue(DEFAULT_OPTION_STRING);

            for (String year: allExistingSchoolYears) {
                yearCB.addItem(year);
            }

            // Auto set selected year text based on the simulation current school year
            if(yearCB.getModel().getSize() > 0) yearCB.setSelectedItem(SimulationUtils.getFormattedAcademicYear());
        }
    }

    // Initialize the frame's components
    private void initializeComponents() {
        // Label
        selectedYearLabel = new JLabel("Selected Year: - ", SwingConstants.CENTER);
        desiredYearLabel = new JLabel("<html>Select Desired Year<font color='red'>*</font>: </html>", SwingConstants.CENTER);

        // Panels
        panel = new JPanel(new BorderLayout());
        northPanel = new JPanel(new BorderLayout());
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        gpaTablePanel = new GPATablePanel();

        // Scroll Pane
        // scrollPane = new CJScrollPane(centerPanel);
        scrollPane = new CJScrollPane(gpaTablePanel);


        // Combo box
        yearCB = new JComboBox<>(new String[]{DEFAULT_OPTION_STRING});

        // Buttons
        proceedBtn = new JButton("Proceed");
        closeBtn = new JButton("Close");
        resetBtn = new JButton("Reset");

        // Tooltips
        yearCB.setToolTipText("Select a desired year to view student's info");
        resetBtn.setToolTipText("Reset dialog to default");
        proceedBtn.setToolTipText("Click to proceed");
        resetBtn.setToolTipText("Click to close off dialog");

        // Visibility
        proceedBtn.setVisible(false);

        // Icons
        IconUtils.setIcon(closeBtn, "close");
        IconUtils.setIcon(proceedBtn, "proceed");
        IconUtils.setIcon(resetBtn, "refresh");
    }

    // Setting the window properties
    private void settingWindowProperties() {
        setLayout(new BorderLayout());
        setSize(new Dimension(1024, 600));
        setResizable(false);
        setLocationRelativeTo(null);
        setAlwaysOnTop(true);

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
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

                setVisible(false);
                setTitle("Student Information");
                closeBtn.doClick();
            }
        });
    }

    // Adding the components to panel
    private void addComponentsToPanels() {
        // North Panel
        buildNorthPanel();

        // Button Panel
        buttonPanel.add(resetBtn);
        buttonPanel.add(closeBtn);

        panel.add(northPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        // panel.add(gpaTablePanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
    }

    // Adding the panel to the window
    private void addPanelsToWindow() {
        add(panel, BorderLayout.CENTER);
    }

    // Registering listeners
    private void registerListeners() {
        // School Checkbox
        yearCB.addActionListener(e -> {
            focus();

            selectedYear = (String) yearCB.getSelectedItem();

            if(selectedYear != null && selectedYear.equalsIgnoreCase(DEFAULT_OPTION_STRING)) {
                updateSelectedYearText("-");
                proceedBtn.setVisible(false);

            }
            else if(selectedYear != null && !selectedYear.equalsIgnoreCase(DEFAULT_OPTION_STRING)) {
                updateSelectedYearText(null);
                proceedBtn.setVisible(true);
                clearTables();

            }
        });

        // Close Button
        closeBtn.addActionListener(e -> {
            if(blurPaneListener != null) blurPaneListener.unBlurBackground();

            focus();
            setVisible(false);
            reset();
        });

        // Reset Button
        resetBtn.addActionListener(e -> {
            focus();
            reset();
        });

        // Proceed Button
        proceedBtn.addActionListener(e -> {
            focus();

            // If the form can submit and proceed
            if(canFormSubmit()){
                Thread thread = new Thread(() -> {
                    // Query the database to get module details
                    if(studentTabListener != null) {
                        String currentAcademicYear = SimulationUtils.getAcademicYear();

                        // If no simulation was done prior invoking this method, inform the user accordingly...
                        if(infoIsNotPresentToView(currentAcademicYear)) return;

                        // Disabling the buttons that may interfere will operations
                        enableButtons(false);

                        // Continue...
                        clearTables();

                        // System.out.println("Cleared");
                        String currentSemester = SimulationUtils.getSemester();

                        // If the current school year is not equal to the selected school year then load relevant results accordingly
                        if(selectedYear.endsWith(currentAcademicYear) && (currentSemester.equalsIgnoreCase("semester 3") || currentSemester.equalsIgnoreCase("semester 2") || currentSemester.equalsIgnoreCase("semester 1"))){
                            fetchSemester1Results(false);
                            fetchSemester2Results(false);
                            fetchSemester3Results(false);
                        }

                        // If the current semester is semester 3 then load results for semester 1 & 2
                        else if(currentSemester.equalsIgnoreCase("semester 3")){
                            fetchSemester1Results(true);
                            fetchSemester2Results(true);
                        }

                        // If the current semester is semester 2 then load results for semester 1 only
                        else if(currentSemester.equalsIgnoreCase("semester 2")){
                            fetchSemester1Results(true);
                        }

                        // If the current semester is semester 1 then load results for semester 1, 2 & 3 from the previous school year
                        else if(currentSemester.equalsIgnoreCase("semester 1")){
                            fetchSemester1Results(true);
                            fetchSemester2Results(true);
                            fetchSemester3Results(true);
                        }

                        // Enabling the buttons that trigger operations
                        enableButtons(true);
                    }
                });

                thread.setName("calculate-gpa");
                thread.start();
            }
            else{
                showErrorMessage("Please fill out all required fields!", "Form Error");
            }
        });
    }

    private void focus(){
        if(panel != null){
            panel.setFocusable(true);
            panel.grabFocus();
        }
    }

    // Reset the dialog layout etc.
    private void reset(){
        yearCB.setSelectedIndex(0);

        selectedYear = null;
        clearTables();
    }

    // North Panel Builder
    private void buildNorthPanel(){
        JPanel np1 = new JPanel(new FlowLayout());
        JPanel np2 = new JPanel(new FlowLayout());
        JPanel np3 = new JPanel(new FlowLayout());

        // Combobox
        np1.add(desiredYearLabel);
        np1.add(yearCB);

        // Label
        np2.add(selectedYearLabel);

        // Button
        np3.add(proceedBtn);

        // Adding components to the north panel
        // northPanel.add(np1, BorderLayout.NORTH);
        northPanel.add(np1, BorderLayout.WEST);
        // northPanel.add(np2, BorderLayout.CENTER);
        northPanel.add(np2, BorderLayout.EAST);
        northPanel.add(np3, BorderLayout.SOUTH);
    }

    /* Utility Methods */
    private void showErrorMessage(String message, String title){
        setAlwaysOnTop(false);
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);
        setAlwaysOnTop(true);
    }

    // Validate form submission
    private boolean canFormSubmit() {
        // Validating the year combo field
        selectedYear = (String) yearCB.getSelectedItem();
        if(selectedYear != null && selectedYear.equalsIgnoreCase(DEFAULT_OPTION_STRING)) return false;

        return true;
    }

    // Update the selected year label text
    private void updateSelectedYearText(String text){
        if(text != null) selectedYearLabel.setText("<html>Selected Year: <strong>" + text + "</strong></html>"); // color='red'
        else selectedYearLabel.setText("<html>Selected Year: <strong>" + selectedYear + "</strong></html>"); // color='red'
    }

    // Fetching results based on semesters and selected year (gpa is optional)
    private void fetchSemester1Results(boolean isSameYear){
        gpaTablePanel.simulate("semester 1", "start", isSameYear);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException ignore) {}

        Map<String, List<String>> data = studentTabListener.findAllModules(student.getId(), "semester 1", selectedYear);
        gpaTablePanel.load(data, "semester 1");

        gpaTablePanel.simulate("semester 1", "stop", isSameYear);
    }

    private void fetchSemester2Results(boolean isSameYear){
        gpaTablePanel.simulate("semester 2", "start", isSameYear);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException ignore) {}

        Map<String, List<String>> data = studentTabListener.findAllModules(student.getId(), "semester 2", selectedYear);
        gpaTablePanel.load(data, "semester 2");

        gpaTablePanel.simulate("semester 2", "stop", isSameYear);
    }

    private void fetchSemester3Results(boolean isSameYear){
        gpaTablePanel.simulate("semester 3", "start", isSameYear);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException ignore) {}

        Map<String, List<String>> data = studentTabListener.findAllModules(student.getId(), "semester 3", selectedYear);
        gpaTablePanel.load(data, "semester 3");

        gpaTablePanel.simulate("semester 3", "stop", isSameYear);
    }

    // Clear result tables
    private void clearTables(){
        gpaTablePanel.clear();
    }

    // This method will be called to enable/disable the button when thread starts and stop
    private void enableButtons(boolean value){
        proceedBtn.setEnabled(value);
        resetBtn.setEnabled(value);
    }

    // Method to validate and display inform the user if they can view information or not
    private boolean infoIsNotPresentToView(String currentAcademicYear){
        // Get all school years simulated so far
        List<String> yearsList = new ArrayList<>(List.of(SimulationUtils.getAllSchoolYears()));

        // Remove the current school year
        yearsList.remove(SimulationUtils.getFormattedAcademicYear());

        // Search for the selected year in the years list after removing the current school year
        boolean yearFound = yearsList.contains(selectedYear);

        /*System.out.println("All Existing Years: " + yearsList);
        System.out.println("Current Year: " + selectedYear);
        System.out.println("Year Found: " + yearFound + "\n\n");*/

        // If no simulation was done prior invoking this method, inform the user accordingly...
        if(!yearFound && !selectedYear.endsWith(currentAcademicYear) && SimulationUtils.noSimulationTakenPlaceYet()) {
            showErrorMessage("Please note that no results were generated for semester 1 in the current school year!", "Request Error");
            return true;
        }

        return false;
    }
}
