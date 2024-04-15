package com.app;

import com.app.utils.SwingTheme;
import com.app.view.PostLoaderFrame;
import com.app.view.PreLoaderFrame;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import javax.swing.*;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        System.setProperty("java.awt.headless", "false"); // To help with enabling java swing to run with spring boot
        SwingTheme.setup();

        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);

        // Dispose the loading frame after finish loading...
        PreLoaderFrame loaderFrame = (PreLoaderFrame) context.getBean("getPreLoaderFrame");
        loaderFrame.dispose();

        // Showing the PostLoaderFrame after finish loading -> change to log in frame in the future...
        SwingUtilities.invokeLater(() -> {
            PostLoaderFrame postLoaderFrame = (PostLoaderFrame) context.getBean("getPostLoaderFrame");
            postLoaderFrame.loadDataFromProlog();
            postLoaderFrame.setVisible(true);
        });
    }

}
