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

package com.app.utils;


import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatGitHubContrastIJTheme;
import com.formdev.flatlaf.ui.FlatLineBorder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import java.awt.*;


public class SwingTheme {
    private static final Logger LOGGER = LoggerFactory.getLogger(SwingTheme.class);
    private static LookAndFeel THEME;

    // Setup the look and feel for the application
    public static void setup(){
        try{
            THEME = new FlatGitHubContrastIJTheme();
            UIManager.setLookAndFeel(THEME);
        }
        catch (Exception e){
            LOGGER.error("Error inside of Theme [setup] -> " + e.getMessage());

            // Default look
            try {
                THEME = new NimbusLookAndFeel();
                UIManager.setLookAndFeel(THEME);
            }
            catch(Exception ex) {
                LOGGER.error("Failed to load nimbus theme -> " + e.getMessage());
            }
        }

        customize();
    }

    // Setup custom look and feel
    private static void customize(){
        try{
            // All Components
            UIManager.put("Component.hideMnemonics", false);
            // UIManager.put("Component.arrowType", "chevron");

            // List Component
            UIManager.put("List.selectionArc", 5);

            // Menu
            //UIManager.put("MenuItem.selectionForeground", Utils.getIconColour());
            //UIManager.put("MenuItem.acceleratorSelectionForeground", Utils.getIconColour());

            // Text Component
            UIManager.put("TextComponent.arc", 5);

            // Progress Bar
            //UIManager.put("ProgressBar.background", Color.ORANGE);
            //UIManager.put("ProgressBar.foreground", Color.BLUE);
            //UIManager.put("ProgressBar.selectionBackground", Color.RED);
            UIManager.put("ProgressBar.selectionForeground", Color.DARK_GRAY);
            UIManager.put("ProgressBar.arc", 7);

            // Scroll Pane Components
//            UIManager.put("ScrollBar.trackArc", 3);
//            UIManager.put("ScrollBar.thumbArc", 3);
//            UIManager.put("ScrollBar.showButtons", true);
//            UIManager.put("ScrollBar.buttonArrowColor", Utils.getAccentColour());
//            UIManager.put("ScrollBar.trackInsets", new Insets(2, 4, 2, 4));
//            UIManager.put("ScrollBar.thumbInsets", new Insets(2, 2, 2, 2));
//            UIManager.put("ScrollBar.track", Utils.getAccentColour());

            // Table
            //UIManager.put("Table.sortIconColor", Utils.getAccentColour());

            // Tabbed Pane
            UIManager.put("TabbedPane.showTabSeparators", true);

            /*if(staticName.equalsIgnoreCase("gradient to dark fuchsia") || staticName.equalsIgnoreCase("gradient to deep ocean") || staticName.equalsIgnoreCase("colbat2") || staticName.equalsIgnoreCase("material deep ocean contrast")){
                // UIManager.put("Table.selectionBackground", Utils.getIconColour());
                UIManager.put("Table.selectionForeground", Utils.getAccentColour());

                // List
                UIManager.put("List.selectionForeground", Utils.getAccentColour());
                UIManager.put("List.selectionInactiveForeground", Utils.getAccentColour());

                // Table
                UIManager.put("Table.selectionInactiveForeground", Utils.getAccentColour());

                // Menu
                UIManager.put("MenuItem.selectionForeground", Utils.getAccentColour());
                UIManager.put("MenuItem.acceleratorSelectionForeground", Utils.getAccentColour());

                if(staticName.equalsIgnoreCase("gradient to deep ocean")){
                    // List
                    UIManager.put("List.selectionForeground", Utils.getAccentColour());
                    UIManager.put("List.selectionInactiveForeground", Utils.getAccentColour());

                    // Table
                    UIManager.put("Table.selectionInactiveForeground", Utils.getAccentColour());

                    // Menu
                    UIManager.put("MenuItem.selectionForeground", Utils.getAccentColour());
                    UIManager.put("MenuItem.acceleratorSelectionForeground", Utils.getAccentColour());
                }

                else if(staticName.equalsIgnoreCase("colbat2")){
                    // List
                    UIManager.put("List.selectionForeground", Utils.getAccentColour());
                    UIManager.put("List.selectionInactiveForeground", Utils.getAccentColour());

                    // Table
                    UIManager.put("Table.selectionInactiveForeground", Utils.getAccentColour());

                    // Menu
                    UIManager.put("MenuItem.selectionForeground", Utils.getAccentColour());
                    UIManager.put("MenuItem.acceleratorSelectionForeground", Utils.getAccentColour());
                }
            }
            */
        }
        catch (Exception e){
            System.err.println("Error inside of Theme [customize] -> " + e.getMessage());
        }
    }

    public static void roundedBorder(JComponent component, int value){
        component.setBorder(new FlatLineBorder(new Insets(value, value, value, value), Color.GRAY, 1, 8));
    }
}
