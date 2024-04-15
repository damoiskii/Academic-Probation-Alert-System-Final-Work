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

import com.app.utils.IconUtils;
import com.app.view.listeners.BlurPaneListener;
import com.app.view.listeners.MenuBarListener;

import javax.swing.*;


public class MenuBar extends JMenuBar {
    private JMenu fileMenu, optionMenu;
    private JMenuItem exitItem, generateReportOptionItem, setDefaultGPAItem;

    // Dialogs
    private GenerateReportDialog generateReportDialog;

    // Listeners
    private MenuBarListener menuBarListener;
    private BlurPaneListener blurPaneListener;

    private volatile double gpa;

    public MenuBar(){
        super();

        initializeComponents();
        addComponentsToMenu();
        registerListeners();

        loadMenuItemsData();
    }

    // Loading all the available school years recorded in the system
    public void loadMenuItemsData(){
        generateReportDialog.loadData();
    }

    // This will be used in
    public void setGpa(double gpa) {
        this.gpa = gpa;
        generateReportDialog.setGpa(gpa);
    }

    // Set menubar listener
    public void setMenuBarListener(MenuBarListener menuBarListener) {
        this.menuBarListener = menuBarListener;
        generateReportDialog.setMenuBarListener(menuBarListener);
    }

    // Set blurred pane listener
    public void setBlurPaneListener(BlurPaneListener blurPaneListener) {
        this.blurPaneListener = blurPaneListener;
        generateReportDialog.setBlurPaneListener(blurPaneListener);
    }

    // Initialize the frame's components
    private void initializeComponents() {
        // Create sub menus
        fileMenu = new JMenu("File");
        optionMenu = new JMenu("Options");

        // Create sub menu items
        exitItem = new JMenuItem("Exit");
        setDefaultGPAItem = new JMenuItem("Set Default GPA");
        generateReportOptionItem = new JMenuItem("Generate Report");

        // Icons
        IconUtils.setIcon(exitItem, "exit");
        IconUtils.setIcon(setDefaultGPAItem, "set");
        IconUtils.setIcon(generateReportOptionItem, "generate");

        // Generate Report Form Dialog
        generateReportDialog = new GenerateReportDialog(null, "Generate Report");
    }

    // Adding the menus to menu bar
    private void addComponentsToMenu() {
        // Add menu items to menus
        fileMenu.add(exitItem);

        optionMenu.add(setDefaultGPAItem);
        optionMenu.add(generateReportOptionItem);

        // Add menus to menubar
        add(fileMenu);
        add(optionMenu);
    }

    // Registering listeners
    private void registerListeners() {
        // Exit item to close program
        exitItem.addActionListener(e -> {
            if(blurPaneListener != null) blurPaneListener.blurBackground();

            int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit this program?", "Confirm exit", JOptionPane.OK_CANCEL_OPTION);

            if(blurPaneListener != null) blurPaneListener.unBlurBackground();

            // If the choice is okay
            if(choice == JOptionPane.OK_OPTION){
                System.exit(0);
            }
        });

        // Allow the user to set a default GPA in the system -> storing this to prolog knowledge base...
        setDefaultGPAItem.addActionListener(e -> {
            if(blurPaneListener != null) blurPaneListener.blurBackground();

            String title = "<html>Default GPA is <strong style=\"font-weight: bold;\">" + String.format("%.2f", gpa) + "</html>";
            int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to change the default GPA value?", title, JOptionPane.OK_CANCEL_OPTION);

            // If the choice is okay
            if(choice == JOptionPane.OK_OPTION){
                if(menuBarListener == null) {
                    if(blurPaneListener != null) blurPaneListener.unBlurBackground();
                    return;
                }

                if(blurPaneListener != null) blurPaneListener.blurBackground();

                String input = JOptionPane.showInputDialog(null, "Please enter the new desired GPA value");

                if(input == null) {
                    if(blurPaneListener != null) blurPaneListener.unBlurBackground();
                    return;
                }

                // Validate to ensure that the user has entered a numerical value
                try{
                    double gpa = Double.parseDouble(input);

                    // Save the gpa value to the prolog file here...
                    Thread thread = new Thread(() -> {
                        boolean updated = menuBarListener.setNewGPA(gpa);
                        
                        if(blurPaneListener != null) blurPaneListener.blurBackground();

                        if(updated) JOptionPane.showMessageDialog(null, "You have successfully updated the system's default GPA!", "Default GPA Saved!", JOptionPane.INFORMATION_MESSAGE);
                        else JOptionPane.showMessageDialog(null, "There was an error trying to update the default GPA! Please try again later...", "Not Updated!", JOptionPane.ERROR_MESSAGE);
                        
                        if(blurPaneListener != null) blurPaneListener.unBlurBackground();
                    });

                    thread.setName("setting-gpa");
                    thread.start();
                }
                catch (NullPointerException | NumberFormatException ex){
                    JOptionPane.showMessageDialog(null, "Sorry, but only numerical values allowed here!", "Numbers Only", JOptionPane.ERROR_MESSAGE);
                }
            }

            if(blurPaneListener != null) blurPaneListener.unBlurBackground();
        });

        // Allow the user to generate a report
        generateReportOptionItem.addActionListener(e -> {
            if(blurPaneListener != null) blurPaneListener.blurBackground();

            int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to generate a report?", "Confirm action", JOptionPane.OK_CANCEL_OPTION);

            if(choice != JOptionPane.OK_OPTION) if(blurPaneListener != null) blurPaneListener.unBlurBackground();

            // If the choice is okay
            if(choice == JOptionPane.OK_OPTION){
                // If dialog already created but hidden
                if(generateReportDialog != null && !generateReportDialog.isVisible()) generateReportDialog.setVisible(true);
            }
        });
    }
}
