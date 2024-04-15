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
import com.app.task.UpdateLabelTask;
import com.app.utils.BaseUtils;
import com.app.utils.IconUtils;
import com.app.view.customs.CJScrollPane;
import com.app.view.listeners.BlurPaneListener;
import com.app.view.listeners.StudentTableMenuItemListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;


public class StudentTablePanel extends JPanel {
    private static final Logger LOGGER = LoggerFactory.getLogger(StudentTablePanel.class);

    private Student student;

    private int rowSelected;
    private JTable table;
    private DefaultTableModel tableModel;
    private CJScrollPane scrollPane;
    private JLabel infoLabel;

    private UpdateLabelTask updateLabelTask;

    private String keyword, filterBy;
    private StudentTableMenuItemListener menuItemListener;
    private BlurPaneListener blurPaneListener;


    public StudentTablePanel(){
        super();

        setContainerProperties();

        initializeComponents();
        addComponentsToPanels();
        registerListener();
    }

    // Set menu item listener
    public void setMenuItemListener(StudentTableMenuItemListener menuItemListener) {
        this.menuItemListener = menuItemListener;
    }

    // Set blurred pane listener
    public void setBlurPaneListener(BlurPaneListener blurPaneListener) {
        this.blurPaneListener = blurPaneListener;
    }

    // Set keyword
    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    // Set filter by
    public void setFilterBy(String filterBy) {
        this.filterBy = filterBy;
    }

    // Load table data
    public void loadTableData(List<Student> dataset, String action) {
        tableModel = (DefaultTableModel) table.getModel();

        if(action != null && action.equalsIgnoreCase("refresh")) tableModel.setRowCount(0);

        if(action != null && action.equalsIgnoreCase("delete")){
            // Update the column counter values
            columnCounter();
        }
        else{
            int count = 0;

            // Insert data into Table
            for (Student student : dataset) {
                Object[] data = buildDataObject(student, count);

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
            rowSelected = viewRow;

            // To prevent duplicate selections
            if (!event.getValueIsAdjusting() && viewRow != -1) {
                try{
                    // Retrieve the query object from the hidden cell in the selected row...
                    // This same student object will be used when the popup menu items are clicked.
                    student = (Student) table.getModel().getValueAt(table.getSelectedRow(),0);

                    // System.out.println("Student: " + student);

                    // Retrieve the row data
                    // Object[] student = tableModel.getDataVector().elementAt(table.convertRowIndexToModel(table.getSelectedRow())).toArray();
                    // System.out.println("Cell: " + Arrays.toString(student));
                }
                catch (ClassCastException | IndexOutOfBoundsException e){
                    LOGGER.error("Error inside of the StudentTablePanel [addListSelectionListener] -> " + e.getMessage());
                }
            }
        });

        // Mouse Lister
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // Set up mouse right-click
                if (SwingUtilities.isRightMouseButton(e)) {
                    int row = table.rowAtPoint(e.getPoint());

                    if (row >= 0 && row < table.getRowCount()) {
                        table.setRowSelectionInterval(row, row);

                        JPopupMenu menu = new JPopupMenu();
                        JMenuItem deleteItem = new JMenuItem("Delete");
                        JMenuItem updateItem = new JMenuItem("Update");
                        JMenuItem viewItem = new JMenuItem("Info");

                        // Set Icons
                        IconUtils.setIcon(updateItem, "edit");
                        IconUtils.setIcon(deleteItem, "delete");
                        IconUtils.setIcon(viewItem, "view");

                        // Set Tooltips
                        viewItem.setToolTipText("Select to view the details of this student");
                        updateItem.setToolTipText("Select to update this student info");
                        deleteItem.setToolTipText("Select to delete this student info");

                        //Set up action listeners
                        // Updating
                        updateItem.addActionListener(e1 -> {
                            if(blurPaneListener != null) blurPaneListener.blurBackground();

                            int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to update this student's info?", "Confirm Action", JOptionPane.YES_NO_OPTION);

                            if(choice != JOptionPane.YES_OPTION) {
                                if(blurPaneListener != null) blurPaneListener.unBlurBackground();
                                return;
                            }

                            if(menuItemListener != null) menuItemListener.updateStudent(student);
                        });

                        // Viewing and maybe updating...
                        viewItem.addActionListener(e1 -> {
                            if(blurPaneListener != null) blurPaneListener.blurBackground();

                            int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to view this student's info?", "Confirm Action", JOptionPane.YES_NO_OPTION);

                            if(choice != JOptionPane.YES_OPTION) {
                                if(blurPaneListener != null) blurPaneListener.unBlurBackground();
                                return;
                            }

                            if(menuItemListener != null){
                                menuItemListener.viewStudentInfo(student);
                            }
                        });

                        // Deleting
                        deleteItem.addActionListener(e1 -> {
                            if(blurPaneListener != null) blurPaneListener.blurBackground();

                            int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this student's info?", "Confirm Action", JOptionPane.YES_NO_OPTION);

                            if(choice != JOptionPane.YES_OPTION) {
                                if(blurPaneListener != null) blurPaneListener.unBlurBackground();
                                return;
                            }

                            if(menuItemListener != null){
                                boolean success = menuItemListener.deleteStudent(student.getId());
                                String message;

                                if(success){
                                    message = "Student info was deleted successfully!";
                                }
                                else{
                                    message = "There was an error trying to delete student info!";
                                }

                                grabFocus();
                                JOptionPane.showMessageDialog(null, message, "Deleted!", JOptionPane.INFORMATION_MESSAGE);
                            }

                            if(blurPaneListener != null) blurPaneListener.unBlurBackground();
                        });


                        // Adding menu items to the table menu
                        menu.add(viewItem);
                        menu.add(updateItem);
                        menu.add(deleteItem);

                        menu.show(e.getComponent(), e.getPoint().x, e.getPoint().y);
                    }
                }
            }
        });

        // Table Header Listener
        /*table.getTableHeader().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                int columnIndex = table.getTableHeader().columnAtPoint(e.getPoint());
                table.getRowSorter().toggleSortOrder(columnIndex + 1);
            }
        });*/
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
        tableModel.setColumnIdentifiers(new String[]{"StudentObj", "Count", "ID#", "Name", "Email", "Programme", "School"});


        // Set the selection mode
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);


        // Change the width of the count column
        resizeColumn(1, 50, 20, 50);

        // Change the width of the id column
        resizeColumn(2, 200, 130, 200);

        // Change the width of the email column
        resizeColumn(4, 300, 230, 300);


        // Hiding the first column since these will have the ids
        table.removeColumn(table.getColumnModel().getColumn(0));



        // Disabling user editing the table data on the fly
        table.setDefaultEditor(Object.class, null);


        // Setting the position of the cells value
        // DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        // centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        // table.setDefaultRenderer(Object.class, centerRenderer); // to set all cells
        // table.getColumnModel().getColumn(3).setCellRenderer(centerRenderer); // to set a single and specific cell

        // table.getColumnModel().getColumn(1).setCellRenderer(new WordWrapCellRenderer());


        // To prevent the user from re-ordering the table
        table.getTableHeader().setReorderingAllowed(false);


        // To allow table sorting by clicking on the table header on a specific column
        // table.setAutoCreateRowSorter(true);
        // TableRowSorter<TableModel> sorter = new TableRowSorter<>(table.getModel());
        // table.setRowSorter(sorter);
    }

    // Updating the table
    public void addData(Student student){
        tableModel = (DefaultTableModel) table.getModel();

        Object[] data = buildDataObject(student, null);

        // Adding the formatted data to the table model
        tableModel.addRow(data);

        // Update the column counter values
        columnCounter();

        tableModel.fireTableDataChanged();

        // Update the panel if necessary
        updateComponentsOnPanels();

        // Scroll to the bottom to show the latest student data added...
        scrollToBottom();
    }

    public void updateData(Student student){
        tableModel = (DefaultTableModel) table.getModel();

        Object[] data = buildDataObject(student, null);

        // Adding the updated data to the table model
        tableModel.setValueAt(data[0], rowSelected, 0);
        // tableModel.setValueAt(data[1], rowSelected, 1);
        tableModel.setValueAt(data[2], rowSelected, 2);
        tableModel.setValueAt(data[3], rowSelected, 3);
        tableModel.setValueAt(data[4], rowSelected, 4);
        tableModel.setValueAt(data[5], rowSelected, 5);
        tableModel.setValueAt(data[6], rowSelected, 6);

        tableModel.fireTableDataChanged();
    }

    public void deleteData(){
        tableModel = (DefaultTableModel) table.getModel();

        // Remove data at the row selected
        tableModel.removeRow(rowSelected);

        // Check how many rows are present in the table then you can probably scroll to top if anything...
        scrollToTop();

        loadTableData(new ArrayList<>(), "delete");

        // Update the panel if necessary
        updateComponentsOnPanels();
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


    /* Utility Methods */
    // This method is used to build the data object[] from the student object: also used to minimize code repetition
    private Object[] buildDataObject(Student student, Integer count){
        Object[] data = new Object[7];

        // Set up the rendering of the data...
        data[0] = student; // Hidden but will be used to manipulate the query object in the database

        if(count != null) data[1] = (count + 1);
        data[2] = student.getId();
        data[3] = student.getName();
        data[4] = student.getEmail();
        data[5] = student.getProgramme();
        data[6] = student.getSchool();

        // Highlight search/filter results
        if(keyword != null) highlightKeyword(student, data);
        if(filterBy != null) highlightFilterBy(student, data);

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
    public void highlightKeyword(Student student, Object[] data) {
        // If keyword in ID#
        if (student.getId().contains(keyword)) {
            String text = student.getId().replace(" ", "&nbsp;");
            data[2] = "<html>" + text.replace(keyword, "<strong style=\"background-color: yellow; font-weight: bold; text-decoration: underline;\">" + keyword + "</strong>") + "</html>";
        }

        // If keyword in name
        if (student.getName().toLowerCase().contains(keyword.toLowerCase())) {
            String text = student.getName().replace(" ", "&nbsp;");

            // Checking for uppercase in the student's name; if found, capitalize the keyword [starting the name]
            String keyword = BaseUtils.isUpperCase(text) ? BaseUtils.titleCase(this.keyword, true) : this.keyword;

            // Run tweak to find names containing the keyword [name containing]
            keyword = student.getName().contains(keyword) ? keyword : this.keyword;;

            data[3] = "<html>" + text.replace(keyword, "<strong style=\"background-color: yellow; font-weight: bold; text-decoration: underline;\">" + keyword + "</strong>") + "</html>";
        }

        // If keyword in email
        if (student.getEmail().toLowerCase().contains(keyword.toLowerCase())) {
            String text = student.getEmail().replace(" ", "&nbsp;");
            data[4] = "<html>" + text.replace(keyword, "<strong style=\"background-color: yellow; font-weight: bold; text-decoration: underline;\">" + keyword + "</strong>") + "</html>";
        }
    }

    // Method to highlight the filter by term
    public void highlightFilterBy(Student student, Object[] data) {
        // If filter by in programme
        if (student.getProgramme().toLowerCase().contains(filterBy.toLowerCase())) {
            String text = student.getProgramme().replace(" ", "&nbsp;");
            data[5] = "<html>" + text.replace(filterBy, "<strong style=\"background-color: yellow; font-weight: bold; text-decoration: underline;\">" + filterBy + "</strong>") + "</html>";
        }

        // If keyword in school
        if (student.getSchool().toLowerCase().contains(filterBy.toLowerCase())) {
            String text = student.getSchool().replace(" ", "&nbsp;");
            data[6] = "<html>" + text.replace(filterBy, "<strong style=\"background-color: yellow; font-weight: bold; text-decoration: underline;\">" + filterBy + "</strong>") + "</html>";
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
