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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;


public class FileUtils {
    private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);

    // public static final String PrologFilename = "knowledge_base.pl";
    public static final String PrologFilename = "testing.pl";
    public static final String GeneratedReportFilename = "Academic Probation Alert GPA Report.pdf";

    public static final String BlackAnimatedImageFilename = "animated-black-bg.gif";
    public static final String WhiteAnimatedImageFilename = "animated-white-bg.gif";

    private static Toolkit toolkit = Toolkit.getDefaultToolkit();
    private static final String resourcesPath = "/assets/";


    // Create file
    public static void createFile(String path) {
        File file = new File(path);

        try{
            // If file doesn't exist then create it
            if(!Files.exists(file.toPath())) Files.writeString(file.toPath(), "", StandardOpenOption.CREATE);
        }
        catch (Exception e){
            logger.error("There was an error creating the file...");
        }
    }

    // Write to file
    public static void writeToFile(File file, String text){
        try{
            if(Files.exists(file.toPath())) {
                Files.writeString(file.toPath(), text + "\n", StandardOpenOption.APPEND);
            }
        }
        catch (Exception e){
            logger.error("There was an error writing to the file...");
        }
    }

    // Rename file
    public static void renameTo(File file, String filename) throws IOException {
        Path path = Paths.get(file.getPath());
        Files.move(path, path.resolveSibling(filename), REPLACE_EXISTING);
    }

    // Delete file
    public static void deleteFile(File file){
        try {
            Files.deleteIfExists(file.toPath());
        }
        catch (IOException e) {
            logger.error("Invalid permissions.");
        }
    }


    // This method will get images from the assets folder
    public static Image getImage(String filename) {
        Image image = null;
        boolean error = false;

        try{
            URL url = FileUtils.class.getClassLoader().getResource(resourcesPath + "img/" + filename);

            image = ImageIO.read(new File(url.getFile()));
        }
        catch (IOException | NullPointerException e){
            error = true;
        }

        if(error){
            try{
                image = toolkit.getImage("src/main/resources/assets/img/" + filename);
            }
            catch (NullPointerException e){
                logger.error("Image not found!");
            }
        }

        return image;
    }


    // Will be used to open file from the application GUI based on user click
    public static void openDocument(String filePath) throws IOException, IllegalStateException {
        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.OPEN)) {
            File file = new File(filePath);
            Desktop.getDesktop().open(file);
        }
        else {
            throw new UnsupportedOperationException("Desktop API not supported or opening not supported for this file type");
        }
    }

    // Backup document opener if the first choice method (openDocument) above fails
    public static void openDocumentBackup(String filePath) throws IOException {
        // Build the command based on OS (assuming typical defaults)
        String command = "";

        if (isWindows()) {
            command = "cmd /c start " + filePath;
        }
        else if (isMac()) {
            command = "open " + filePath;
        }
        else if (isLinux()) {
            // Handle Linux (assuming typical terminal app)
            command = "xdg-open " + filePath;
        }
        else {
            // Handle other operating systems (if needed)
            throw new UnsupportedOperationException("Platform not supported");
        }

        // Execute the command
        Runtime.getRuntime().exec(command);
    }

    // Checking platforms
    private static boolean isWindows() {
        return System.getProperty("os.name").toLowerCase().startsWith("win");
    }

    private static boolean isMac() {
        return System.getProperty("os.name").toLowerCase().startsWith("mac");
    }

    public static boolean isLinux() {
        return System.getProperty("os.name").toLowerCase().contains("linux");
    }
}
