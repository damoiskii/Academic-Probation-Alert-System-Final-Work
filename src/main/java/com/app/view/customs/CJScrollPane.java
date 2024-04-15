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
import javax.swing.border.Border;


public class CJScrollPane extends JScrollPane {
    private Border olderBorder, newBorder;

    // Constructors
    public CJScrollPane (){
        super();

        setupFieldProperties();
    }

    public CJScrollPane (JComponent component){
        super(component);

        setupFieldProperties();
    }

    public Border getOlderBorder() {
        return olderBorder;
    }

    public void setOlderBorder(Border olderBorder) {
        this.olderBorder = olderBorder;
    }

    public Border getNewBorder() {
        return newBorder;
    }

    public void setNewBorder(Border newBorder) {
        this.newBorder = newBorder;

        setBorder(newBorder);
    }

    // Settings
    public void setupFieldProperties(){
        getVerticalScrollBar().setUnitIncrement(20);
    }

    // Scroll to top
    public void resetScroll(){
        getVerticalScrollBar().setValue(0);

        // getViewport().setViewPosition(new Point(0,0));
    }

    // Scroll to bottom
    public void scrollToBottom(){
        getVerticalScrollBar().setValue(getVerticalScrollBar().getMaximum());
    }
}
