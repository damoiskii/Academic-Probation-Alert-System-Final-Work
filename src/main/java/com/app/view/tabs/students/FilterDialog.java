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


import com.app.utils.IconUtils;
import com.app.view.listeners.BlurPaneListener;
import com.app.view.listeners.FilterListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.*;
import java.util.List;


public class FilterDialog extends JDialog {
    private JLabel byLabel, selectedLabel;
    private JComboBox<String> filterByFieldCB, schoolCB, programmeCB;

    private JButton filterBtn, closeBtn, resetBtn;

    private JPanel panel, northPanel, centerPanel, buttonPanel;

    private final String DEFAULT_FIELD_STRING = "-------------------";
    private final String DEFAULT_OPTION_STRING = "--------------------------------------------------------------------------------";
    private String selectedField, selectedOption;

    private FilterListener filterListener;
    private Map<String, Set<String>> filterByDialogOption;
    private BlurPaneListener blurPaneListener;


    public FilterDialog(){
        super();

        focus();
    }

    public FilterDialog(JFrame frame, String title){
        super(frame, title);

        settingWindowProperties();
        initializeComponents();
        addComponentsToPanels();
        addPanelsToWindow();
        registerListeners();

        loadData();

        focus();
    }

    // Ste filter listener
    public void setFilterListener(FilterListener filterListener) {
        this.filterListener = filterListener;
    }

    public void setFilterByDialogOption(Map<String, Set<String>> filterByDialogOption) {
        this.filterByDialogOption = filterByDialogOption;
    }

    public void setBlurPaneListener(BlurPaneListener blurPaneListener) {
        this.blurPaneListener = blurPaneListener;
    }

    // Load dialog data...
    public void loadData() {
        if(filterByDialogOption == null) return;

        // Setup schools info
        Set<String> schools = filterByDialogOption.getOrDefault("schools", null);
        if(schools != null && schoolCB != null) {
            // Sorting data to give the combo boxes an organized look
            List<String> sortedSchoolsList = new ArrayList<>(schools);
            Collections.sort(sortedSchoolsList);

            // Add school to the schools set
            schoolCB.removeAllItems();
            schoolCB.addItem(DEFAULT_OPTION_STRING);
            schoolCB.setPrototypeDisplayValue(DEFAULT_OPTION_STRING);

            for(String school: sortedSchoolsList){
                schoolCB.addItem(school);
            }
        }

        // Setup programme info
        Set<String> programmes = filterByDialogOption.getOrDefault("programmes", null);
        if(programmes != null && programmeCB != null){
            // Sorting data to give the combo boxes an organized look
            List<String> sortedProgrammesList = new ArrayList<>(programmes);
            Collections.sort(sortedProgrammesList);

            // Add programme to the programmes set
            programmeCB.removeAllItems();
            programmeCB.addItem(DEFAULT_OPTION_STRING);
            programmeCB.setPrototypeDisplayValue(DEFAULT_OPTION_STRING);

            for(String programme: sortedProgrammesList){
                programmeCB.addItem(programme);
            }
        }
    }

    // Initialize the frame's components
    private void initializeComponents() {
        // Panels
        panel = new JPanel(new BorderLayout());
        northPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        centerPanel = new JPanel(new GridBagLayout());
        buttonPanel = new JPanel(new FlowLayout());

        // Label
        byLabel = new JLabel("By: ", SwingConstants.CENTER);
        selectedLabel = new JLabel("", SwingConstants.CENTER);
        selectedLabel.setFont(new Font("Arial", Font.BOLD, 18));

        // Combo box
        filterByFieldCB = new JComboBox<>(new String[]{DEFAULT_FIELD_STRING, "School", "Programme"});
        filterByFieldCB.setPrototypeDisplayValue(DEFAULT_FIELD_STRING);

        schoolCB = new JComboBox<>(new String[]{DEFAULT_OPTION_STRING});
        programmeCB = new JComboBox<>(new String[]{DEFAULT_OPTION_STRING});

        // Buttons
        filterBtn = new JButton("Filter");
        closeBtn = new JButton("Close");
        resetBtn = new JButton("Reset");

        // Tooltips
        filterBtn.setToolTipText("Click to filter selected option");
        closeBtn.setToolTipText("Click to close filter dialog");
        resetBtn.setToolTipText("Click to reset the filter options");

        // Visibility
        schoolCB.setVisible(false);
        programmeCB.setVisible(false);
        filterBtn.setVisible(false);
        resetBtn.setVisible(false);

        // Icons
        IconUtils.setIcon(closeBtn, "close");
        IconUtils.setIcon(filterBtn, "filter");
        IconUtils.setIcon(resetBtn, "refresh");
    }

    // Setting the window properties
    private void settingWindowProperties() {
        setLayout(new BorderLayout());
        setSize(new Dimension(750, 250));
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
        buttonPanel.add(filterBtn);
        buttonPanel.add(resetBtn);
        buttonPanel.add(closeBtn);

        northPanel.add(byLabel);
        northPanel.add(filterByFieldCB);

        GridBagConstraints c = new GridBagConstraints();

        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(-5, 0, 5, 0);
        c.gridx = 0;
        c.gridy = 0;
        centerPanel.add(schoolCB, c);

        c.insets.top = 0;
        c.gridx = 0;
        c.gridy = 1;
        centerPanel.add(programmeCB, c);

        c.gridx = 0;
        c.gridy = 4;
        centerPanel.add(selectedLabel, c);

        panel.add(northPanel, BorderLayout.NORTH);
        panel.add(centerPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
    }

    // Adding the panel to the window
    private void addPanelsToWindow() {
        add(panel, BorderLayout.CENTER);
    }

    // Registering listeners
    private void registerListeners() {
        // Filter By Field Checkbox
        filterByFieldCB.addActionListener(e -> {
            focus();

            selectedField = (String) filterByFieldCB.getSelectedItem();

            if(selectedField != null){
                selectedLabel.setText("");
                schoolCB.setSelectedIndex(0);
                programmeCB.setSelectedIndex(0);

                if(selectedField.equalsIgnoreCase(DEFAULT_FIELD_STRING)){
                    // Hide Option Combobox
                    schoolCB.setVisible(false);
                    programmeCB.setVisible(false);
                    resetBtn.setVisible(false);
                }
                else if(selectedField.equalsIgnoreCase("school")){
                    // Show Option Combobox
                    programmeCB.setVisible(false);
                    schoolCB.setVisible(true);
                    resetBtn.setVisible(true);
                }
                else if(selectedField.equalsIgnoreCase("programme")){
                    // Show Option Combobox
                    schoolCB.setVisible(false);
                    programmeCB.setVisible(true);
                    resetBtn.setVisible(true);
                }
            }
        });

        // School Checkbox
        schoolCB.addActionListener(e -> {
            focus();

            selectedOption = (String) schoolCB.getSelectedItem();

            // Update the selected label info
            if(selectedOption != null && selectedOption.equalsIgnoreCase(DEFAULT_OPTION_STRING)) {
                selectedLabel.setText("");
                filterBtn.setVisible(false);
            }
            else {
                selectedLabel.setText(selectedOption);
                filterBtn.setVisible(true);
            }
        });

        // Programme Checkbox
        programmeCB.addActionListener(e -> {
            focus();

            selectedOption = (String) programmeCB.getSelectedItem();

            // Update the selected label info
            if(selectedOption != null && selectedOption.equalsIgnoreCase(DEFAULT_OPTION_STRING)) {
                selectedLabel.setText("");
                filterBtn.setVisible(false);
            }
            else {
                selectedLabel.setText(selectedOption);
                filterBtn.setVisible(true);
            }
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
            reset();
        });

        // Filter Button
        filterBtn.addActionListener(e -> {
            if(filterListener != null) filterListener.filterStudents(selectedField, selectedOption);

            closeBtn.doClick();
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
        filterByFieldCB.setSelectedIndex(0);
        schoolCB.setSelectedIndex(0);
        programmeCB.setSelectedIndex(0);

        selectedLabel.setText("");
    }
}
