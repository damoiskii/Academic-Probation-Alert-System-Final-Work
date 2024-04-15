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


import javax.swing.*;
import java.awt.*;


public class CProgressBar extends JProgressBar {

    public CProgressBar(){
        super();

        setValue(0);
        setStringPainted(true);

        //setBackground(new Color(255, 114, 94, 100));
        //setForeground(new Color(255, 100, 0, 100));
        setFont(new Font("Arial", Font.PLAIN, 10));

        setVisible(false);
    }


    public void run(String text){
        setString(text);
        setIndeterminate(true);

        setVisible(true);
    }

    public void stop(){
        setVisible(false);

        setString("");
        setIndeterminate(false);
    }
}
