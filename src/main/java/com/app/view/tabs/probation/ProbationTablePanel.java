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
import com.app.task.UpdateLabelTask;
import com.app.utils.BaseUtils;
import com.app.view.customs.CJScrollPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;


public class ProbationTablePanel extends JPanel {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProbationTablePanel.class);

    //private Probation probation;

    //private int rowSelected;
    private JTable table;
    private DefaultTableModel tableModel;
    private CJScrollPane scrollPane;
    private JLabel infoLabel;

    private UpdateLabelTask updateLabelTask;

    private String keyword, filterBy;


    public ProbationTablePanel(){
        super();

        setContainerProperties();

        initializeComponents();
        addComponentsToPanels();
        registerListener();
    }

    // Set keyword
    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    // Set filter by
    public void setFilterBy(String filterBy) {
        this.filterBy = filterBy;
    }

    public void loadTableData(List<Probation> dataset, String action) {
        tableModel = (DefaultTableModel) table.getModel();

        if(action != null && action.equalsIgnoreCase("refresh")) tableModel.setRowCount(0);

        if(action != null && action.equalsIgnoreCase("delete")){
            // Update the column counter values
            columnCounter();
        }
        else{
            int count = 0;

            // Insert data into Table
            for (Probation probation : dataset) {
                Object[] data = buildDataObject(probation, count);

                // Adding the formatted data to the table model
                tableModel.addRow(data);

                // Increment the count variable
                count++;
            }
        }

        // Scroll to top...
        scrollToTop();

        // Trigger table change/update event
        tableModel.fireTableDataChanged();

        updateComponentsOnPanels();

        if(action != null && action.equalsIgnoreCase("refresh")) repaint(); revalidate();
    }


    // Initializing the components
    private void initializeComponents(){
        infoLabel = new JLabel("No data available...", SwingConstants.CENTER);
        table = new JTable();

        // Table Settings
        tableSettings();

        scrollPane = new CJScrollPane(table);
    }

    // Adding the components to the panels
    private void addComponentsToPanels(){
        add(infoLabel, BorderLayout.CENTER);
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
        // setBackground(Color.BLACK);

        //setOpaque(true);
    }

    // Setting up listeners
    private void registerListener(){
        // Selection Listener
        table.getSelectionModel().addListSelectionListener(event -> {
            int viewRow = table.getSelectedRow();
            //rowSelected = viewRow;

            // To prevent duplicate selections
            if (!event.getValueIsAdjusting() && viewRow != -1) {
                try{
                    // Retrieve the query object from the hidden cell in the selected row...
                    // This same module object will be used when the popup menu items are clicked.
                    //probation = (Probation) table.getModel().getValueAt(table.getSelectedRow(),0);

                    // System.out.println("Module: " + module);

                    // Retrieve the row data
                    // Object[] module = tableModel.getDataVector().elementAt(table.convertRowIndexToModel(table.getSelectedRow())).toArray();
                    // System.out.println("Cell: " + Arrays.toString(student));
                }
                catch (ClassCastException | IndexOutOfBoundsException e){
                    LOGGER.error("Error inside of the ModuleTablePanel [addListSelectionListener] -> " + e.getMessage());
                }
            }
        });
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
        tableModel.setColumnIdentifiers(new String[]{"ProbationObj", "Count", "Student ID#", "Name", "Programme", "School", "GPA"});


        // Set the selection mode
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Change the width of the count column
        resizeColumn(1, 50, 20, 50);

        // Change the width of the id# column
        resizeColumn(2, 200, 130, 200);

        // Change the width of the name column
        resizeColumn(3, 400, 200, 400);

        // Change the width of the gpa column
        resizeColumn(6, 200, 130, 200);


        // Hiding the first column since these will have the ids
        table.removeColumn(table.getColumnModel().getColumn(0));


        // Disabling user editing the table data on the fly
        table.setDefaultEditor(Object.class, null);

        // To prevent the user from re-ordering the table
        table.getTableHeader().setReorderingAllowed(false);
    }

    // This method will be used to help count the number of rows in the table and pass it to which ever class using the returned value.
    public boolean isTableEmpty(){
        tableModel = (DefaultTableModel) table.getModel();

        if(tableModel.getRowCount() < 1) {
            updateComponentsOnPanels();

            repaint();
            revalidate();

            return true;
        }

        return false;
    }

    public int getTableRowCount(){
        tableModel = (DefaultTableModel) table.getModel();

        return tableModel.getRowCount();
    }

    // This method is used to build the data object[] from the student object: also used to minimize code repetition
    private Object[] buildDataObject(Probation probation, Integer count){
        Object[] data = new Object[7];

        // Set up the rendering of the data...
        data[0] = probation; // Hidden but will be used to manipulate the query object in the database

        if(count != null) data[1] = (count + 1);

        data[2] = probation.getStudentID();
        data[3] = probation.getName();
        data[4] = probation.getProgramme();
        data[5] = probation.getSchool();
        data[6] = String.format("%.2f", probation.getGpa());

        // Highlight search/filter results
        if(keyword != null) highlightKeyword(probation, data);
        if(filterBy != null) highlightFilterBy(probation, data);

        return data;
    }

    // This method is to change the size of the column width of the given index for a column; will eliminate code duplication
    private void resizeColumn(int columnIndex, int width, int min, int max){
        table.getColumnModel().getColumn(columnIndex).setWidth(width);
        table.getColumnModel().getColumn(columnIndex).setMinWidth(min);
        table.getColumnModel().getColumn(columnIndex).setMaxWidth(max);
    }

    // Update the top label
    public void updateLabel(String text){
        infoLabel.setText(text);

        if(updateLabelTask == null) updateLabelTask = new UpdateLabelTask();

        if(updateLabelTask.isRunning() || text.length() == 0) updateLabelTask.stop();

        if(!text.equalsIgnoreCase("No data available...")) updateLabelTask.run(infoLabel);
    }


    // Method to highlight the search term/keyword
    public void highlightKeyword(Probation probation, Object[] data) {
        // If keyword in student ID
        if (probation.getStudentID().toLowerCase().contains(keyword.toLowerCase())) {
            String text = probation.getStudentID().replace(" ", "&nbsp;");
            data[2] = "<html>" + text.replace(keyword, "<strong style=\"background-color: yellow; font-weight: bold; text-decoration: underline;\">" + keyword + "</strong>") + "</html>";
        }

        // If keyword in name
        if (probation.getName().toLowerCase().contains(keyword.toLowerCase())) {
            String text = probation.getName().replace(" ", "&nbsp;");

            // Checking for uppercase in the student's name; if found, capitalize the keyword [starting the name]
            String keyword = BaseUtils.isUpperCase(text) ? BaseUtils.titleCase(this.keyword, true) : this.keyword;

            // Run tweak to find names containing the keyword [name containing]
            keyword = probation.getName().contains(keyword) ? keyword : this.keyword;;

            data[3] = "<html>" + text.replace(keyword, "<strong style=\"background-color: yellow; font-weight: bold; text-decoration: underline;\">" + keyword + "</strong>") + "</html>";
        }
    }

    // Method to highlight the filter by term
    public void highlightFilterBy(Probation probation, Object[] data) {
        // If filter by in programme
        if (probation.getProgramme().toLowerCase().contains(filterBy.toLowerCase())) {
            String text = probation.getProgramme().replace(" ", "&nbsp;");
            data[4] = "<html>" + text.replace(filterBy, "<strong style=\"background-color: yellow; font-weight: bold; text-decoration: underline;\">" + filterBy + "</strong>") + "</html>";
        }

        // If keyword in school
        if (probation.getSchool().toLowerCase().contains(filterBy.toLowerCase())) {
            String text = probation.getSchool().replace(" ", "&nbsp;");
            data[5] = "<html>" + text.replace(filterBy, "<strong style=\"background-color: yellow; font-weight: bold; text-decoration: underline;\">" + filterBy + "</strong>") + "</html>";
        }
    }

    // Add column count values
    private void columnCounter(){
        int count = 0;
        int rows = tableModel.getRowCount();

        // Insert data into Table
        for (int i = 0; i < rows; i++) {
            tableModel.setValueAt((count + 1), count, 1);

            // Increment the count variable
            count++;
        }
    }
}
