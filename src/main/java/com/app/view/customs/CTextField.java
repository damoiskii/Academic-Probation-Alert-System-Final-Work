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
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;


public class CTextField extends JTextField {
    private String defaultText;

    // Default Constructor
    public CTextField(){
        super();

        setupFieldProperties();
        registerListeners();
    }

    // Primary Constructors
    public CTextField(String text){
        super(text);

        defaultText = text;

        setupFieldProperties();
        registerListeners();
    }

    public CTextField(String text, int column){
        super(text, column);

        defaultText = text;

        setupFieldProperties();
        registerListeners();
    }

    // This method will be utilized to avoid code repetition
    private void setupFieldProperties(){
        setForeground(Color.GRAY);


    }

    // Registering the focus listener within this class
    private void registerListeners(){
        // To allow the field's default text to behave like a placeholder
        addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                // If the username text field is not empty and is not equal to the default text then return
                if(getText().length() > 1 && !getText().equalsIgnoreCase(defaultText)) return;

                setText("");
                setForeground(Color.BLACK);
            }

            @Override
            public void focusLost(FocusEvent e) {
                // If the username text field is not empty then return
                if(getText().length() > 0) return;

                setText(defaultText);
                setForeground(Color.GRAY);
            }
        });
    }

    // Getters & Setters
    public String getDefaultText() {
        return defaultText;
    }

    public void setDefaultText(String defaultText) {
        this.defaultText = defaultText;
        setText(defaultText);
    }

    public void reset(){
        setText(defaultText);
        setForeground(Color.GRAY);
    }

    // Change the foreground whenever needed
    public void writingForeground(){
        setForeground(Color.BLACK);
    }

    public boolean isTextsDifferent(){
        return getText().length() <= 0 || !getText().equalsIgnoreCase(defaultText);
    }
}
