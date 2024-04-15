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

package com.app.config;

import com.app.controller.*;
import com.app.service.LoadLazyCollectionServiceImpl;
import com.app.view.PostLoaderFrame;
import com.app.view.PreLoaderFrame;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@RequiredArgsConstructor
public class SwingConfiguration {
    private final PrologController prologController;
    private final PopulateData populateData;
    private final StudentController studentController;
    private final ModuleController moduleController;
    private final LoadLazyCollectionServiceImpl loadLazyCollectionService;
    private final ModuleDetailController moduleDetailController;
    private final ReportController reportController;
    private final ProbationController probationController;

    @Bean
    public PreLoaderFrame getPreLoaderFrame() {
        // System.setProperty("java.awt.headless", "false"); // To help with enabling java swing to run with spring boot

        PreLoaderFrame frame = new PreLoaderFrame();
        frame.setVisible(true);
        frame.fill();

        return frame;
    }

    @Bean
    public PostLoaderFrame getPostLoaderFrame() {
        return new PostLoaderFrame(prologController, populateData, studentController, moduleController, loadLazyCollectionService, moduleDetailController, reportController, probationController);
    }
}
