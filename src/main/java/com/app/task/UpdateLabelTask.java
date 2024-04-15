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

package com.app.task;

import javax.swing.*;
import java.awt.event.ActionListener;


public class UpdateLabelTask {
    private Timer timer;
    private String text, updatedText;
    private int counter = 0;

    public void run(JLabel label){
        text = label.getText();

        ActionListener action = (e) -> {
            if(updatedText == null) updatedText = text;

            if(counter <= 3){
                updatedText += ".";
            }

            counter++;

            if(counter > 3) {
                updatedText = text;
                counter = 0;
            }

            label.setText(updatedText);
        };

        timer = new Timer(1000, action);
        timer.start();
    }

    public boolean isRunning(){
        // If the timer is currently running...
        return timer != null && timer.isRunning();
    }

    public void stop(){
        if(timer != null && timer.isRunning()){
            timer.stop();
        }

        text = null;
        updatedText = null;
    }
}