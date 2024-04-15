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

package com.app.view.menubar;

import com.app.utils.FileUtils;
import com.app.utils.IconUtils;
import com.app.utils.SimulationUtils;
import com.app.view.customs.CTextField;
import com.app.view.listeners.BlurPaneListener;
import com.app.view.listeners.MenuBarListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;


public class GenerateReportDialog extends JDialog {
    private JLabel selectedYearLabel, desiredYearLabel, desiredGPALabel;
    private JComboBox<String> yearCB;

    private JPanel panel, northPanel, buttonPanel;
    private JButton proceedBtn, closeBtn, resetBtn;

    private int FIELD_SIZE = 20;
    private final String DEFAULT_OPTION_STRING = "---------------------------";
    private String selectedYear;
    private CTextField gpaField;


    // Listeners
    private BlurPaneListener blurPaneListener;
    private MenuBarListener menuBarListener;

    private volatile double gpa;

    // Custom panel
    private AnimatedPanel animatedPanel;


    public GenerateReportDialog(JFrame frame, String title){
        super(frame, title);

        settingWindowProperties();
        initializeComponents();
        addComponentsToPanels();
        addPanelsToWindow();
        registerListeners();

        focus();
    }

    // This will be used in
    public void setGpa(double gpa) {
        this.gpa = gpa;

        // Update GPA text field to reflect the default GPA value
        gpaField.setDefaultText("Using default GPA " + String.format("%.2f", gpa));
    }

    // Set menubar listener
    public void setMenuBarListener(MenuBarListener menuBarListener) {
        this.menuBarListener = menuBarListener;
    }

    // Setup dialog blur listener listener
    public void setBlurPaneListener(BlurPaneListener blurPaneListener) {
        this.blurPaneListener = blurPaneListener;
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
        desiredGPALabel = new JLabel("Select Desired GPA:", SwingConstants.CENTER);

        // Panels
        panel = new JPanel(new BorderLayout());
        northPanel = new JPanel(new BorderLayout());
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        // Animated Panel
        animatedPanel = new AnimatedPanel();

        // Text field
        gpaField = new CTextField("Enter GPA here...", FIELD_SIZE);

        // Combo box
        yearCB = new JComboBox<>(new String[]{DEFAULT_OPTION_STRING});

        // Buttons
        proceedBtn = new JButton("Proceed");
        closeBtn = new JButton("Close");
        resetBtn = new JButton("Reset");

        // Tooltips
        gpaField.setToolTipText("Enter GPA amount [optional]");
        yearCB.setToolTipText("Select a desired year to view overall gpa info");
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
                setTitle("Generate Report");
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
        // panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(animatedPanel, BorderLayout.CENTER);
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

            // Attempt to update gpa value if user from user input (if any)
            if(gpaField.isTextsDifferent()) {
                try{
                    gpa = Double.parseDouble(gpaField.getText());
                }
                catch (NumberFormatException ex) {
                    showErrorMessage("Sorry, only numerical values are allowed for the GPA field!", "Numbers allowed");
                    gpaField.reset();
                    return;
                }
            }

            // If the form can submit and proceed
            if(canFormSubmit()){
                // Should be inside the if block when form can submit
                Thread thread = new Thread(() -> {
                    if(menuBarListener != null) {
                        String currentAcademicYear = SimulationUtils.getAcademicYear();

                        // If no simulation was done prior invoking this method, inform the user accordingly...
                        if(infoIsNotPresentToView(currentAcademicYear)) return;

                        // Turn animation and notification on
                        enableButtons(false);
                        animatedPanel.animate(true);

                        // This is responsible for generating the soft copy of the report
                        // Query the database to get module details
                        boolean success = menuBarListener.generateReport(gpa, selectedYear);

                        // Turn animation and notification off
                        animatedPanel.animate(false);
                        enableButtons(true);

                        // Show success message
                        if(success){
                            setAlwaysOnTop(false);

                            // If generated, prompt user to open and view
                            int choice = JOptionPane.showConfirmDialog(null, "Report generated successfully! Do you wish to open this file?", "Report Generation Completed!", JOptionPane.YES_NO_OPTION);

                            // If the choice is yes
                            if(choice == JOptionPane.YES_OPTION){
                                // Opening generated file for user to view...
                                // animatedPanel.updateLabel("Opening document");

                                try {
                                    FileUtils.openDocument(FileUtils.GeneratedReportFilename);

                                    // Close the dialog
                                    closeBtn.doClick();
                                }
                                catch (Exception ex) {
                                    // Try opening the document using another way: Backup method...
                                    try {
                                        FileUtils.openDocumentBackup(FileUtils.GeneratedReportFilename);

                                        // Close the dialog
                                        closeBtn.doClick();
                                    }
                                    catch (Exception exx) {
                                        showErrorMessage("Sorry, there was an error trying to open the generated report! Please try again later...", "Document Not Opened!");
                                    }
                                }
                            }

                            setAlwaysOnTop(true);
                            animatedPanel.updateLabel("Generating report");
                        }
                        else{
                            // If an error was generated
                            showErrorMessage("Sorry, there was an error trying to generate the report! Please try again later...", "Report Generation Failed!");
                        }
                    }
                });

                thread.setName("gen-report");
                thread.start();
            }
            else{
                showErrorMessage("Please a desired school year!", "Form Error");
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
        gpaField.reset();
        yearCB.setSelectedIndex(0);

        animatedPanel.animate(false);
        enableButtons(true);
        animatedPanel.updateLabel("Generating report");

        selectedYear = null;
        clearTables();
    }

    // North Panel Builder
    private void buildNorthPanel(){
        JPanel np1 = new JPanel(new FlowLayout());
        JPanel np2 = new JPanel(new FlowLayout());
        JPanel np3 = new JPanel(new FlowLayout());
        JPanel np4 = new JPanel(new FlowLayout());

        // Combobox
        np1.add(desiredYearLabel);
        np1.add(yearCB);

        // Textfield
        np2.add(desiredGPALabel);
        np2.add(gpaField);

        // Label
        np3.add(selectedYearLabel);

        // Button
        np4.add(proceedBtn);

        // Adding components to the north panel
        // northPanel.add(np1, BorderLayout.NORTH);
        northPanel.add(np1, BorderLayout.WEST);
        northPanel.add(np2, BorderLayout.CENTER);
        northPanel.add(np3, BorderLayout.EAST);
        northPanel.add(np4, BorderLayout.SOUTH);
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

    // Clear result tables
    private void clearTables(){
        //gpaTablePanel.clear();
    }

    // This method will be called to enable/disable the button when thread starts and stop
    private void enableButtons(boolean value){
        proceedBtn.setEnabled(value);
        resetBtn.setEnabled(value);
    }

    // Method to validate and display inform the user if they can view information or not
    private boolean infoIsNotPresentToView(String currentAcademicYear){
        // Get all school years simulated so far
        java.util.List<String> yearsList = new ArrayList<>(List.of(SimulationUtils.getAllSchoolYears()));

        // Remove the current school year
        yearsList.remove(SimulationUtils.getFormattedAcademicYear());

        // Search for the selected year in the years list after removing the current school year
        boolean yearFound = yearsList.contains(selectedYear);

        // If no simulation was done prior invoking this method, inform the user accordingly...
        if(!yearFound && !selectedYear.endsWith(currentAcademicYear) && SimulationUtils.noSimulationTakenPlaceYet()) {
            showErrorMessage("Please note that no results were generated for semester 1 in the current school year!", "Request Error");
            return true;
        }

        return false;
    }
}
