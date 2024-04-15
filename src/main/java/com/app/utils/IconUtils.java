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

import org.kordamp.ikonli.materialdesign2.*;
import org.kordamp.ikonli.swing.FontIcon;

import javax.swing.*;
import java.awt.*;


// Class to control icon distribution
// Source: https://kordamp.org/ikonli/cheat-sheet-materialdesign2.html
public class IconUtils {

    public static Color getHighlightColor(){
        return Color.decode("#ff008d");
    }

    // Buttons
    public static void setIcon(JButton button, String name){
        FontIcon icon = chooseIcon(name);

        button.setIcon(icon);
        // button.setForeground(color);
    }

    // Menu items
    public static void setIcon(JMenuItem menuItem, String name){
        FontIcon icon = chooseIcon(name);

        menuItem.setIcon(icon);
        // menuItem.setForeground(color);
    }

    // Set Icon
    public static FontIcon chooseIcon(String name){
        FontIcon icon;
        int size = 15;
        // Color color = getHighlightColor();

        if(name.equalsIgnoreCase("add") || name.equalsIgnoreCase("plus")){
            icon = FontIcon.of(MaterialDesignA.ACCOUNT_PLUS);
        }
        else if(name.equalsIgnoreCase("generate")){
            icon = FontIcon.of(MaterialDesignC.CREATION);
        }
        else if(name.equalsIgnoreCase("close")){
            icon = FontIcon.of(MaterialDesignC.CLOSE);
        }
        else if(name.equalsIgnoreCase("edit") || name.equalsIgnoreCase("update")){
            icon = FontIcon.of(MaterialDesignA.ACCOUNT_EDIT);
        }
        else if(name.equalsIgnoreCase("refresh")){
            icon = FontIcon.of(MaterialDesignR.REFRESH);
        }
        else if(name.equalsIgnoreCase("search")){
            icon = FontIcon.of(MaterialDesignA.ACCOUNT_SEARCH);
        }
        else if(name.equalsIgnoreCase("delete")){
            icon = FontIcon.of(MaterialDesignD.DELETE);
        }
        else if(name.equalsIgnoreCase("clear")){
            icon = FontIcon.of(MaterialDesignD.DELETE_SWEEP);
        }
        else if(name.equalsIgnoreCase("filter")){
            icon = FontIcon.of(MaterialDesignF.FILTER_VARIANT);
        }
        else if(name.equalsIgnoreCase("reset")){
            icon = FontIcon.of(MaterialDesignN.NOTIFICATION_CLEAR_ALL);
        }
        else if(name.equalsIgnoreCase("view")){
            icon = FontIcon.of(MaterialDesignE.EYE);
        }
        else if(name.equalsIgnoreCase("save")){
            icon = FontIcon.of(MaterialDesignC.CONTENT_SAVE);
        }
        else if(name.equalsIgnoreCase("save-outline")){
            icon = FontIcon.of(MaterialDesignC.CONTENT_SAVE_OUTLINE);
        }
        else if(name.equalsIgnoreCase("exit")){
            icon = FontIcon.of(MaterialDesignE.EXIT_TO_APP);
        }
        else if(name.equalsIgnoreCase("add-module")){
            icon = FontIcon.of(MaterialDesignL.LAYERS_PLUS);
        }
        else if(name.equalsIgnoreCase("search-module")){
            icon = FontIcon.of(MaterialDesignL.LAYERS_SEARCH);
        }
        else if(name.equalsIgnoreCase("edit-module")){
            icon = FontIcon.of(MaterialDesignS.SQUARE_EDIT_OUTLINE);
        }
        else if(name.equalsIgnoreCase("run")){
            icon = FontIcon.of(MaterialDesignP.PLAY);
        }
        else if(name.equalsIgnoreCase("proceed")){
            icon = FontIcon.of(MaterialDesignA.ARROW_RIGHT_THICK);
        }
        else if(name.equalsIgnoreCase("report")){
            icon = FontIcon.of(MaterialDesignA.AUTO_FIX);
        }
        else if(name.equalsIgnoreCase("set")){
            icon = FontIcon.of(MaterialDesignS.SET_MERGE);
        }
        else{
            icon = FontIcon.of(MaterialDesignG.GOOGLE_NEARBY);
        }

        icon.setIconSize(size);
        //icon.setIconColor(color);

        return icon;
    }
}
