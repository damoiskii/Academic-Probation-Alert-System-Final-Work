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

package com.app.view.tabs;

import javax.swing.*;
import java.awt.*;


public class DashboardTabPanel extends JPanel {
    private CardLayout view;
    private JPanel panel, northPanel;

    private JButton generateReportBtn;


    public DashboardTabPanel(){
        super();

        settingPanelProperties();
        initializeComponents();
        addComponentsToPanels();
        registerListeners();

        focus();
    }

    // Initialize the frame's components
    private void initializeComponents() {
        // Layout
        view = new CardLayout();

        // Panels
        panel = new JPanel(view);
        panel.setBackground(Color.LIGHT_GRAY);

        northPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        // Button
        generateReportBtn = new JButton("Generate Report");
    }

    // Setting the panel properties
    private void settingPanelProperties() {
        setLayout(new BorderLayout());
        //setBackground(Color.LIGHT_GRAY);
    }

    // Adding the components to panel
    private void addComponentsToPanels() {
        northPanel.add(generateReportBtn);

        add(northPanel, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);
    }

    // Registering listeners
    private void registerListeners() {

    }

    private void focus(){

    }
}
