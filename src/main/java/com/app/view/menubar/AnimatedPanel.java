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

package com.app.view.menubar;

import com.app.task.UpdateLabelTask;
import com.app.utils.FileUtils;
import com.app.view.customs.CPanel;

import javax.swing.*;
import java.awt.*;
import java.util.Random;


// This class will be used when generating a report to give the user a visual sense that the document is generating...
public class AnimatedPanel extends CPanel {
    private JLabel notificationLabel;
    private UpdateLabelTask updateLabelTask;

    public AnimatedPanel(){
        super();

        updateLabelTask = new UpdateLabelTask();

        notificationLabel = new JLabel("Generating report");
        notificationLabel.setHorizontalAlignment(SwingConstants.CENTER);
        notificationLabel.setFont(new Font("Arial", Font.BOLD, 15));

        add(notificationLabel, BorderLayout.SOUTH);

        setVisible(false);
    }

    // Turn on and off
    public void animate(boolean flag){
        // Setting up the animated effect
        if(flag){
            int choice = new Random().nextInt(new String[]{FileUtils.BlackAnimatedImageFilename, FileUtils.WhiteAnimatedImageFilename}.length);

            // Choose black background for 0, white for other
            if(choice == 0){
                setBackground(Color.decode("#1B1B1B"));
                notificationLabel.setForeground(Color.WHITE);

                setImage(FileUtils.BlackAnimatedImageFilename);
            }
            else{
                setBackground(Color.decode("#FFFFFF"));
                notificationLabel.setForeground(Color.BLACK);

                setImage(FileUtils.WhiteAnimatedImageFilename);
            }

            // Update text
            updateLabelTask.run(notificationLabel);
        }
        else{
            // Kill the task to update the label
            if(updateLabelTask != null && updateLabelTask.isRunning()){
                updateLabelTask.stop();

                notificationLabel.setText("Generating report");
            }
        }

        setVisible(flag);
    }

    // Update the label if/when necessary
    public void updateLabel(String text){
        notificationLabel.setText(text);

        if(updateLabelTask == null) updateLabelTask = new UpdateLabelTask();

        if(updateLabelTask.isRunning() || text.length() == 0) updateLabelTask.stop();

        if(!text.equalsIgnoreCase("Generating report")) updateLabelTask.run(notificationLabel);
    }
}
