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

package com.app.view.customs;

import com.app.utils.FileUtils;

import javax.swing.*;
import java.awt.*;


public class CPanel extends JPanel {
    private JLabel animatedLabel;

    public CPanel() {
        super();

        setLayout(new BorderLayout());

        // Create a JLabel to hold the animated image
        animatedLabel = new JLabel();
        animatedLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Add the JLabel to the panel
        add(animatedLabel, BorderLayout.CENTER);
    }

    // Setting the filename of the animated image
    public void setImage(String filename){
        animatedLabel.setIcon(new ImageIcon(FileUtils.getImage(filename)));
    }
}
