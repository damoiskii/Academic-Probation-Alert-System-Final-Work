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


import com.app.model.Module;
import com.app.model.Probation;
import com.app.model.Student;
import com.app.view.customs.CProgressBar;
import com.app.view.listeners.*;
import com.app.view.tabs.modules.ModuleTabPanel;
import com.app.view.tabs.probation.ProbationTabPanel;
import com.app.view.tabs.simulation.SimulationTabPanel;
import com.app.view.tabs.students.StudentTabPanel;

import javax.swing.*;
import java.awt.*;
import java.util.List;


public class MainPanel extends JPanel {
    private JPanel panel, southPanel;
    private JLabel infoLabel, lastUpdateInfoLabel, probationInfoLabel;

    private JTabbedPane tabbedPane;
    private CProgressBar progressBar;

    // Tab Panels
    private StudentTabPanel studentTabPanel;
    private ModuleTabPanel moduleTabPanel;
    private ProbationTabPanel probationTabPanel;
    private SimulationTabPanel simulationTabPanel;

    private int studentCount = 0, moduleCount = 0, probationCount = 0;


    public MainPanel(){
        super();

        settingPanelProperties();
        initializeComponents();
        addComponentsToPanels();
        registerListeners();

        focus();
    }

    // Set Student Tab Listener
    public void setStudentTabListener(StudentTabListener studentTabListener){
        studentTabPanel.setStudentTabListener(studentTabListener);
    }

    // Set Module Tab Listener
    public void setModuleTabListener(ModuleTabListener moduleTabListener){
        moduleTabPanel.setModuleTabListener(moduleTabListener);
    }

    // Set Simulation Tab Listener
    public void setSimulationTabListener(SimulationTabListener simulationTabListener){
        simulationTabPanel.setSimulationTabListener(simulationTabListener);
    }

    // Set Lazy Loader Listener
    public void setLoadLazyListener(LoadLazyListener loadLazyListener) {
        moduleTabPanel.setLoadLazyListener(loadLazyListener);
    }

    // Set Blur Pane Listener
    public void setBlurPaneListener(BlurPaneListener blurPaneListener) {
        studentTabPanel.setBlurPaneListener(blurPaneListener);
        moduleTabPanel.setBlurPaneListener(blurPaneListener);
        probationTabPanel.setBlurPaneListener(blurPaneListener);
    }

    // Set Probation Tab Listener
    public void setProbationTabListener(ProbationTabListener probationTabListener){
        probationTabPanel.setProbationTabListener(probationTabListener);
    }

    // Load student table data...
    public void loadTableData(List<Student> students, List<Module> modules, List<Probation> probations, String action) {
        if(students != null) {
            studentCount = students.size();
            infoLabel.setText("Students found (" + studentCount + ")");
            studentTabPanel.loadTableData(students, action);
        }

        if(modules != null) {
            moduleCount = modules.size();
            moduleTabPanel.loadTableData(modules, action);
        }

        if(probations != null) {
            probationCount = probations.size();
            probationTabPanel.loadTableData(probations, action);
        }
    }

    // Generate a student id number in the background while the program loads or closes the form
    public void generateStudentIDNumber(){
        studentTabPanel.generateStudentIDNumber();
    }

    // Generate a module code in the background while the program loads or closes the form
    public void generateModuleCode(){
        moduleTabPanel.generateModuleCode();
    }

    // Initialize the frame's components
    private void initializeComponents() {
        // Panels
        panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        southPanel = new JPanel(new BorderLayout());

        studentTabPanel = new StudentTabPanel();
        moduleTabPanel = new ModuleTabPanel();
        probationTabPanel = new ProbationTabPanel();
        simulationTabPanel = new SimulationTabPanel();

        // Label
        infoLabel = new JLabel("Students found (0)", SwingConstants.LEFT);
        infoLabel.setFont(new Font("Arial", Font.BOLD, 15));

        lastUpdateInfoLabel = new JLabel("", SwingConstants.RIGHT);
        lastUpdateInfoLabel.setFont(new Font("Arial", Font.BOLD, 12));

        probationInfoLabel = new JLabel("Students found on probation (0)", SwingConstants.LEFT);
        probationInfoLabel.setFont(new Font("Arial", Font.BOLD, 15));

        lastUpdateInfoLabel.setVisible(false);
        probationInfoLabel.setVisible(false);

        // Tabbed Pane
        tabbedPane = new JTabbedPane();

        // Adding tabs to the tabbed pane
        tabbedPane.addTab("Students", studentTabPanel);
        tabbedPane.addTab("Modules", moduleTabPanel);
        tabbedPane.addTab("Probation List", probationTabPanel);
        tabbedPane.addTab("Semester Simulator", simulationTabPanel);

        // Progress Bar
        progressBar = new CProgressBar();
    }

    // Setting the panel properties
    private void settingPanelProperties() {
        setLayout(new BorderLayout());

        //Image icon = FileUtils.getImage("icon.png");
        //setIconImage(icon);
    }

    // Adding the components to panel
    private void addComponentsToPanels() {
        JPanel subSouthPanel1 = new JPanel(new FlowLayout());
        JPanel subSouthPanel2 = new JPanel(new FlowLayout());

        subSouthPanel1.add(progressBar);
        subSouthPanel1.add(lastUpdateInfoLabel);

        subSouthPanel2.add(infoLabel);
        subSouthPanel2.add(probationInfoLabel);

        southPanel.add(subSouthPanel2, BorderLayout.WEST);
        // southPanel.add(infoLabel, BorderLayout.WEST);
        // southPanel.add(progressBar, BorderLayout.EAST);
        southPanel.add(subSouthPanel1, BorderLayout.EAST);

        add(tabbedPane, BorderLayout.CENTER);
        add(southPanel, BorderLayout.SOUTH);
    }

    // Registering listeners
    private void registerListeners() {
        // Add Change Listener to Tabbed Pane
        tabbedPane.addChangeListener(e -> {
            int index = tabbedPane.getSelectedIndex();

            // Since message tab is the second in the tab list, then index 1 is the message tab...
            // If the message tab has been selected then load all messages...
            if(index == 0){
                lastUpdateInfoLabel.setVisible(false);
                probationInfoLabel.setVisible(false);

                infoLabel.setText("Students found (" + studentCount + ")");
                infoLabel.setVisible(true);

                studentTabPanel.grabFocus();
                studentTabPanel.refresh("Loading data", 1000);
            }
            else if(index == 1){
                lastUpdateInfoLabel.setVisible(false);
                probationInfoLabel.setVisible(false);

                infoLabel.setText("Modules found (" + moduleCount + ")");
                infoLabel.setVisible(true);
                moduleTabPanel.grabFocus();
                moduleTabPanel.refresh("Loading data", 1000);
            }
            else if(index == 2){
                infoLabel.setVisible(false);
                lastUpdateInfoLabel.setVisible(true);

                probationInfoLabel.setText("Students found on probation (" + probationCount + ")");
                probationInfoLabel.setVisible(true);

                probationTabPanel.grabFocus();
            }
            else if(index == 3){
                lastUpdateInfoLabel.setVisible(false);
                probationInfoLabel.setVisible(false);

                infoLabel.setText("");
                simulationTabPanel.grabFocus();
            }
        });
    }

    private void focus(){
        if(panel != null){
            panel.setFocusable(true);
            panel.grabFocus();
        }
    }

    // Updating the student information table
    public void updateStudentInfoLabel(){
        studentCount = studentTabPanel.rowCount();
        infoLabel.setText("Students found (" + studentCount + ")");
    }

    public void updateStudentInfoLabel(int amount){
        studentCount = amount;
        infoLabel.setText("Students found (" + studentCount + ")");
    }

    // Updating the module information table
    public void updateModuleInfoLabel(){
        moduleCount = moduleTabPanel.rowCount();
        infoLabel.setText("Modules found (" + moduleCount + ")");
    }

    public void updateModuleInfoLabel(int amount){
        moduleCount = amount;
        infoLabel.setText("Modules found (" + moduleCount + ")");
    }

    // Updating the probation information table
    public void updateProbationInfoLabel(){
        probationCount = probationTabPanel.rowCount();
        probationInfoLabel.setText("Students found on probation (" + probationCount + ")");
    }

    public void updateProbationInfoLabel(int amount){
        probationCount = amount;
        probationInfoLabel.setText("Students found on probation (" + probationCount + ")");
    }

    // Updating the table with the new student data added to the system
    public void addData(Student student){
        studentTabPanel.addData(student);
    }

    public void updateData(Student student){
        studentTabPanel.updateData(student);
    }

    // Updating the table with the new module data added to the system
    public void addData(Module module){
        moduleTabPanel.addData(module);
    }

    public void updateData(Module module){
        moduleTabPanel.updateData(module);
    }

    // This method will be called whenever a simulation is done
    public void refreshTableData(List<Student> students, List<Module> modules) {
        // Update students tab table
        if(students != null) {
            studentCount = students.size();
            studentTabPanel.loadTableData(students, "refresh");
        }

        // Update modules tab table
        if(modules != null) {
            moduleCount = modules.size();
            moduleTabPanel.loadTableData(modules, "refresh");
        }
    }

    // To hide or display the progress
    public void showProgressBar(String text, boolean show) {
        if(show){
            progressBar.run(text);
        }
        else{
            progressBar.stop();
        }
    }

    // Auto Checker Helpers
    public void updateProbationListFooterText(String text){
        lastUpdateInfoLabel.setText("Last updated: " + text);
    }

    public void refreshProbationList(){
        probationTabPanel.refreshList();
    }
}
