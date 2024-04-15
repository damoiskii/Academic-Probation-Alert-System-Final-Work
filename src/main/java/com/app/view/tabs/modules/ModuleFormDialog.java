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

package com.app.view.tabs.modules;


import com.app.model.Module;
import com.app.utils.BaseUtils;
import com.app.utils.IconUtils;
import com.app.view.customs.CTextField;
import com.app.view.listeners.BlurPaneListener;
import com.app.view.listeners.ModuleTabListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class ModuleFormDialog extends JDialog {
    private JComboBox<String> schoolCB;

    private JButton saveBtn, closeBtn, resetBtn;
    private JPanel panel, centerPanel, buttonPanel;

    private int FIELD_SIZE = 40;
    private final String DEFAULT_OPTION_STRING = "--------------------------------------------------------------------------------";
    //private String selectedSchool;
    private CTextField codeField, nameField, creditsField;

    private List<String> moduleFormDialogOption;


    private boolean updateRequest = false;
    private Module updateModule;

    // Listener
    private ModuleTabListener moduleTabListener;
    private BlurPaneListener blurPaneListener;


    public ModuleFormDialog(){
        super();

        focus();
    }

    public ModuleFormDialog(JFrame frame, String title){
        super(frame, title);

        settingWindowProperties();
        initializeComponents();
        addComponentsToPanels();
        addPanelsToWindow();
        registerListeners();

        loadData();

        focus();
    }

    public void setModuleFormDialogOption(List<String> moduleFormDialogOption) {
        this.moduleFormDialogOption = moduleFormDialogOption;
    }

    // Setup module tab listener
    public void setModuleTabListener(ModuleTabListener moduleTabListener) {
        this.moduleTabListener = moduleTabListener;
    }

    public void setBlurPaneListener(BlurPaneListener blurPaneListener) {
        this.blurPaneListener = blurPaneListener;
    }

    // Set the generated ID#
    public void setGeneratedCodeField(String code){
        if(codeField != null) {
            codeField.writingForeground();
            codeField.setText(code);

            // Ensure the field is disabled
            codeField.setEnabled(false);
        }
    }

    // Load dialog data...
    public void loadData() {
        if(moduleFormDialogOption == null) return;

        // Setup schools info
        if(schoolCB != null) {
            // Sorting data to give the combo boxes an organized look
            List<String> schools = new ArrayList<>(moduleFormDialogOption);
            Collections.sort(schools);

            // Add school to the schools set
            schoolCB.removeAllItems();
            schoolCB.addItem(DEFAULT_OPTION_STRING);
            schoolCB.setPrototypeDisplayValue(DEFAULT_OPTION_STRING);

            for(String school: schools){
                schoolCB.addItem(school);
            }
        }
    }

    // Initialize the frame's components
    private void initializeComponents() {
        // Panels
        panel = new JPanel(new BorderLayout());
        centerPanel = new JPanel(new GridBagLayout());
        buttonPanel = new JPanel(new FlowLayout());

        // Text field
        codeField = new CTextField("CMP1020", FIELD_SIZE);
        nameField = new CTextField("Enter module name here...", FIELD_SIZE);
        creditsField = new CTextField("Enter module credits here...", FIELD_SIZE);

        // Combo box
        schoolCB = new JComboBox<>(new String[]{DEFAULT_OPTION_STRING});

        // Buttons
        saveBtn = new JButton("Save");
        closeBtn = new JButton("Close");
        resetBtn = new JButton("Reset");

        // Tooltips
        saveBtn.setToolTipText("Click to save the student info");
        closeBtn.setToolTipText("Click to close student form dialog");
        resetBtn.setToolTipText("Click to reset the student form");

        codeField.setToolTipText("Generated code field is disabled");
        nameField.setToolTipText("Enter module name");
        creditsField.setToolTipText("Enter amount of credits");
        schoolCB.setToolTipText("Select school module is apart of");

        // Visibility
        saveBtn.setVisible(false);

        // Disable
        codeField.setEnabled(false);

        // Icons
        IconUtils.setIcon(closeBtn, "close");
        IconUtils.setIcon(saveBtn, "save");
        IconUtils.setIcon(resetBtn, "refresh");
    }

    // Setting the window properties
    private void settingWindowProperties() {
        setLayout(new BorderLayout());
        setSize(new Dimension(550, 250));
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
                closeBtn.doClick();
            }
        });
    }

    // Adding the components to panel
    private void addComponentsToPanels() {
        buttonPanel.add(saveBtn);
        buttonPanel.add(resetBtn);
        buttonPanel.add(closeBtn);

        buildCenterConstraintsPanel();

        panel.add(centerPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
    }

    // Adding the panel to the window
    private void addPanelsToWindow() {
        add(panel, BorderLayout.CENTER);
    }

    // Registering listeners
    private void registerListeners() {
        // Name text field
        nameField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                saveBtnVisibility();
            }
        });

        // Credits text field
        creditsField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                saveBtnVisibility();
            }
        });

        // School Checkbox
        schoolCB.addActionListener(e -> {
            focus();

            //selectedSchool = (String) schoolCB.getSelectedItem();
            saveBtnVisibility();
        });

        // Reset Button
        resetBtn.addActionListener(e -> {
            focus();
            reset();
        });

        // Close Button
        closeBtn.addActionListener(e -> {
            if(blurPaneListener != null) blurPaneListener.unBlurBackground();

            focus();
            setVisible(false);

            setTitle("Add Module Form");
            saveBtn.setText("Save");
            updateRequest = false;

            reset();
        });

        // Save Button
        saveBtn.addActionListener(e -> {
            focus();

            // If the form can submit
            if(canFormSubmit()){
                int credits;

                // If the value entered is not a number
                try {
                    credits = Integer.parseInt(creditsField.getText().trim());
                }
                catch (NumberFormatException ignore) {
                    setAlwaysOnTop(false);
                    showErrorMessage("Please enter a valid number for the amount of credits that the module carry!", "Form Error");
                    setAlwaysOnTop(true);

                    return;
                }

                // Pass the created module to be saved in the database
                if(moduleTabListener != null) {
                    setAlwaysOnTop(false);

                    if(updateRequest){
                        String oldName = updateModule.getName();

                        updateModule.setCode(codeField.getText().trim());
                        updateModule.setName(BaseUtils.titleCase(nameField.getText().trim(), true));
                        updateModule.setCredits(credits);

                        // Send the module data to be updated in the database
                        boolean updated = moduleTabListener.updateModule(updateModule, oldName);

                        // If the module was updated
                        if(updated){
                            closeBtn.doClick();
                            JOptionPane.showMessageDialog(null, "Module was updated successfully!", "Modification Success", JOptionPane.INFORMATION_MESSAGE);
                        }
                        else{
                            // Reset the changed name to default
                            updateModule.setName(oldName);
                        }
                    }
                    else{
                        // Create module object
                        Module module = new Module();

                        module.setCode(codeField.getText().trim());
                        module.setName(BaseUtils.titleCase(nameField.getText().trim(), true));
                        module.setCredits(credits);

                        // Send the module data to be saved in the database
                        boolean added = moduleTabListener.addModule(module);

                        // If the student was added
                        if(added){
                            closeBtn.doClick();
                            JOptionPane.showMessageDialog(null, "Module was added successfully!", "Creation Success", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }

                    setAlwaysOnTop(true);
                }
            }
            else{
                setAlwaysOnTop(false);
                showErrorMessage("Please fill out all required fields!", "Form Error");
                setAlwaysOnTop(true);
            }
        });
    }

    private void focus(){
        if(panel != null){
            panel.setFocusable(true);
            panel.grabFocus();
        }
    }

    // Reset values
    private void reset(){
        if(updateRequest){
            nameField.setText(updateModule.getName());
            codeField.setText(updateModule.getCode());
            creditsField.setText(String.valueOf(updateModule.getCredits()));
        }
        else{
            nameField.reset();
            creditsField.reset();
            codeField.reset();
            schoolCB.setSelectedIndex(0);

            //selectedSchool = null;
        }
    }

    // Build constraints panel
    private void buildCenterConstraintsPanel(){
        GridBagConstraints c = new GridBagConstraints();

        c.insets = new Insets(5, 5, 0, 0);

        // First row
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.FIRST_LINE_END;
        centerPanel.add(new JLabel("<html>Generated Code <font color='red'>*</font></html>"), c);

        c.gridx = 1;
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        centerPanel.add(codeField, c);


        // Second row
        c.gridx = 0;
        c.gridy = 1;
        c.anchor = GridBagConstraints.FIRST_LINE_END;
        centerPanel.add(new JLabel("<html>Name <font color='red'>*</font></html>"), c);

        c.gridx = 1;
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        centerPanel.add(nameField, c);


        // Third row
        c.gridx = 0;
        c.gridy = 2;
        c.anchor = GridBagConstraints.FIRST_LINE_END;
        centerPanel.add(new JLabel("<html>Credits <font color='red'>*</font></html>"), c);

        c.gridx = 1;
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        centerPanel.add(creditsField, c);
    }

    /* Utility Methods */
    private void showErrorMessage(String message, String title){
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);
    }

    // Validate form submission
    private boolean canFormSubmit() {
        // Validating the code field
        if(codeField.getText().trim().length() > 0 && !codeField.isTextsDifferent()) return false;

        // Validating the name field
        if(nameField.getText().trim().length() > 0 && !nameField.isTextsDifferent()) return false;

        // Validating the credits field
        if((creditsField.getText().trim().length() > 0 && !creditsField.isTextsDifferent())) return false;

        return true;
    }


    // Determine the save button visibility
    private void saveBtnVisibility(){
        boolean idGoodToGo = (codeField.getText().trim().length() > 0 && codeField.isTextsDifferent());
        boolean nameGoodToGo = (nameField.getText().trim().length() > 0 && nameField.isTextsDifferent());
        boolean emailGoodToGo = (creditsField.getText().trim().length() > 0 && creditsField.isTextsDifferent());
        //boolean schoolGoodToGo = (selectedSchool != null && !selectedSchool.equalsIgnoreCase(DEFAULT_OPTION_STRING));

        boolean visible = (idGoodToGo && nameGoodToGo && emailGoodToGo);

        // Set the visibility of the save button accordingly
        saveBtn.setVisible(visible);
    }


    // This method will update the form to show student information to update
    public void turnIntoUpdateForm(Module module){
        setTitle("Update Module Form");
        saveBtn.setText("Update");
        updateRequest = true;
        updateModule = module;

        nameField.writingForeground();
        creditsField.writingForeground();

        // Update the fields
        codeField.setText(module.getCode());
        nameField.setText(module.getName());
        creditsField.setText(String.valueOf(module.getCredits()));
    }
}
