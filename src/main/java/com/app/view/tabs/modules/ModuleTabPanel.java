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

import com.app.exception.ModuleNotFoundException;
import com.app.model.Module;
import com.app.utils.BaseUtils;
import com.app.utils.IconUtils;
import com.app.utils.SchoolUtils;
import com.app.view.customs.CTextField;
import com.app.view.listeners.BlurPaneListener;
import com.app.view.listeners.LoadLazyListener;
import com.app.view.listeners.ModuleTableMenuItemListener;
import com.app.view.listeners.ModuleTabListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class ModuleTabPanel extends JPanel {
    private static final Logger LOGGER = LoggerFactory.getLogger(ModuleTabPanel.class);

    private JPanel northPanel;

    private JButton searchBtn, addBtn, generatorBtn, refreshBtn, clearTableBtn;
    private CTextField searchField;
    private JLabel infoLabel;

    private ModuleTablePanel moduleTablePanel;
    private ModuleFormDialog moduleFormDialog;

    // Listeners
    private BlurPaneListener blurPaneListener;
    private ModuleTabListener moduleTabListener;

    private String generatedCode;


    public ModuleTabPanel(){
        super();

        settingPanelProperties();
        initializeComponents();
        addComponentsToPanels();
        registerListeners();

        focus();
    }

    // Set Module Tab Listener
    public void setModuleTabListener(ModuleTabListener moduleTabListener) {
        this.moduleTabListener = moduleTabListener;
        moduleFormDialog.setModuleTabListener(moduleTabListener);

        // Starting the id generator thread...
        generateModuleCode();
    }

    // Set lazy loader listener
    public void setLoadLazyListener(LoadLazyListener loadLazyListener) {
        moduleTablePanel.setLoadLazyListener(loadLazyListener);
    }

    public void setBlurPaneListener(BlurPaneListener blurPaneListener) {
        this.blurPaneListener = blurPaneListener;
        moduleFormDialog.setBlurPaneListener(blurPaneListener);
        moduleTablePanel.setBlurPaneListener(blurPaneListener);
    }

    // Loading the table data.
    public void loadTableData(List<Module> dataset, String action) {
        if(dataset.size() > 0) clearTableBtn.setVisible(true);

        moduleTablePanel.loadTableData(dataset, action);

        loadModuleFormOptions();
    }

    // Generate a module code in the background while the program loads or closes the form
    public void generateModuleCode(){
        if(moduleTabListener != null) {
            Thread thread = new Thread(() -> {
                while (true){
                    generatedCode = BaseUtils.generateModuleCode();

                    boolean found = moduleTabListener.isCodeFound(generatedCode);

                    // If the code is not in the database then break the loop...
                    if (!found) break;
                }
            });

            thread.setName("co-gen-thread");
            thread.start();
        }
    }

    // This method will populate the dialog form of the add module with the necessary data...
    public void loadModuleFormOptions(){
        // Update filter dialog
        Thread thread = new Thread(() -> {
            // Setup schools info
            moduleFormDialog.setModuleFormDialogOption(SchoolUtils.schools());
            moduleFormDialog.loadData();
        });

        thread.setName("m-tab-loader-s");
        thread.start();
    }

    // Initialize the frame's components
    private void initializeComponents() {
        // Labels
        infoLabel = new JLabel("Results for keyword '...'", SwingConstants.LEFT);

        // Panels
        northPanel = new JPanel(new BorderLayout());

        // Button
        addBtn = new JButton();
        searchBtn = new JButton("Search");

        refreshBtn = new JButton();
        clearTableBtn = new JButton();
        generatorBtn = new JButton("Generate Modules");

        // Text field
        searchField = new CTextField("Search by module code, or name...", 50);

        // Table
        moduleTablePanel = new ModuleTablePanel();

        // Tooltips
        addBtn.setToolTipText("Click to add a new module");
        searchBtn.setToolTipText("Click to search for modules");
        refreshBtn.setToolTipText("Click to refresh table data");
        generatorBtn.setToolTipText("Click to generate modules");
        clearTableBtn.setToolTipText("Click to delete all modules information");

        // Icons
        IconUtils.setIcon(addBtn, "add-module");
        IconUtils.setIcon(searchBtn, "search-module");
        IconUtils.setIcon(refreshBtn, "refresh");
        IconUtils.setIcon(generatorBtn, "generate");
        IconUtils.setIcon(clearTableBtn, "clear");

        // Visibility
        refreshBtn.setVisible(false);
        clearTableBtn.setVisible(false);
        infoLabel.setVisible(false);


        // Dialogs
        moduleFormDialog = new ModuleFormDialog(null, "Add Module Form");
    }

    // Setting the panel properties
    private void settingPanelProperties() {
        setLayout(new BorderLayout());
        //setBackground(Color.GRAY);
    }

    // Adding the components to panel
    private void addComponentsToPanels() {
        JPanel subNorthPanel1 = new JPanel(new FlowLayout());
        JPanel subNorthPanel2 = new JPanel(new FlowLayout());
        JPanel subNorthPanel3 = new JPanel(new FlowLayout());


        subNorthPanel1.add(searchField);
        subNorthPanel1.add(searchBtn);

        subNorthPanel2.add(addBtn);
        subNorthPanel2.add(refreshBtn);
        subNorthPanel2.add(clearTableBtn);
        subNorthPanel2.add(generatorBtn);

        subNorthPanel3.add(infoLabel);

        northPanel.add(subNorthPanel1, BorderLayout.CENTER);
        northPanel.add(subNorthPanel2, BorderLayout.EAST);
        northPanel.add(subNorthPanel3, BorderLayout.WEST);

        add(northPanel, BorderLayout.NORTH);
        add(moduleTablePanel, BorderLayout.CENTER);
    }

    // Registering listeners
    private void registerListeners() {
        // Generator Button
        generatorBtn.addActionListener(e -> {
            focus();

            if(blurPaneListener != null) blurPaneListener.blurBackground();

            String response = JOptionPane.showInputDialog(null, "Enter the amount of modules you wish to generate: ", "Confirm Generation Amount", JOptionPane.OK_OPTION|JOptionPane.INFORMATION_MESSAGE);

            // If empty
            if(response == null || response.length() == 0){
                // showErrorMessage("Sorry, only positive values are allowed to be entered here!", "Number Format Error");
                if(blurPaneListener != null) blurPaneListener.unBlurBackground();
                return;
            }

            // Validate user input
            try{
                int amount = Integer.parseInt(response);

                // If the user entered a value less than one then throw an error
                if(amount < 1) {
                    showErrorMessage("Sorry, only positive values are allowed to be entered here!", "Number Format Error");
                    if(blurPaneListener != null) blurPaneListener.unBlurBackground();
                    return;
                }

                if(moduleTabListener != null) {
                    infoLabel.setVisible(false);
                    infoLabel.setText("");

                    // Offload work to another thread
                    Thread thread = new Thread(() -> {
                        LOGGER.info("Running Module Generator...");

                        // Update info label
                        moduleTablePanel.updateLabel("Generating modules data");

                        // Emptying the table...
                        moduleTablePanel.loadTableData(new ArrayList<>(), "refresh");

                        // Generate data and retrieve list of data
                        List<Module> modules = moduleTabListener.generateModules(amount);

                        // Hide or show clear button
                        clearTableBtn.setVisible(modules.size() > 0);
                        searchField.reset();

                        // Update the student table with the generated information
                        moduleTablePanel.loadTableData(modules, "refresh");

                        // Update info label
                        moduleTablePanel.updateLabel("No data available...");

                        LOGGER.info("Completed Modules Generation!\n");
                    });

                    thread.setName("m-tab-add");
                    thread.start();
                }
            }
            catch (NumberFormatException ignore){
                showErrorMessage("Sorry, only numbers are allowed to be entered here!", "Number Format Error");
            }

            if(blurPaneListener != null) blurPaneListener.unBlurBackground();
        });

        // Clear Table Button
        clearTableBtn.addActionListener(e -> {
            focus();

            if(blurPaneListener != null) blurPaneListener.blurBackground();

            int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete all modules?", "Confirm Action", JOptionPane.YES_NO_OPTION);

            if(choice == JOptionPane.YES_OPTION){
                if(moduleTabListener != null) {
                    infoLabel.setVisible(false);
                    infoLabel.setText("");

                    // Offload work to another thread
                    Thread thread = new Thread(() -> {
                        LOGGER.info("Running Modules Deleter...");

                        // Update info label
                        moduleTablePanel.updateLabel("Deleting modules' data");

                        // Emptying the table...
                        moduleTablePanel.loadTableData(new ArrayList<>(), "refresh");

                        // Fake worker: Temp pause to simulate system working...
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException ignore) {}

                        // Deleting the data from the database
                        moduleTabListener.deleteModules();


                        clearTableBtn.setVisible(false);
                        refreshBtn.setVisible(false);
                        searchField.reset();

                        // Update info label
                        moduleTablePanel.updateLabel("No data available...");

                        LOGGER.info("Completed Modules Deletion!\n");
                    });

                    thread.setName("a-tab-clear");
                    thread.start();
                }
            }

            if(blurPaneListener != null) blurPaneListener.unBlurBackground();
        });

        // Search Button
        searchBtn.addActionListener(e -> {
            focus();

            if(moduleTabListener != null) {
                if(searchField.getText().trim().length()> 0 && !searchField.isTextsDifferent()) {
                    if(blurPaneListener != null) blurPaneListener.blurBackground();

                    //showErrorMessage("Sorry, you have not entered a search term!", "Search Error");
                    JOptionPane.showMessageDialog(null, "Sorry, you have not entered a search term!", "Search Error", JOptionPane.ERROR_MESSAGE);

                    if(blurPaneListener != null) blurPaneListener.unBlurBackground();
                    return;
                }

                // Offload work to another thread
                Thread thread = new Thread(() -> {
                    LOGGER.info("Running Modules Searcher...");

                    // Update info label
                    moduleTablePanel.updateLabel("Searching for module(s)");

                    // Emptying the table...
                    moduleTablePanel.loadTableData(new ArrayList<>(), "refresh");

                    // Fake worker: Temp pause to simulate system working...
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException ignore) {}

                    // Grab the search term
                    String keyword = searchField.getText();

                    // Search and retrieve data to populate the modules table
                    List<Module> modules = moduleTabListener.search(keyword);

                    // Set keyword to highlight in the table search results
                    moduleTablePanel.setKeyword(keyword);

                    // Update the module table with the retrieved information
                    moduleTablePanel.loadTableData(modules, "refresh");

                    clearTableBtn.setVisible(false);
                    refreshBtn.setVisible(true);
                    searchField.reset();

                    infoLabel.setText("<html>Results for keyword '<strong style=\"background-color: yellow; font-weight: bold; text-decoration: underline;\">" + keyword + "</strong>'</html>");
                    infoLabel.setVisible(true);

                    // Set keyword to null
                    moduleTablePanel.setKeyword(null);

                    // Update info label
                    moduleTablePanel.updateLabel("No data available...");

                    LOGGER.info("Completed Modules Search!\n");
                });

                thread.setName("m-tab-search");
                thread.start();
            }
        });

        // Refresh Button
        refreshBtn.addActionListener(e -> {
            focus();

            if(blurPaneListener != null) blurPaneListener.blurBackground();

            int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to refresh the table?", "Confirm Action", JOptionPane.YES_NO_OPTION);

            if(choice == JOptionPane.YES_OPTION){
                if(moduleTabListener != null) {
                    refresh("Refreshing table data", 6000);
                }
            }

            if(blurPaneListener != null) blurPaneListener.unBlurBackground();
        });

        // Setup the table menu item listener
        moduleTablePanel.setMenuItemListener(new ModuleTableMenuItemListener() {
            @Override
            public boolean deleteModule(String code) {
                boolean success = false;

                if(moduleTabListener != null){
                    LOGGER.info("Running Module Deleter...");

                    // Delete module's data from the table and the database
                    try {
                        moduleTablePanel.deleteData();
                        moduleTabListener.deleteModule(code);

                        success = true;
                    } catch (ModuleNotFoundException ignore) {}

                    clearTableBtn.setVisible(false);
                    refreshBtn.setVisible(true);
                    //infoLabel.setVisible(false);
                    searchField.reset();

                    LOGGER.info("Completed Module Deletion!\n");
                }

                return success;
            }

            @Override
            public void updateModule(Module module) {
                // If dialog already created but hidden
                if(moduleFormDialog != null && !moduleFormDialog.isVisible()) {
                    if(blurPaneListener != null) blurPaneListener.blurBackground();
                    
                    refreshBtn.setVisible(false);

                    // Update the form to reflect the module's information
                    moduleFormDialog.turnIntoUpdateForm(module);
                    moduleFormDialog.setVisible(true);
                }
            }
        });

        // Add Button
        addBtn.addActionListener(e -> {
            focus();

            // If dialog already created but hidden
            if(moduleFormDialog != null && !moduleFormDialog.isVisible()) {
                if(blurPaneListener != null) blurPaneListener.blurBackground();

                refreshBtn.setVisible(false);

                moduleFormDialog.setGeneratedCodeField(generatedCode); // Passing the generated code here when opening the form
                moduleFormDialog.setVisible(true);
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
    private void showErrorMessage(String message, String title){
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);
    }

    public int rowCount(){
        return moduleTablePanel.getTableRowCount();
    }

    // Updating the table with the new module data added to the system
    public void addData(Module module){
        refreshBtn.setVisible(true);

        moduleTablePanel.addData(module);
    }

    public void updateData(Module module){
        refreshBtn.setVisible(true);

        moduleTablePanel.updateData(module);
    }

    // Refreshing page
    public void refresh(String text, long sleep){
        infoLabel.setVisible(false);
        infoLabel.setText("");

        // Offload work to another thread
        Thread thread = new Thread(() -> {
            LOGGER.info("Running Modules Refresher...");

            // Update info label
            moduleTablePanel.updateLabel(text);

            // Emptying the table...
            moduleTablePanel.loadTableData(new ArrayList<>(), "refresh");

            // Fake worker: Temp pause to simulate system working...
            try {
                Thread.sleep(sleep);
            } catch (InterruptedException ignore) {}

            // Retrieve data and populate the modules table
            List<Module> modules = moduleTabListener.refreshTable();

            // Hide or show clear button
            clearTableBtn.setVisible(modules.size() > 0);
            refreshBtn.setVisible(false);
            searchField.reset();

            // Update the student table with the retrieved information
            moduleTablePanel.loadTableData(modules, "refresh");

            // Update info label
            moduleTablePanel.updateLabel("No data available...");

            LOGGER.info("Completed Modules Table Refreshment!\n");
        });

        thread.setName("m-tab-fill");
        thread.start();
    }
}
