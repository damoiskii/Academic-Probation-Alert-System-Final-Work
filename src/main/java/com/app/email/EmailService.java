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

package com.app.email;


import jakarta.mail.MessagingException;

public interface EmailService {
    void sendSimpleMessage(Email email);
    void sendAlertEmail(Email email) throws MessagingException;
}
