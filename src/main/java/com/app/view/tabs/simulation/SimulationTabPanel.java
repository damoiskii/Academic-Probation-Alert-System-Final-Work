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

package com.app.view.tabs.simulation;

import com.app.task.UpdateLabelTask;
import com.app.utils.IconUtils;
import com.app.utils.SimulationUtils;
import com.app.view.listeners.SimulationTabListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;


public class SimulationTabPanel extends JPanel {
    private static final Logger LOGGER = LoggerFactory.getLogger(SimulationTabPanel.class);

    private JPanel northPanel, centerPanel, southPanel;

    private JButton runSimulateBtn, resetSimulationBtn;
    private JLabel simulatingLabel, currentAcademicYearLabel, currentSemesterLabel, progressBarSimLabel;

    private UpdateLabelTask updateLabelTask;

    private SimulationTabListener simulationTabListener;


    public SimulationTabPanel(){
        super();

        settingPanelProperties();
        initializeComponents();
        addComponentsToPanels();
        registerListeners();

        loadSimulationInfo();

        focus();
    }

    // Set listener
    public void setSimulationTabListener(SimulationTabListener simulationTabListener) {
        this.simulationTabListener = simulationTabListener;
    }

    // Loading simulation information
    public void loadSimulationInfo(){
        updateSemesterLabel(SimulationUtils.getSemester());
        updateAcademicYearLabel(SimulationUtils.getFormattedAcademicYear());
    }

    // Initialize the frame's components
    private void initializeComponents() {
        // Labels
        simulatingLabel = new JLabel("Simulator", SwingConstants.CENTER);
        currentSemesterLabel = new JLabel("Semester 1");
        currentAcademicYearLabel = new JLabel("Academic Year 2023-2024");
        progressBarSimLabel = new JLabel("", SwingConstants.CENTER);

        // Panels
        northPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        centerPanel = new JPanel(new GridBagLayout());
        southPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        // Borders
        northPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0,0));

        // Button
        runSimulateBtn = new JButton("Simulate Semester");
        resetSimulationBtn = new JButton("Reset Simulation");

        // Visibility
        simulatingLabel.setVisible(false);

        // Tooltips
        runSimulateBtn.setToolTipText("Click to simulate semester");
        resetSimulationBtn.setToolTipText("Click to reset the whole simulation to default");

        // Icons
        IconUtils.setIcon(runSimulateBtn, "run");
        IconUtils.setIcon(resetSimulationBtn, "refresh");

        // Fonts
        simulatingLabel.setFont(new Font("Arial", Font.BOLD, 16));
        currentSemesterLabel.setFont(new Font("Arial", Font.BOLD, 40));
        currentAcademicYearLabel.setFont(new Font("Arial", Font.BOLD, 80));
        progressBarSimLabel.setFont(new Font("Arial", Font.ITALIC, 25));
    }

    // Setting the panel properties
    private void settingPanelProperties() {
        setLayout(new BorderLayout());
        //setBackground(Color.GRAY);
    }

    // Adding the components to panel
    private void addComponentsToPanels() {
        //JPanel subNorthPanel2 = new JPanel(new FlowLayout());
        //JPanel subNorthPanel3 = new JPanel(new FlowLayout());

        // North
        northPanel.add(simulatingLabel);

        // Center
        buildCenterConstraintsPanel();

        // South
        southPanel.add(runSimulateBtn);
        southPanel.add(resetSimulationBtn);


        add(northPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(southPanel, BorderLayout.SOUTH);
    }

    // Registering listeners
    private void registerListeners() {
        // Run Simulation Button
        runSimulateBtn.addActionListener(e -> {
            focus();

            Thread thread = new Thread(() -> {
                LOGGER.info("Simulator Running...");
                updateInfoLabel("Simulating");
                simulatingLabel.setVisible(true);

                // Disabling the buttons that may interfere will operations
                enableButtons(false);

                /* Simulating what is taking place in the semester... */
                simulationEvents();

                // This will load any updated/new data to the relevant pages
                if(simulationTabListener != null) simulationTabListener.refreshSystem();

                simulatingLabel.setVisible(false);
                updateInfoLabel("Simulator");

                // Enabling the buttons that trigger operations
                enableButtons(true);
                LOGGER.info("Simulator Completed!\n");
            });

            thread.setName("simulator");
            thread.start();
        });

        // Rest Simulation Button
        resetSimulationBtn.addActionListener(e -> {
            focus();

            Thread thread = new Thread(() -> {
                LOGGER.info("Simulator Resetter Running...");
                updateInfoLabel("Resetting");
                simulatingLabel.setVisible(true);
                progressBarSimLabel.setText("");

                // Disabling the buttons that may interfere will operations
                enableButtons(false);

                // Also remove all modules/details from students in the database...
                if(simulationTabListener != null) simulationTabListener.resetSimulation();

                try {
                    Thread.sleep(5000);
                } catch (InterruptedException ignore) {}

                SimulationUtils.resetSimulation();
                loadSimulationInfo();

                simulatingLabel.setVisible(false);
                updateInfoLabel("Simulator");

                // Enabling the buttons that trigger operations
                enableButtons(true);
                LOGGER.info("Simulator Resetter Completed!\n");
            });

            thread.setName("simulator");
            thread.start();
        });
    }

    private void focus(){
        if(northPanel != null){
            northPanel.setFocusable(true);
            northPanel.grabFocus();
        }
    }

    /* Utility Methods */
    // Build constraints panel
    private void buildCenterConstraintsPanel(){
        GridBagConstraints c = new GridBagConstraints();

        c.insets = new Insets(5, 5, 0, 0);
        c.fill = GridBagConstraints.CENTER;

        // First row
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.FIRST_LINE_END;
        centerPanel.add(currentAcademicYearLabel, c);


        // Second row
        c.gridx = 0;
        c.gridy = 1;
        c.anchor = GridBagConstraints.FIRST_LINE_END;
        centerPanel.add(currentSemesterLabel, c);


        // Third row
        c.gridx = 0;
        c.gridy = 2;
        c.insets.top = 25;
        c.anchor = GridBagConstraints.CENTER;
        centerPanel.add(progressBarSimLabel, c);
    }

    // Updating the labels
    private void updateSemesterLabel(String text){
        currentSemesterLabel.setText(text);
    }

    private void updateAcademicYearLabel(String text){
        currentAcademicYearLabel.setText("Academic Year " + text);
    }

    // Update the top label to tell the user that the simulation is running...
    public void updateInfoLabel(String text){
        simulatingLabel.setText(text);

        if(updateLabelTask == null) updateLabelTask = new UpdateLabelTask();

        if(updateLabelTask.isRunning() || text.length() == 0) updateLabelTask.stop();

        if(!text.equalsIgnoreCase("Simulator")) updateLabelTask.run(simulatingLabel);
    }

    // This method is responsible for the simulation events
    private void simulationEvents(){
        final int SLEEP_FOR = 1250;

        // Module selection portal open
        progressBarSimLabel.setText("Module selection portal open");
        try {
            Thread.sleep(SLEEP_FOR);
        } catch (InterruptedException ignore) {}


        // Students picking modules
        progressBarSimLabel.setText("Students selecting modules");

        // Add 3-7 modules to students and save them to the database... adding modules that have not been done nor passed as yet
        if(simulationTabListener != null) simulationTabListener.moduleSelection();

        try {
            Thread.sleep(SLEEP_FOR);
        } catch (InterruptedException ignore) {}


        // Semester starts
        progressBarSimLabel.setText("Semester begins");
        try {
            Thread.sleep(SLEEP_FOR);
        } catch (InterruptedException ignore) {}


        // Students doing course work
        progressBarSimLabel.setText("Students doing coursework");
        try {
            Thread.sleep(SLEEP_FOR);
        } catch (InterruptedException ignore) {}


        // Students doing mid semester tests
        progressBarSimLabel.setText("Mid semester test begins");
        try {
            Thread.sleep(SLEEP_FOR);
        } catch (InterruptedException ignore) {}


        // Students doing course work cont.
        progressBarSimLabel.setText("Ongoing coursework");
        try {
            Thread.sleep(SLEEP_FOR);
        } catch (InterruptedException ignore) {}


        // Students now in the dedicated study week
        progressBarSimLabel.setText("Now in study week");
        try {
            Thread.sleep(SLEEP_FOR);
        } catch (InterruptedException ignore) {}


        // Students doing final examinations
        progressBarSimLabel.setText("Final examinations begins");
        try {
            Thread.sleep(SLEEP_FOR);
        } catch (InterruptedException ignore) {}

        progressBarSimLabel.setText("Final examinations ends");
        try {
            Thread.sleep(SLEEP_FOR);
        } catch (InterruptedException ignore) {}


        // Modules results are out for all students
        progressBarSimLabel.setText("Module results are now available");

        // Set random results for selected modules by students
        if(simulationTabListener != null) simulationTabListener.moduleResults();

        try {
            Thread.sleep(SLEEP_FOR);
        } catch (InterruptedException ignore) {}


        // Progressing to the next semester/school year
        progressBarSimLabel.setText("Progressing to next semester");
        try {
            Thread.sleep(SLEEP_FOR);
        } catch (InterruptedException ignore) {}

        SimulationUtils.semesterProgression();
        loadSimulationInfo();

        // System.out.println("Current Semester after Progression: " +  SimulationUtils.getSemester() + " - Year: " + SimulationUtils.getFormattedAcademicYear());

        progressBarSimLabel.setText("");
    }

    // This method will be called to enable/disable the button when thread starts and stop
    private void enableButtons(boolean value){
        runSimulateBtn.setEnabled(value);
        resetSimulationBtn.setEnabled(value);
    }
}
