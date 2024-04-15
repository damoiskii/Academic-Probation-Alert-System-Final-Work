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

package com.app.view.tabs.probation;

import com.app.model.Probation;
import com.app.utils.IconUtils;
import com.app.view.customs.CTextField;
import com.app.view.listeners.BlurPaneListener;
import com.app.view.listeners.FilterListener;
import com.app.view.listeners.ProbationTabListener;
import com.app.view.tabs.students.FilterDialog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;


public class ProbationTabPanel  extends JPanel {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProbationTabPanel.class);

    private JPanel northPanel;

    private JButton searchBtn, refreshBtn, clearTableBtn, filterBtn;
    private CTextField searchField;
    private JLabel infoLabel;

    private ProbationTablePanel probationTablePanel;

    // Dialogs
    private FilterDialog filterDialog;
    private Map<String, Set<String>> filterByDialogOption;

    // Listeners
    private BlurPaneListener blurPaneListener;
    private ProbationTabListener probationTabListener;

    public ProbationTabPanel(){
        super();

        settingPanelProperties();
        initializeComponents();
        addComponentsToPanels();
        registerListeners();

        focus();
    }

    // Set Probation Tab Listener
    public void setProbationTabListener(ProbationTabListener probationTabListener) {
        this.probationTabListener = probationTabListener;

        // Start auto checker
        probationTabListener.autoChecker();
    }

    public void setBlurPaneListener(BlurPaneListener blurPaneListener) {
        this.blurPaneListener = blurPaneListener;
        filterDialog.setBlurPaneListener(blurPaneListener);
    }

    // Loading the table data.
    public void loadTableData(List<Probation> dataset, String action) {
        if(dataset.size() > 0) clearTableBtn.setVisible(true);

        probationTablePanel.loadTableData(dataset, action);

        loadFilterData(dataset);
    }

    // This method will populate the dialog filter form with the necessary data...
    public void loadFilterData(List<Probation> dataset){
        filterByDialogOption = new HashMap<>();

        // Update filter dialog
        Thread thread = new Thread(() -> {
            for(Probation probation : dataset){
                // Setup schools info
                Set<String> schools = filterByDialogOption.getOrDefault("schools", null);
                if(schools == null) schools = new HashSet<>();

                // Add school to the schools set
                schools.add(probation.getSchool());
                filterByDialogOption.put("schools", schools);

                // Setup programme info
                Set<String> programmes = filterByDialogOption.getOrDefault("programmes", null);
                if(programmes == null) programmes = new HashSet<>();

                // Add programme to the programmes set
                programmes.add(probation.getProgramme());
                filterByDialogOption.put("programmes", programmes);
            }

            filterDialog.setFilterByDialogOption(filterByDialogOption);
            filterDialog.loadData();
        });

        thread.setName("p-tab-loader-f");
        thread.start();
    }

    // Initialize the frame's components
    private void initializeComponents() {
        // Labels
        infoLabel = new JLabel("Results for keyword '...'", SwingConstants.LEFT);

        // Panels
        northPanel = new JPanel(new BorderLayout());

        // Button
        searchBtn = new JButton("Search");
        filterBtn = new JButton();

        refreshBtn = new JButton();
        clearTableBtn = new JButton();

        // Text field
        searchField = new CTextField("Search by name or id #...", 50);

        // Table
        probationTablePanel = new ProbationTablePanel();

        // Tooltips
        searchBtn.setToolTipText("Click to search for students");
        refreshBtn.setToolTipText("Click to refresh table data");
        clearTableBtn.setToolTipText("Click to delete all student on probation information");
        filterBtn.setToolTipText("Click to apply table filter");

        // Icons
        IconUtils.setIcon(searchBtn, "search");
        IconUtils.setIcon(refreshBtn, "refresh");
        IconUtils.setIcon(clearTableBtn, "clear");
        IconUtils.setIcon(filterBtn, "filter");

        // Visibility
        refreshBtn.setVisible(false);
        clearTableBtn.setVisible(false);
        infoLabel.setVisible(false);


        // Filter Dialog
        filterDialog = new FilterDialog(null, "Filter Students on Probation");
    }

    // Setting the panel properties
    private void settingPanelProperties() {
        setLayout(new BorderLayout());
        // setBackground(Color.BLACK);
    }

    // Adding the components to panel
    private void addComponentsToPanels() {
        JPanel subNorthPanel1 = new JPanel(new FlowLayout());
        JPanel subNorthPanel2 = new JPanel(new FlowLayout());
        JPanel subNorthPanel3 = new JPanel(new FlowLayout());


        subNorthPanel1.add(searchField);
        subNorthPanel1.add(searchBtn);
        subNorthPanel1.add(filterBtn);

        subNorthPanel2.add(refreshBtn);
        subNorthPanel2.add(clearTableBtn);

        subNorthPanel3.add(infoLabel);

        northPanel.add(subNorthPanel1, BorderLayout.CENTER);
        northPanel.add(subNorthPanel2, BorderLayout.EAST);
        northPanel.add(subNorthPanel3, BorderLayout.WEST);

        add(northPanel, BorderLayout.NORTH);
        add(probationTablePanel, BorderLayout.CENTER);
    }

    // Registering listeners
    private void registerListeners() {
        // Refresh Button
        refreshBtn.addActionListener(e -> {
            focus();

            if(blurPaneListener != null) blurPaneListener.blurBackground();

            int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to refresh the table?", "Confirm Action", JOptionPane.YES_NO_OPTION);

            if(choice == JOptionPane.YES_OPTION){
                refreshList();
            }

            if(blurPaneListener != null) blurPaneListener.unBlurBackground();
        });

        // Clear Table Button
        clearTableBtn.addActionListener(e -> {
            focus();

            if(blurPaneListener != null) blurPaneListener.blurBackground();

            int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete all probation data?", "Confirm Action", JOptionPane.YES_NO_OPTION);

            if(choice == JOptionPane.YES_OPTION){
                if(probationTabListener != null) {
                    infoLabel.setVisible(false);
                    infoLabel.setText("");

                    // Offload work to another thread
                    Thread thread = new Thread(() -> {
                        LOGGER.info("Running Students Deleter...");

                        // Update info label
                        probationTablePanel.updateLabel("Deleting students' data");

                        // Emptying the table...
                        probationTablePanel.loadTableData(new ArrayList<>(), "refresh");

                        // Fake worker: Temp pause to simulate system working...
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException ignore) {}

                        // Deleting the data from the database
                        probationTabListener.deleteProbationList();

                        // Load filter data
                        loadFilterData(new ArrayList<>());

                        clearTableBtn.setVisible(false);
                        refreshBtn.setVisible(false);
                        searchField.reset();

                        // Update info label
                        probationTablePanel.updateLabel("No data available...");

                        LOGGER.info("Completed Students Deletion!\n");
                    });

                    thread.setName("p-tab-clear");
                    thread.start();
                }
            }

            if(blurPaneListener != null) blurPaneListener.unBlurBackground();
        });

        // Search Button
        searchBtn.addActionListener(e -> {
            focus();

            if(probationTabListener != null) {
                if(searchField.getText().trim().length()> 0 && !searchField.isTextsDifferent()) {
                    if(blurPaneListener != null) blurPaneListener.blurBackground();

                    //showErrorMessage("Sorry, you have not entered a search term!", "Search Error");
                    JOptionPane.showMessageDialog(null, "Sorry, you have not entered a search term!", "Search Error", JOptionPane.ERROR_MESSAGE);

                    if(blurPaneListener != null) blurPaneListener.unBlurBackground();
                    return;
                }

                // Offload work to another thread
                Thread thread = new Thread(() -> {
                    LOGGER.info("Running Students Searcher...");

                    // Update info label
                    probationTablePanel.updateLabel("Searching for student(s)");

                    // Emptying the table...
                    probationTablePanel.loadTableData(new ArrayList<>(), "refresh");

                    // Fake worker: Temp pause to simulate system working...
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException ignore) {}

                    // Grab the search term
                    String keyword = searchField.getText();

                    // Search and retrieve data to populate the students table
                    List<Probation> students = probationTabListener.search(keyword);

                    // Set keyword to highlight in the table search results
                    probationTablePanel.setKeyword(keyword);

                    // Update the student table with the retrieved information
                    probationTablePanel.loadTableData(students, "refresh");

                    clearTableBtn.setVisible(false);
                    refreshBtn.setVisible(true);
                    searchField.reset();

                    infoLabel.setText("<html>Results for keyword '<strong style=\"background-color: yellow; font-weight: bold; text-decoration: underline;\">" + keyword + "</strong>'</html>");
                    infoLabel.setVisible(true);

                    // Set keyword to null
                    probationTablePanel.setKeyword(null);

                    // Update info label
                    probationTablePanel.updateLabel("No data available...");

                    LOGGER.info("Completed Students Search!\n");
                });

                thread.setName("p-tab-search");
                thread.start();
            }
        });

        // Filter Button
        filterBtn.addActionListener(e -> {
            focus();

            // If dialog already created but hidden
            if(filterDialog != null && !filterDialog.isVisible()) {
                if(blurPaneListener != null) blurPaneListener.blurBackground();

                filterDialog.setVisible(true);
            }
        });

        // Setup filter dialog listener
        filterDialog.setFilterListener(new FilterListener() {
            @Override
            public void filterStudents(String field, String by) {
                if(probationTabListener == null) return;

                // Offload work to another thread
                Thread thread = new Thread(() -> {
                    LOGGER.info("Running Students Filter...");

                    // Update info label
                    probationTablePanel.updateLabel("Filtering students");

                    // Emptying the table...
                    probationTablePanel.loadTableData(new ArrayList<>(), "refresh");

                    // Fake worker: Temp pause to simulate system working...
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException ignore) {}

                    // Search and retrieve data to populate the students table
                    List<Probation> students = probationTabListener.filter(field, by);

                    // Set filter by to highlight in the table search results
                    probationTablePanel.setFilterBy(by);

                    // Update the student table with the retrieved information
                    probationTablePanel.loadTableData(students, "refresh");

                    clearTableBtn.setVisible(false);
                    refreshBtn.setVisible(true);
                    searchField.reset();

                    infoLabel.setText("<html>Filtered students by " + field.toLowerCase() + " = '<strong style=\"background-color: yellow; font-weight: bold;\">" + by + "</strong>'</html>");
                    infoLabel.setVisible(true);

                    // Set keyword to null
                    probationTablePanel.setFilterBy(null);

                    // Update info label
                    probationTablePanel.updateLabel("No data available...");

                    LOGGER.info("Completed Students Filter!\n");
                });

                thread.setName("s-tab-filter");
                thread.start();
            }
        });
    }

    private void focus(){
        if(northPanel != null){
            northPanel.setFocusable(true);
            northPanel.grabFocus();
        }
    }

    /* Utility Methods */
    public int rowCount(){
        return probationTablePanel.getTableRowCount();
    }

    public void refreshList(){
        if(probationTabListener != null) {
            infoLabel.setVisible(false);
            infoLabel.setText("");

            // Offload work to another thread
            Thread thread = new Thread(() -> {
                //LOGGER.info("Running Students Refresher...");

                // Update info label
                probationTablePanel.updateLabel("Refreshing table data");

                // Emptying the table...
                probationTablePanel.loadTableData(new ArrayList<>(), "refresh");

                // Fake worker: Temp pause to simulate system working...
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException ignore) {}

                // Retrieve data and populate the students table
                List<Probation> students = probationTabListener.refreshTable();

                // Load filter data
                loadFilterData(students);

                // Hide or show clear button
                clearTableBtn.setVisible(students.size() > 0);
                refreshBtn.setVisible(false);
                searchField.reset();

                // Update the student table with the retrieved information
                probationTablePanel.loadTableData(students, "refresh");

                // Update info label
                probationTablePanel.updateLabel("No data available...");

                //LOGGER.info("Completed Students Table Refreshment!\n");
            });

            thread.setName("p-tab-fill");
            thread.start();
        }
    }

    // Refreshing page
    public void refresh(){
        refreshBtn.doClick();
    }
}
