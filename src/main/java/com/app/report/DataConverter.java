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

package com.app.report;

import com.app.controller.ModuleDetailController;
import com.app.controller.PrologController;
import com.app.model.ModuleDetail;
import com.app.utils.BaseUtils;
import com.app.utils.FileUtils;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.FileOutputStream;

import java.util.*;
import java.util.List;


@Component
@RequiredArgsConstructor
@Data
public class DataConverter {
    private final Logger logger = LoggerFactory.getLogger(DataConverter.class);

    private final ModuleDetailController moduleDetailController;
    private final PrologController prologController;

    private Double gpa;
    private String year;
    private int count = 0;


    // Using the table as a source when converting data to the pdf
    public boolean generateTable(){
        // Reset counting tracker
        count = 0;

        // Column names
        String[] columnNames = {"Student ID", "Student Name", "Semester 1 GPA", "Semester 2 GPA", "Semester 3 GPA", "Cumulative GPA"};

        // Create DefaultTableModel with column names
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        // Retrieve all module details by school year
        List<ModuleDetail> details = moduleDetailController.findAllModuleDetailsByYear(year);

        // Data structures to capture unique data
        Map<String, String> studentTracker = new HashMap<>();

        // To capture grades per semester
        Map<String, List<Double>> semesterOneScores = new HashMap<>();
        Map<String, List<Double>> semesterTwoScores = new HashMap<>();
        Map<String, List<Double>> semesterThreeScores = new HashMap<>();

        // To capture credits taken per semester
        Map<String, List<Integer>> semesterOneCredits = new HashMap<>();
        Map<String, List<Integer>> semesterTwoCredits = new HashMap<>();
        Map<String, List<Integer>> semesterThreeCredits = new HashMap<>();


        // Loop to record all distinctive students with their gpa scores per semester
        for (ModuleDetail detail : details) {
            String studentID = detail.getStudent().getId();

            // Keeping track of the student's ID # and name
            studentTracker.put(studentID, BaseUtils.titleCase(detail.getStudent().getName(), true));

            // Assigning the necessary semester score to the relevant list
            if(detail.getSemester().equalsIgnoreCase("semester 1") || detail.getSemester().equalsIgnoreCase("semester one") ){
                List<Double> scores = semesterOneScores.getOrDefault(studentID, new ArrayList<>());
                List<Integer> credits = semesterOneCredits.getOrDefault(studentID, new ArrayList<>());

                // Add score to the list for the students uniquely
                scores.add(detail.getGradePoints());

                // Add credits for the module
                if(detail.getModule() != null) credits.add(detail.getModule().getCredits());

                // Update the maps
                semesterOneScores.put(studentID, scores);
                semesterOneCredits.put(studentID, credits);
            }
            else if(detail.getSemester().equalsIgnoreCase("semester 2") || detail.getSemester().equalsIgnoreCase("semester two") ){
                List<Double> scores = semesterTwoScores.getOrDefault(studentID, new ArrayList<>());
                List<Integer> credits = semesterTwoCredits.getOrDefault(studentID, new ArrayList<>());

                // Add score to the list for the students uniquely
                scores.add(detail.getGradePoints());

                // Add credits for the module
                if(detail.getModule() != null) credits.add(detail.getModule().getCredits());

                // Update the maps
                semesterTwoScores.put(studentID, scores);
                semesterTwoCredits.put(studentID, credits);
            }
            else if(detail.getSemester().equalsIgnoreCase("semester 3") || detail.getSemester().equalsIgnoreCase("semester three") ){
                List<Double> scores = semesterThreeScores.getOrDefault(studentID, new ArrayList<>());
                List<Integer> credits = semesterThreeCredits.getOrDefault(studentID, new ArrayList<>());

                // Add score to the list for the students uniquely
                scores.add(detail.getGradePoints());

                // Add credits for the module
                if(detail.getModule() != null) credits.add(detail.getModule().getCredits());

                // Update the maps
                semesterThreeScores.put(studentID, scores);
                semesterThreeCredits.put(studentID, credits);
            }
        }


        // Populating table with the student's data --> Key: Student ID, Value: Student Name
        studentTracker.forEach((key, value) -> {
            // Using prolog to calculate the semester's and cumulative gpa.
            // Calculating total credits using prolog
            double semesterOneTotalCredits = prologController.getSum(null, semesterOneCredits.getOrDefault(key, new ArrayList<>()));
            double semesterTwoTotalCredits = prologController.getSum(null, semesterTwoCredits.getOrDefault(key, new ArrayList<>()));
            double semesterThreeTotalCredits = prologController.getSum(null, semesterThreeCredits.getOrDefault(key, new ArrayList<>()));

            // Calculating total points earned using prolog
            double semesterOneTotalPointsEarned = prologController.totalPointsEarned(semesterOneScores.getOrDefault(key, new ArrayList<>()));;
            double semesterTwoTotalPointsEarned = prologController.totalPointsEarned(semesterTwoScores.getOrDefault(key, new ArrayList<>()));
            double semesterThreeTotalPointsEarned = prologController.totalPointsEarned(semesterThreeScores.getOrDefault(key, new ArrayList<>()));

            // Calculating cumulative GPA per semester using prolog
            double gpaSemester1 = prologController.getCumulativeGPA(semesterOneTotalPointsEarned, semesterOneTotalCredits);
            double gpaSemester2 = prologController.getCumulativeGPA(semesterTwoTotalPointsEarned, semesterTwoTotalCredits);
            double gpaSemester3 = prologController.getCumulativeGPA(semesterThreeTotalPointsEarned, semesterThreeTotalCredits);

            // Calculating cumulative GPA using prolog
            double cumulativeGPA = 0.0;

            // If semester one grades are available or only one available -> divide by 1
            if(gpaSemester1 > 0.0){
                cumulativeGPA = prologController.getCumulativeGPA((gpaSemester1), 1);
            }

            // If semester two grades are available  or only two available -> divide by 2
            if(gpaSemester2 > 0.0){
                cumulativeGPA = prologController.getCumulativeGPA((gpaSemester1 + gpaSemester2), 2);
            }

            // If semester three grades are available or all three available -> divide by 3
            if(gpaSemester3 > 0.0){
                cumulativeGPA = prologController.getCumulativeGPA((gpaSemester1 + gpaSemester2 + gpaSemester3), 3);
            }


            // If cumulative GPA is less than or equal to the default GPA then add to list
            if(cumulativeGPA <= gpa){
                // Add data to the table model
                model.addRow(new Object[]{
                        key,
                        value,
                        (gpaSemester1 > 0.0 ? String.format("%.2f", gpaSemester1): "-"),
                        (gpaSemester2 > 0.0 ? String.format("%.2f", gpaSemester2): "-"),
                        (gpaSemester3 > 0.0 ? String.format("%.2f", gpaSemester3): "-"),
                        (cumulativeGPA > 0.0 ? String.format("%.2f", cumulativeGPA): "-"),
                });
                // Counting students
                count++;
            }
        });


        // Convert table with data to pdf format
        // return exportTableToPDF(new JTable(model));
        try{
            return exportTableToPDF(buildTable(model));
        }
        catch (Exception e){
            logger.error("Error Generating Table: " + e.getMessage());
            return false;
        }
    }

    // Create table with sorting order
    @SuppressWarnings({"unchecked", "rawtypes"})
    private JTable buildTable(DefaultTableModel model) throws Exception {
        JTable table = new JTable(model);
        table.setAutoCreateRowSorter(true);

        // Sort the "Cumulative GPA" - "5th" column in descending order initially
        ArrayList<RowSorter.SortKey> list = new ArrayList<>();
        DefaultRowSorter sorter = ((DefaultRowSorter) table.getRowSorter());
        sorter.setSortsOnUpdates(true);
        list.add(new RowSorter.SortKey(5, SortOrder.DESCENDING));
        sorter.setSortKeys(list);
        sorter.sort();

        return table;
    }

    private boolean exportTableToPDF(JTable table) {
        try {
            logger.info("Now generating report...");

            String titleString = "University of Technology, Jamaica\nAcademic Probation Alert GPA Report\nYear: " + year + "\nGPA: " + String.format("%.2f", gpa) + "\n\n";

            Document document = new Document();
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(FileUtils.GeneratedReportFilename));

            // Create an instance of the page event handler
            PageNumberEventHandler eventHandler = new PageNumberEventHandler();

            // Set the event handler to the writer
            writer.setPageEvent(eventHandler);

            document.open();

            // Add image
            try{
                Image image = Image.getInstance("src/main/resources/assets/img/report-logo.png");

                // Set the absolute width and height of the image
                image.scaleAbsolute(200f, 100f); // Adjust the values as needed
                image.setAlignment(Element.ALIGN_CENTER);
                document.add(image);
            }
            catch (Exception ignore){}

            // Add title to the PDF
            Font titleFont = FontFactory.getFont(FontFactory.TIMES_BOLD, 14);
            Paragraph title = new Paragraph(titleString, titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            // Add empty line
            // document.add(Chunk.NEWLINE);

            PdfPTable pdfTable = new PdfPTable(table.getColumnCount());

            // Set table width as percentage of page width
            pdfTable.setWidthPercentage(100);

            // Adding the data to the document table
            addTableHeader(pdfTable, table);
            addTableRowData(pdfTable, table);

            // Set column widths based on page size
            float[] columnWidths = calculateColumnWidths(document, table.getColumnCount());
            pdfTable.setWidths(columnWidths);

            // Adding table to the PDF
            document.add(pdfTable);

            // Add count text to the PDF
            String footerString = "\nNumber of students: " + count + ".\n\n";
            Font footerFont = FontFactory.getFont(FontFactory.TIMES_BOLD, 12);
            Paragraph footer = new Paragraph(footerString, footerFont);
            footer.setAlignment(Element.ALIGN_LEFT);
            document.add(footer);

            document.close();

            // Reset counting tracker
            // count = 0;

            // JOptionPane.showMessageDialog(null, "Table data exported to PDF successfully.");
            logger.info("Report generated successfully!\n");

            return true;
        }
        catch (Exception e) {
            logger.error("Report Generation Conversion Error: " + e.getMessage());

            return false;
        }
    }

    // Writing all columns to the pdf
    private void addTableHeader(PdfPTable pdfTable, JTable table) {
        // Old
        /*for (int column = 0; column < table.getColumnCount(); column++) {
            pdfTable.addCell(new PdfPCell(new Phrase(table.getColumnName(column))));
        }*/

        for (int column = 0; column < table.getColumnCount(); column++) {
            Font font = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
            font.setColor(new BaseColor(252, 208, 1)); // yellow

            PdfPCell cell = new PdfPCell(new Phrase(table.getColumnName(column), font));

            // Set background
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            // cell.setBackgroundColor(new BaseColor(254,131,0));
            // cell.setBackgroundColor(BaseColor.ORANGE);
            cell.setBackgroundColor(new BaseColor(15, 3, 79)); // dark blue

            // Center text in cell
            setCellAlignment(cell);

            pdfTable.addCell(cell);
        }
    }

    // Writing all rows to the pdf
    private void addTableRowData(PdfPTable pdfTable, JTable table) {
        // Old
        /*for (int row = 0; row < table.getRowCount(); row++) {
            for (int column = 0; column < table.getColumnCount(); column++) {
                pdfTable.addCell(table.getValueAt(row, column).toString());
            }
        }*/

        for (int row = 0; row < table.getRowCount(); row++) {
            for (int column = 0; column < table.getColumnCount(); column++) {
                Font font = new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL);
                PdfPCell cell = new PdfPCell(new Phrase(table.getValueAt(row, column).toString(), font));

                // Center text in cell
                setCellAlignment(cell);

                pdfTable.addCell(cell);
            }
        }
    }

    // Method to set alignment for a PdfPCell
    private static void setCellAlignment(PdfPCell cell) {
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);

        cell.setPaddingBottom(5);
        cell.setPaddingRight(5);
        cell.setPaddingLeft(5);
    }

    // Custom page event handler class to add page numbers
    private static class PageNumberEventHandler extends PdfPageEventHelper {
        /*@Override
        public void onEndPage(PdfWriter writer, Document document) {
            PdfContentByte cb = writer.getDirectContent();

            Font font = new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL);
            Phrase footer = new Phrase("Page " + writer.getPageNumber(), font);

            // Calculate the width of the page number text
            float width = footer.getContent().length();

            // Place the page number at the end of the page
            ColumnText.showTextAligned(cb, Element.ALIGN_RIGHT, footer, document.right() - document.rightMargin() - width, document.bottom() - 10, 0);
        }*/

        @Override
        public void onEndPage(PdfWriter writer, Document document) {
            PdfContentByte cb = writer.getDirectContent();
            // Set font for the page number
            Font pageNumberFont = new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL);
            Phrase footer = new Phrase("Page " + writer.getPageNumber(), pageNumberFont);

            // Set font for the additional text (left-aligned)
            Font dateTextFont = new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL);
            Phrase date = new Phrase("Generated on " + BaseUtils.currentDateAndTimeInString(), dateTextFont);

            // Calculate the width of the combined text
            float totalWidth = footer.getContent().length();// + date.getContent().length();

            // Place the combined text at the end of the page
            ColumnText.showTextAligned(cb, Element.ALIGN_LEFT, date, document.left() + document.leftMargin(), document.bottom() - 10, 0);
            ColumnText.showTextAligned(cb, Element.ALIGN_RIGHT, footer, document.right() - document.rightMargin() - totalWidth, document.bottom() - 10, 0);
        }
    }

    private float[] calculateColumnWidths(Document document, int columnCount) {
        float pageWidth = document.getPageSize().getWidth();
        float[] columnWidths = new float[columnCount];

        // Divide the page width equally among columns
        float equalWidth = pageWidth / columnCount;

        for (int i = 0; i < columnCount; i++) {
            columnWidths[i] = equalWidth;
        }

        return columnWidths;
    }
}
