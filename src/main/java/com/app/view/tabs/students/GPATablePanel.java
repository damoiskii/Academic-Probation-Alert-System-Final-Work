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

import com.app.utils.GPAResultsUtils;
import com.app.view.tabs.students.gpa_tables.SemesterResultsTablePanel;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;


public class GPATablePanel extends JPanel {
    private JLabel selectedYearCumulativeLabel;
    private JPanel basePanel;
    private SemesterResultsTablePanel semesterOneTablePanel, semesterTwoTablePanel, semesterThreeTablePanel;


    public GPATablePanel(){
        super();

        settingPanelProperties();
        initializeComponents();
        addComponentsToPanels();
        registerListeners();

        focus();
    }

    // Initialize the frame's components
    private void initializeComponents() {
        // Labels
        selectedYearCumulativeLabel = new JLabel("Cumulative GPA For the Year", SwingConstants.CENTER);

        // Set font of the label
        selectedYearCumulativeLabel.setFont(new Font("Arial", Font.ITALIC, 15));

        // Panels
        //basePanel = new JPanel(new GridLayout(3, 1));
        basePanel = new JPanel(new GridLayout(0, 1)); // Temporarily initializing the panel here... but building it dynamically further down

        semesterOneTablePanel = new SemesterResultsTablePanel();
        semesterTwoTablePanel = new SemesterResultsTablePanel();
        semesterThreeTablePanel = new SemesterResultsTablePanel();

        // Set header text of the tables
        semesterOneTablePanel.setHeaderNoteText("Semester 1");
        semesterTwoTablePanel.setHeaderNoteText("Semester 2");
        semesterThreeTablePanel.setHeaderNoteText("Semester 3");
    }

    // Setting the panel properties
    private void settingPanelProperties() {
        setLayout(new BorderLayout());
        //setBackground(Color.GRAY);
    }

    // Adding the components to panel
    private void addComponentsToPanels() {
        // Base Panel

        add(basePanel, BorderLayout.CENTER);
        add(selectedYearCumulativeLabel, BorderLayout.SOUTH);
    }

    // Registering listeners
    private void registerListeners() {

    }

    private void focus(){
        if(basePanel != null){
            basePanel.setFocusable(true);
            basePanel.grabFocus();
        }
    }

    /* Utility Methods */
    // Loading data into the results table
    public void load(Map<String, List<String>> dataset, String tableName){
        if(tableName.equalsIgnoreCase("semester 1")){
            semesterOneTablePanel.loadTableData(dataset);
            semesterOneTablePanel.setFooterNoteText(GPAResultsUtils.s1totalCredit, GPAResultsUtils.s1totalPointsEarned, GPAResultsUtils.s1GPA);
        }
        else if(tableName.equalsIgnoreCase("semester 2")){
            semesterTwoTablePanel.loadTableData(dataset);
            semesterTwoTablePanel.setFooterNoteText(GPAResultsUtils.s2totalCredit, GPAResultsUtils.s2totalPointsEarned, GPAResultsUtils.s2GPA);
        }
        else if(tableName.equalsIgnoreCase("semester 3")){
            semesterThreeTablePanel.loadTableData(dataset);
            semesterThreeTablePanel.setFooterNoteText(GPAResultsUtils.s3totalCredit, GPAResultsUtils.s3totalPointsEarned, GPAResultsUtils.s3GPA);
        }

        updateCumulativeGPALabel();
    }

    // Clear all tables
    public void clear() {
        if(semesterOneTablePanel != null) {
            // Hide and Clear Table
            semesterOneTablePanel.setVisible(false);
            semesterOneTablePanel.clearTable();
        }

        if(semesterTwoTablePanel != null) {
            // Hide and Clear Table
            semesterTwoTablePanel.setVisible(false);
            semesterTwoTablePanel.clearTable();
        }

        if(semesterThreeTablePanel != null) {
            // Hide and Clear Table
            semesterThreeTablePanel.setVisible(false);
            semesterThreeTablePanel.clearTable();
        }

        // overallCumulativeLabel.setText("");
        selectedYearCumulativeLabel.setText("");

        GPAResultsUtils.clear();

        // Removing the component from the grid
        try{
            basePanel.remove(semesterOneTablePanel);
            basePanel.remove(semesterTwoTablePanel);
            basePanel.remove(semesterThreeTablePanel);
        }
        catch (Exception ignored){}

        refresh();
    }

    // Adding simulation notes
    public void simulate(String tableName, String action, boolean isSameYear) {
        // Semester One
        if(tableName.equalsIgnoreCase("semester 1")){
            // Adding component to the grid
            basePanel.add(semesterOneTablePanel);

            // Show Table
            semesterOneTablePanel.updateBorder(isSameYear);
            semesterOneTablePanel.setVisible(true);

            // Set actions
            if(action.equalsIgnoreCase("start")) semesterOneTablePanel.updateLabel("Calculating GPA Results");
            else semesterOneTablePanel.updateLabel("No data available...");

            refresh();
            return;
        }

        // Semester Two
        if(tableName.equalsIgnoreCase("semester 2")){
            // Adding component to the grid
            basePanel.add(semesterTwoTablePanel);

            // Show Table
            semesterTwoTablePanel.updateBorder(isSameYear);
            semesterTwoTablePanel.setVisible(true);

            // Set actions
            if(action.equalsIgnoreCase("start")) semesterTwoTablePanel.updateLabel("Calculating GPA Results");
            else semesterTwoTablePanel.updateLabel("No data available...");

            refresh();
            return;
        }

        // Semester Three
        if(tableName.equalsIgnoreCase("semester 3")){
            // Adding component to the grid
            basePanel.add(semesterThreeTablePanel);

            // Show Table
            semesterThreeTablePanel.updateBorder(isSameYear);
            semesterThreeTablePanel.setVisible(true);

            // Set actions
            if(action.equalsIgnoreCase("start")) semesterThreeTablePanel.updateLabel("Calculating GPA Results");
            else semesterThreeTablePanel.updateLabel("No data available...");
        }

        refresh();
    }

    // Update Cumulative GPA Text
    private void updateCumulativeGPALabel(){
        selectedYearCumulativeLabel.setText("<html><span>Cumulative GPA = "  + String.format("%.2f", GPAResultsUtils.cumulativeTotalPointsEarned) + " / " + GPAResultsUtils.cumulativeTotalCredits + " = <strong style='font-size:15px;'>" + String.format("%.2f", GPAResultsUtils.cumulativeGPATotalForSchoolYear) + "<strong/><span></html>");
    }

    // Top update the panels to show what has been added dynamically...
    private void refresh(){
        basePanel.repaint();
        basePanel.revalidate();

        repaint();
        revalidate();
    }
}
