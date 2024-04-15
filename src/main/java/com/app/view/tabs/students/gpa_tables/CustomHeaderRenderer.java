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

import com.app.utils.BaseUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class CustomHeaderRenderer extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        // Create a JPanel to contain the JLabel
        JPanel panel = new JPanel(new BorderLayout());

        // Use a JLabel to render the header cell text
        JLabel label = new JLabel("", JLabel.CENTER);
        // label.setHorizontalAlignment(JLabel.CENTER);

        // Set borders for the panel
        // panel.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY));
        panel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, false));

        // Set Tooltip of the panel
        panel.setToolTipText(value.toString());


        // Do not shorten the column name Modules and Total
        if(value.toString().equalsIgnoreCase("modules") || value.toString().equalsIgnoreCase("total")) label.setText(value.toString());
        // Shorten any course names
        else label.setText(BaseUtils.getShortenedNameCharacters(value.toString()));

        // Add the label to the panel
        panel.add(label, BorderLayout.CENTER);

        return panel;
    }
}