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
import com.app.utils.BaseUtils;
import com.app.utils.IconUtils;
import com.app.utils.SchoolUtils;
import com.app.view.customs.CTextField;
import com.app.view.listeners.BlurPaneListener;
import com.app.view.listeners.StudentTabListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.*;


public class StudentFormDialog extends JDialog {
    private JLabel programmeLabel;
    private JComboBox<String> filterByFieldCB, schoolCB, programmeCB;

    private JButton saveBtn, closeBtn, resetBtn;

    private JPanel panel, centerPanel, buttonPanel;

    private int FIELD_SIZE = 40;
    private final String DEFAULT_FIELD_STRING = "-------------------";
    private final String DEFAULT_OPTION_STRING = "--------------------------------------------------------------------------------";
    private String selectedSchool, selectedProgramme;
    private CTextField idField, nameField, emailField;

    private List<String> studentFormDialogOption;


    private boolean updateRequest = false;
    private Student updateStudent;

    // Listener
    private StudentTabListener studentTabListener;
    private BlurPaneListener blurPaneListener;


    public StudentFormDialog(){
        super();

        focus();
    }

    public StudentFormDialog(JFrame frame, String title){
        super(frame, title);

        settingWindowProperties();
        initializeComponents();
        addComponentsToPanels();
        addPanelsToWindow();
        registerListeners();

        loadData();

        focus();
    }

    public void setStudentFormDialogOption(List<String> studentFormDialogOption) {
        this.studentFormDialogOption = studentFormDialogOption;
    }

    // Setup student tab listener
    public void setStudentTabListener(StudentTabListener studentTabListener) {
        this.studentTabListener = studentTabListener;
    }

    public void setBlurPaneListener(BlurPaneListener blurPaneListener) {
        this.blurPaneListener = blurPaneListener;
    }

    // Set the generated ID#
    public void setGeneratedIDField(String idNumber){
        if(idField != null) {
            idField.writingForeground();
            idField.setText(idNumber);

            // Ensure the field is disabled
            idField.setEnabled(false);
        }
    }

    // Load dialog data...
    public void loadData() {
        if(studentFormDialogOption == null) return;

        // Setup schools info
        if(schoolCB != null) {
            // Sorting data to give the combo boxes an organized look
            List<String> schools = new ArrayList<>(studentFormDialogOption);
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
        // Label
        programmeLabel = new JLabel("<html>Programme <font color='red'>*</font></html>");

        // Panels
        panel = new JPanel(new BorderLayout());
        centerPanel = new JPanel(new GridBagLayout());
        buttonPanel = new JPanel(new FlowLayout());

        // Text field
        idField = new CTextField("1021025", FIELD_SIZE);
        nameField = new CTextField("Enter student's name here...", FIELD_SIZE);
        emailField = new CTextField("Enter student's email here...", FIELD_SIZE);

        // Combo box
        filterByFieldCB = new JComboBox<>(new String[]{DEFAULT_FIELD_STRING, "School", "Programme"});
        filterByFieldCB.setPrototypeDisplayValue(DEFAULT_FIELD_STRING);

        schoolCB = new JComboBox<>(new String[]{DEFAULT_OPTION_STRING});
        programmeCB = new JComboBox<>(new String[]{DEFAULT_OPTION_STRING});

        // Buttons
        saveBtn = new JButton("Save");
        closeBtn = new JButton("Close");
        resetBtn = new JButton("Reset");

        // Tooltips
        saveBtn.setToolTipText("Click to save the student info");
        closeBtn.setToolTipText("Click to close student form dialog");
        resetBtn.setToolTipText("Click to reset the student form");

        idField.setToolTipText("Generated id field is disabled");
        nameField.setToolTipText("Enter student name");
        emailField.setToolTipText("Enter a valid student email address");
        schoolCB.setToolTipText("Select school student is apart of");
        programmeCB.setToolTipText("Select programme student is apart of");

        // Visibility
        saveBtn.setVisible(false);
        programmeCB.setVisible(false);
        programmeLabel.setVisible(false);

        // Disable
        idField.setEnabled(false);

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

        // Email text field
        emailField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                saveBtnVisibility();
            }
        });

        // School Checkbox
        schoolCB.addActionListener(e -> {
            focus();

            selectedSchool = (String) schoolCB.getSelectedItem();

            if(selectedSchool != null && selectedSchool.equalsIgnoreCase(DEFAULT_OPTION_STRING)) {
                programmeCB.setVisible(false);
                programmeLabel.setVisible(false);
                clearProgrammeCB();
            }
            else if(selectedSchool != null && !selectedSchool.equalsIgnoreCase(DEFAULT_OPTION_STRING)) {
                programmeCB.setVisible(true);
                programmeLabel.setVisible(true);
                populateProgrammeCB();
            }

            saveBtnVisibility();
        });

        // Programme Checkbox
        programmeCB.addActionListener(e -> {
            focus();

            selectedProgramme = (String) programmeCB.getSelectedItem();
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

            setTitle("Add Student Form");
            saveBtn.setText("Save");
            updateRequest = false;

            reset();
        });

        // Save Button
        saveBtn.addActionListener(e -> {
            focus();

            // If the form can submit
            if(canFormSubmit()){
                // If the email is not a valid email address
                if(!BaseUtils.isEmailValid(emailField.getText().trim())){
                    setAlwaysOnTop(false);
                    showErrorMessage("Please enter a valid email address!", "Form Error");
                    setAlwaysOnTop(true);

                    return;
                }

                // Pass the created student to be saved in the database
                if(studentTabListener != null) {
                    setAlwaysOnTop(false);

                    if(updateRequest){
                        String oldEmail = updateStudent.getEmail();

                        updateStudent.setId(idField.getText().trim());
                        updateStudent.setName(BaseUtils.titleCase(nameField.getText().trim(), true));
                        updateStudent.setEmail(emailField.getText().trim().toLowerCase());
                        updateStudent.setSchool(selectedSchool);
                        updateStudent.setProgramme(selectedProgramme);

                        // Send the student data to be updated in the database
                        boolean updated = studentTabListener.updateStudent(updateStudent, oldEmail);

                        // If the student was updated
                        if(updated){
                            closeBtn.doClick();
                            JOptionPane.showMessageDialog(null, "Student was updated successfully!", "Modification Success", JOptionPane.INFORMATION_MESSAGE);
                        }
                        else{
                            // Reset the changed email address to default
                            updateStudent.setEmail(oldEmail);
                        }
                    }
                    else{
                        // Create student object
                        Student student = new Student();

                        student.setId(idField.getText().trim());
                        student.setName(BaseUtils.titleCase(nameField.getText().trim(), true));
                        student.setEmail(emailField.getText().trim().toLowerCase());
                        student.setSchool(selectedSchool);
                        student.setProgramme(selectedProgramme);

                        // Send the student data to be saved in the database
                        boolean added = studentTabListener.addStudent(student);

                        // If the student was added
                        if(added){
                            closeBtn.doClick();
                            JOptionPane.showMessageDialog(null, "Student was added successfully!", "Creation Success", JOptionPane.INFORMATION_MESSAGE);
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
            nameField.setText(updateStudent.getName());
            emailField.setText(updateStudent.getEmail());
            schoolCB.setSelectedItem(updateStudent.getSchool());
            programmeCB.setSelectedItem(updateStudent.getProgramme());
        }
        else{
            nameField.reset();
            emailField.reset();
            schoolCB.setSelectedIndex(0);
            programmeCB.setSelectedIndex(0);

            selectedSchool = null;
            selectedProgramme = null;
            updateStudent = null;
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
        // centerPanel.add(new JLabel("Generated ID#: "), c);
        centerPanel.add(new JLabel("<html>Generated ID# <font color='red'>*</font></html>"), c);

        c.gridx = 1;
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        centerPanel.add(idField, c);


        // Second row
        c.gridx = 0;
        c.gridy = 1;
        c.anchor = GridBagConstraints.FIRST_LINE_END;
        // centerPanel.add(new JLabel("Name: "), c);
        centerPanel.add(new JLabel("<html>Name <font color='red'>*</font></html>"), c);

        c.gridx = 1;
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        centerPanel.add(nameField, c);


        // Third row
        c.gridx = 0;
        c.gridy = 2;
        c.anchor = GridBagConstraints.FIRST_LINE_END;
        // centerPanel.add(new JLabel("Email: "), c);
        centerPanel.add(new JLabel("<html>Email <font color='red'>*</font></html>"), c);

        c.gridx = 1;
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        centerPanel.add(emailField, c);


        // Fourth row
        c.gridx = 0;
        c.gridy = 3;
        c.anchor = GridBagConstraints.FIRST_LINE_END;
        // centerPanel.add(new JLabel("School: "), c);
        centerPanel.add(new JLabel("<html>School <font color='red'>*</font></html>"), c);

        c.gridx = 1;
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        centerPanel.add(schoolCB, c);


        // Fifth row
        c.gridx = 0;
        c.gridy = 4;
        c.anchor = GridBagConstraints.FIRST_LINE_END;
        // centerPanel.add(new JLabel("Programme: "), c);
        centerPanel.add(programmeLabel, c);

        c.gridx = 1;
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        centerPanel.add(programmeCB, c);
    }

    /* Utility Methods */
    private void showErrorMessage(String message, String title){
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);
    }

    // Validate form submission
    private boolean canFormSubmit() {
        // Validating the id field
        if(idField.getText().trim().length() > 0 && !idField.isTextsDifferent()) return false;

        // Validating the name field
        if(nameField.getText().trim().length() > 0 && !nameField.isTextsDifferent()) return false;

        // Validating the email field
        if((emailField.getText().trim().length() > 0 && !emailField.isTextsDifferent())) return false;

        // Validating the school combo field
        selectedSchool = (String) schoolCB.getSelectedItem();
        if(selectedSchool != null && selectedSchool.equalsIgnoreCase(DEFAULT_OPTION_STRING)) return false;

        // Validating the programme combo field
        selectedProgramme = (String) programmeCB.getSelectedItem();
        if(selectedProgramme != null && selectedProgramme.equalsIgnoreCase(DEFAULT_OPTION_STRING)) return false;

        return true;
    }

    // Clear the programme combobox
    private void clearProgrammeCB(){
        programmeCB.removeAllItems();
        programmeCB.addItem(DEFAULT_OPTION_STRING);
        programmeCB.setPrototypeDisplayValue(DEFAULT_OPTION_STRING);
    }

    // Populate the programme combobox
    private void populateProgrammeCB(){
        clearProgrammeCB();

        List<String> list = SchoolUtils.getProgrammes(selectedSchool);
        List<String> programmes = new ArrayList<>(list != null ? list : new ArrayList<>());
        Collections.sort(programmes);

        for(String programme: programmes){
            programmeCB.addItem(programme);
        }
    }

    // Determine the save button visibility
    private void saveBtnVisibility(){
        boolean idGoodToGo = (idField.getText().trim().length() > 0 && idField.isTextsDifferent());
        boolean nameGoodToGo = (nameField.getText().trim().length() > 0 && nameField.isTextsDifferent());
        boolean emailGoodToGo = (emailField.getText().trim().length() > 0 && emailField.isTextsDifferent());
        boolean schoolGoodToGo = (selectedSchool != null && !selectedSchool.equalsIgnoreCase(DEFAULT_OPTION_STRING));
        boolean programmeGoodToGo = (selectedProgramme != null && !selectedProgramme.equalsIgnoreCase(DEFAULT_OPTION_STRING));

        boolean visible = (idGoodToGo && nameGoodToGo && emailGoodToGo && schoolGoodToGo && programmeGoodToGo);

        // Set the visibility of the save button accordingly
        saveBtn.setVisible(visible);
    }


    // This method will update the form to show student information to update
    public void turnIntoUpdateForm(Student student){
        setTitle("Update Student Form");
        saveBtn.setText("Update");
        updateRequest = true;
        updateStudent = student;

        nameField.writingForeground();
        emailField.writingForeground();

        // Update the fields
        idField.setText(student.getId());
        nameField.setText(student.getName());
        emailField.setText(student.getEmail());

        schoolCB.setSelectedItem(student.getSchool());
        programmeCB.setSelectedItem(student.getProgramme());
    }
}
