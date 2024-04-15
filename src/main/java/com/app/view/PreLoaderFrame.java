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

import com.app.utils.BaseUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class PreLoaderFrame extends JFrame {
    //private final ApplicationShutdownManager applicationShutdownManager;
    private JProgressBar progressBar;
    private JPanel panel;

    public PreLoaderFrame() {
        super(BaseUtils.APP_NAME);
        //this.applicationShutdownManager = applicationShutdownManager;

        settingWindowProperties();
        initializeComponents();
        addComponentsToPanels();
        addPanelsToWindow();
        registerListeners();
    }

    // Initialize the frame's components
    private void initializeComponents() {
        panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(320, 15));

        progressBar = new JProgressBar(0, 100);
        progressBar.setValue(0);
        progressBar.setStringPainted(true);
    }

    // Setting the window properties
    private void settingWindowProperties() {
        setLayout(new FlowLayout(FlowLayout.CENTER));
        setSize(new Dimension(350, 80));
        setResizable(false);
        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        interceptClosingWindowEvent();

        //Image icon = FileUtils.getImage("icon.png");
        //setIconImage(icon);
    }

    // This method will be used to intercept the closing window event to log a message / disconnect from databases
    private void interceptClosingWindowEvent(){
        // To clean up the code by killing every other process such as database, etc.
        // We will intercept the window closing event
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose(); // To quit the application after clean up...
                System.gc(); // Call garbage collector

                //applicationShutdownManager.initiateShutdown();
            }
        });
    }

    // Adding the components to panel
    private void addComponentsToPanels() {
        panel.add(progressBar, BorderLayout.CENTER);
    }

    // Adding the panel to the window
    private void addPanelsToWindow() {
        add(panel);
    }

    // Registering listeners
    private void registerListeners() {

    }

    // Source: https://www.geeksforgeeks.org/java-swing-jprogressbar/
    public void fill() {
        int i = 0;
        try {
            while (i <= 100) {
                // Set text according to the level to which the bar is filled
                if (i < 14){
                    progressBar.setString("Application starting up...");
                }
                else if (i >= 15 && i < 70){
                    progressBar.setString("Wait for some time...");
                }
                else if (i > 70 && i < 95){
                    progressBar.setString("Almost finish loading...");
                }
                else if (i >= 95){
                    progressBar.setString("Finalizing...");
                }

                // Fill the progress bar
                progressBar.setValue(i + 5);

                // Delay the thread
                Thread.sleep(1000);
                i += 10;
            }

            progressBar.setString("Done!");
        }
        catch (Exception e) {}
    }
}
