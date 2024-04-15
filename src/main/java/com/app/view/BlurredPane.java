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

package com.app.view;


import javax.swing.*;
import java.awt.*;


public class BlurredPane extends JPanel {
    public BlurredPane(){
        super();

        setContainerProperties();

        initializeComponents();
        addComponentsToPanels();
        registerListener();

    }

    // Initializing the components
    private void initializeComponents(){

    }

    // Adding the components to the panels
    private void addComponentsToPanels(){

    }

    // Setting the container properties
    private void setContainerProperties(){
        setOpaque(false); // Make the panel transparent
        setBackground(new Color(0, 0, 0, 128)); // Set a semi-transparent
        setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0, 255), 2));

//        HashMap<String, String> preferenceSettings = Utils.loadPreferences("layerbase-preference-settings");
//        if(preferenceSettings != null){
//            String accentColor = preferenceSettings.getOrDefault("theme-accent-color", null);
//
//            if(accentColor != null) setBorder(BorderFactory.createLineBorder(Color.decode(accentColor), 2));
//        }

        setVisible(false);
    }

    // Setting up listeners
    private void registerListener(){

    }

    // Source: https://stackoverflow.com/a/37724933/15978450
    @Override
    public final void paint(Graphics g) {
        final Color old = g.getColor();
        g.setColor(getBackground());
        g.fillRect(0, 0, getSize().width, getSize().height);
        g.setColor(old);

        super.paint(g);
    }
}
