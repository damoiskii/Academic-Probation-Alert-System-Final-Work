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
import com.app.task.UpdateLabelTask;
import com.app.utils.BaseUtils;
import com.app.utils.IconUtils;
import com.app.view.customs.CJScrollPane;
import com.app.view.listeners.BlurPaneListener;
import com.app.view.listeners.LoadLazyListener;
import com.app.view.listeners.ModuleTableMenuItemListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;


public class ModuleTablePanel extends JPanel {
    private static final Logger LOGGER = LoggerFactory.getLogger(ModuleTablePanel.class);

    private Module module;

    private int rowSelected;
    private JTable table;
    private DefaultTableModel tableModel;
    private CJScrollPane scrollPane;
    private JLabel infoLabel;

    private UpdateLabelTask updateLabelTask;

    private String keyword;
    private ModuleTableMenuItemListener menuItemListener;
    private LoadLazyListener loadLazyListener;
    private BlurPaneListener blurPaneListener;


    public ModuleTablePanel(){
        super();

        setContainerProperties();

        initializeComponents();
        addComponentsToPanels();
        registerListener();
    }

    // Set menu item listener
    public void setMenuItemListener(ModuleTableMenuItemListener menuItemListener) {
        this.menuItemListener = menuItemListener;
    }

    // Set lazy loader listener
    public void setLoadLazyListener(LoadLazyListener loadLazyListener) {
        this.loadLazyListener = loadLazyListener;
    }

    // Set blurred pane listener
    public void setBlurPaneListener(BlurPaneListener blurPaneListener) {
        this.blurPaneListener = blurPaneListener;
    }

    // Set keyword
    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public void loadTableData(List<Module> dataset, String action) {
        tableModel = (DefaultTableModel) table.getModel();

        if(action != null && action.equalsIgnoreCase("refresh")) tableModel.setRowCount(0);

        if(action != null && action.equalsIgnoreCase("delete")){
            // Update the column counter values
            columnCounter();
        }
        else{
            int count = 0;

            // Insert data into Table
            for (Module module : dataset) {
                Object[] data = buildDataObject(module, count);

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
                    // This same module object will be used when the popup menu items are clicked.
                    module = (Module) table.getModel().getValueAt(table.getSelectedRow(),0);

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

                        // Set Icons
                        IconUtils.setIcon(updateItem, "edit-module");
                        IconUtils.setIcon(deleteItem, "delete");

                        // Set Tooltips
                        updateItem.setToolTipText("Select to update this module.");
                        deleteItem.setToolTipText("Select to delete this module.");

                        //Set up action listeners
                        // Updating
                        updateItem.addActionListener(e1 -> {
                            if(blurPaneListener != null) blurPaneListener.blurBackground();
                            
                            int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to update this module's info?", "Confirm Action", JOptionPane.YES_NO_OPTION);

                            if(choice != JOptionPane.YES_OPTION) {
                                if(blurPaneListener != null) blurPaneListener.unBlurBackground();
                                return;
                            }

                            if(menuItemListener != null) menuItemListener.updateModule(module);
                        });

                        // Deleting
                        deleteItem.addActionListener(e1 -> {
                            if(blurPaneListener != null) blurPaneListener.blurBackground();

                            int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this module?", "Confirm Action", JOptionPane.YES_NO_OPTION);

                            if(choice != JOptionPane.YES_OPTION) {
                                if(blurPaneListener != null) blurPaneListener.unBlurBackground();
                                return;
                            }

                            if(menuItemListener != null){
                                boolean success = menuItemListener.deleteModule(module.getCode());
                                String message;

                                if(success){
                                    message = "Module info was deleted successfully!";
                                }
                                else{
                                    message = "There was an error trying to delete module info!";
                                }

                                grabFocus();
                                JOptionPane.showMessageDialog(null, message, "Deleted!", JOptionPane.INFORMATION_MESSAGE);
                            }

                            if(blurPaneListener != null) blurPaneListener.unBlurBackground();
                        });

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
        tableModel.setColumnIdentifiers(new String[]{"ModuleObj", "Count", "Code", "Name", "Credits", "Students Taken Course"});


        // Set the selection mode
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Change the width of the count column
        resizeColumn(1, 50, 20, 50);

        // Change the width of the code column
        resizeColumn(2, 200, 130, 200);


        // Hiding the first column since these will have the ids
        table.removeColumn(table.getColumnModel().getColumn(0));



        // Hiding the first column since these will have the ids -> Second option
        //table.getColumnModel().getColumn(0).setWidth(0);
        //table.getColumnModel().getColumn(0).setMinWidth(0);
        //table.getColumnModel().getColumn(0).setMaxWidth(0);


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
    public void addData(Module module){
        tableModel = (DefaultTableModel) table.getModel();

        Object[] data = buildDataObject(module, null);

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

    public void updateData(Module module){
        tableModel = (DefaultTableModel) table.getModel();

        Object[] data = buildDataObject(module, null);

        // Adding the updated data to the table model
        tableModel.setValueAt(data[0], rowSelected, 0);
        // tableModel.setValueAt(data[1], rowSelected, 1);
        tableModel.setValueAt(data[2], rowSelected, 2);
        tableModel.setValueAt(data[3], rowSelected, 3);
        tableModel.setValueAt(data[4], rowSelected, 4);
        tableModel.setValueAt(data[5], rowSelected, 5);

        tableModel.fireTableDataChanged();
    }

    public void deleteData(){
        tableModel = (DefaultTableModel) table.getModel();

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

    // This method is used to build the data object[] from the student object: also used to minimize code repetition
    private Object[] buildDataObject(Module module, Integer count){
        Object[] data = new Object[6];

        // Set up the rendering of the data...
        data[0] = module; // Hidden but will be used to manipulate the query object in the database

        if(count != null) data[1] = (count + 1);
        data[2] = module.getCode();
        data[3] = module.getName();
        data[4] = module.getCredits();

        int amount = 0;

        if(loadLazyListener != null) {
            Module m = loadLazyListener.start(module);
            amount = m.getDetails().size();
        }

        data[5] =  amount + " student" + (amount > 1 ? "s" : "") + " taken this course";

        // Highlight search results
        if(keyword != null) highlightKeyword(module, data);

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
    public void highlightKeyword(Module module, Object[] data) {
        // If keyword in module code
        if (module.getCode().toLowerCase().contains(keyword.toLowerCase())) {
            String text = module.getCode().replace(" ", "&nbsp;");
            data[2] = "<html>" + text.replace(keyword, "<strong style=\"background-color: yellow; font-weight: bold; text-decoration: underline;\">" + keyword + "</strong>") + "</html>";
        }

        // If keyword in name
        if (module.getName().toLowerCase().contains(keyword.toLowerCase())) {
            String text = module.getName().replace(" ", "&nbsp;");

            // Checking for uppercase in the student's name; if found, capitalize the keyword [starting the name]
            String keyword = BaseUtils.isUpperCase(text) ? BaseUtils.titleCase(this.keyword, true) : this.keyword;

            // Run tweak to find names containing the keyword [name containing]
            keyword = module.getName().contains(keyword) ? keyword : this.keyword;;

            data[3] = "<html>" + text.replace(keyword, "<strong style=\"background-color: yellow; font-weight: bold; text-decoration: underline;\">" + keyword + "</strong>") + "</html>";
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
