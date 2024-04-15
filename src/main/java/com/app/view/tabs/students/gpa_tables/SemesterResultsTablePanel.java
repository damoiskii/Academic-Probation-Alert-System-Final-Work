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

package com.app.view.tabs.students.gpa_tables;


import com.app.task.UpdateLabelTask;
import com.app.utils.SimulationUtils;
import com.app.utils.TableUtils;
import com.app.view.customs.CJScrollPane;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.util.List;
import java.util.Map;


public class SemesterResultsTablePanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private CJScrollPane scrollPane;
    private JLabel infoLabel, headerNoteLabel, footerNoteLabel;

    private UpdateLabelTask updateLabelTask;

    // FOR TESTING PURPOSES ONLY
    private double gpa, pointsEarned;
    private int totalCredits;


    public SemesterResultsTablePanel(){
        super();

        setContainerProperties();

        initializeComponents();
        addComponentsToPanels();
        registerListener();

        loadTableData(null);
    }

    // Set Header Note Text
    public void setHeaderNoteText(String text) {
        headerNoteLabel.setText("<html><strong style='font-size:20px;'>" + text + "<strong/></html>");
    }

    // Set Footer Note Text
    public void setFooterNoteText() {
        // Calculate GPA
        gpa = (pointsEarned / totalCredits);

        footerNoteLabel.setText("<html>GPA = "  + String.format("%.2f", pointsEarned) + " / " + totalCredits + " = <strong style='font-size:10px;'>" + String.format("%.2f", gpa) + "<strong/></html>");
    }

    public void setFooterNoteText(int totalCredits,  double pointsEarned, double gpa) {
        // Calculate GPA
        footerNoteLabel.setText("<html>GPA = "  + String.format("%.2f", pointsEarned) + " / " + totalCredits + " = <strong style='font-size:10px;'>" + String.format("%.2f", gpa) + "<strong/></html>");
    }

    // Load table data
    public void loadTableData(Map<String, List<String>> dataset) {
        tableModel = (DefaultTableModel) table.getModel();

        if(dataset != null){
            // Get the modules info
            List<String> modules = dataset.getOrDefault("modules", null);
            if(modules != null) updateModulesInfo(modules);


            // Get the credits info
            List<String> credits = dataset.getOrDefault("credits", null);
            if(credits != null) updateTableInfo(credits, "credits");


            // Get the grades info
            List<String> grades = dataset.getOrDefault("grades", null);
            if(grades != null) updateTableInfo(grades, null);


            // Get the grade points info
            List<String> gradePoints = dataset.getOrDefault("points", null);
            if(gradePoints != null) updateTableInfo(gradePoints, null);


            // Get the grade points earned info
            List<String> gradePointsEarned = dataset.getOrDefault("earned", null);
            if(gradePointsEarned != null) updateTableInfo(gradePointsEarned, "earned");


            // Update footer note label
            setFooterNoteText();
        }

        // Scroll to top...
        scrollToTop();

        // Trigger table change/update event
        tableModel.fireTableDataChanged();

        updateComponentsOnPanels();

        // Center all contents in the table
        TableUtils.setCellsAlignment(table, SwingConstants.CENTER);

        repaint();
        revalidate();
    }


    // Initializing the components
    private void initializeComponents(){
        headerNoteLabel = new JLabel("", SwingConstants.LEFT);
        footerNoteLabel = new JLabel("", SwingConstants.LEFT);
        infoLabel = new JLabel("No data available...", SwingConstants.CENTER);

        // Table
        table = new JTable();

        // Table Settings
        tableSettings();

        scrollPane = new CJScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(300, 115));
    }

    // Adding the components to the panels
    private void addComponentsToPanels(){
        add(headerNoteLabel, BorderLayout.NORTH);
        add(infoLabel, BorderLayout.CENTER);
        add(footerNoteLabel, BorderLayout.SOUTH);
    }

    private void updateComponentsOnPanels(){
        if(tableModel.getRowCount() > 0){
            // If the scroll pane is already on the panel then return
            if(scrollPane.getParent() != null) return;

            remove(infoLabel);
            add(scrollPane, BorderLayout.CENTER);
        }
        else{
            // If the label is already on the panel then return
            if(infoLabel.getParent() != null) return;

            remove(scrollPane);
            add(infoLabel, BorderLayout.CENTER);
        }
    }

    // Setting the container properties
    private void setContainerProperties(){
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        // setBackground(Color.BLACK);
        // setOpaque(true);

        setVisible(false);
    }

    // Setting up listeners
    private void registerListener(){

    }

    public void scrollToTop(){
        if (scrollPane != null) scrollPane.resetScroll();
    }

    public void scrollToBottom(){
        if (scrollPane != null) scrollPane.scrollToBottom();
    }

    private void tableSettings(){
        // Column Headers
        tableModel = (DefaultTableModel) table.getModel();
        tableModel.setColumnIdentifiers(new String[]{"Module", "Total"});

        // Set the selection mode
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Change the width of the count column
        // resizeColumn(1, 50, 20, 50);

        // Change the width of the id column
        // resizeColumn(2, 200, 130, 200);

        // Change the width of the email column
        // resizeColumn(4, 300, 230, 300);

        // Disabling user editing the table data on the fly
        table.setDefaultEditor(Object.class, null);

        // To prevent the user from re-ordering the table
        table.getTableHeader().setReorderingAllowed(false);
    }

    // Updating the table from the given data
    // Modules
    private void updateModulesInfo(List<String> headers){
        tableModel = (DefaultTableModel) table.getModel();
        headers.add("Total"); // Adding the total header to the list before adding it to the table header

        // Column Headers
        tableModel.setColumnIdentifiers(headers.toArray());

        // Shorten module names and set the tooltip to display the module name instead
        //TableCellRenderer renderer = new CustomHeaderRenderer();
        TableColumnModel columnModel = table.getColumnModel();

        int index = 0;
        for(String header: headers){
            //columnModel.getColumn(index).setHeaderRenderer(renderer);
            columnModel.getColumn(index).setHeaderValue(header);

            index++;
        }
    }


    // Add row data
    private void updateTableInfo(List<String> rowDataSet, String row){
        tableModel = (DefaultTableModel) table.getModel();
        Object[] data;
        int index = 0;

        if(row != null && row.equalsIgnoreCase("credits")){
            // Plus one to add total credits in the total column
            data = new Object[rowDataSet.size() + 1];

            // Insert data into Table
            for (String text: rowDataSet) {
                data[index] = text;

                try{
                    totalCredits += Integer.parseInt(text);
                }
                catch(NumberFormatException ignore){}

                index++;
            }

            // Total credits
            data[index] = totalCredits;
        }
        else if(row != null && row.equalsIgnoreCase("earned")){
            // Plus one to add total credits in the total column
            data = new Object[rowDataSet.size() + 1];
            double points = 0;

            // Insert data into Table
            for (String text: rowDataSet) {
                data[index] = text;

                try{
                    points += Double.parseDouble(text);
                }
                catch(NumberFormatException ignore){}

                index++;
            }

            // Total grade points earned
            pointsEarned = Double.parseDouble(String.format("%.2f", points));
            data[index] = pointsEarned;
        }
        else{
            data = new Object[rowDataSet.size()];

            // Insert data into Table
            for (String text: rowDataSet) {
                data[index] = text;
                index++;
            }
        }

        // Adding the formatted data to the table model
        tableModel.addRow(data);
    }


    /* Utility Methods */
    // Update the top label
    public void updateLabel(String text){
        infoLabel.setText(text);

        if(updateLabelTask == null) updateLabelTask = new UpdateLabelTask();

        if(updateLabelTask.isRunning() || text.length() == 0) updateLabelTask.stop();

        if(!text.equalsIgnoreCase("No data available...")) updateLabelTask.run(infoLabel);
    }


    // Clear the table whenever necessary
    public void clearTable(){
        tableModel = (DefaultTableModel) table.getModel();
        tableModel.setColumnIdentifiers(new String[]{"Module", "Total"});
        tableModel.setRowCount(0);

        gpa = 0;
        totalCredits = 0;
        pointsEarned = 0;
        footerNoteLabel.setText("");

        loadTableData(null);
        setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
    }

    // Update the border of the panel
    public void updateBorder(boolean isSameYear){
        // Reshape the container when it's only one semester info is available
        if(isSameYear && SimulationUtils.getSemester().equalsIgnoreCase("semester 2")){
            setBorder(BorderFactory.createEmptyBorder(20, 0, 230, 0));
        }
        else{
            setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        }
    }
}
