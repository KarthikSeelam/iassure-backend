package com.iassure.util;

import com.iassure.incident.entity.IncidentDetailsById;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

@Component
public class ReportUtil {
    public static InputStream generatePdf(IncidentDetailsById incidentDetails) throws FileNotFoundException, DocumentException {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        // Create a new PDF document
        Document document = new Document();
        PdfWriter.getInstance(document, byteArrayOutputStream);
        document.open();

        // Add title
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
        Paragraph title = new Paragraph("Incident Report", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);
        document.add(new Paragraph("\n"));

        // Section: Incident Details
        addSectionHeader(document, "Incident Details");

        // Create a table with two columns: Key and Value
        PdfPTable table = new PdfPTable(2); // Two columns
        table.setWidthPercentage(100); // Table width as a percentage of page width

        // Add incident details as rows
        table.addCell("Source");
        table.addCell(incidentDetails.getSource());

        table.addCell("Category");
        table.addCell(incidentDetails.getCategory());

        table.addCell("Severity");
        table.addCell(incidentDetails.getSeverity());
        table.addCell("Department");
        table.addCell(incidentDetails.getDepartmentName());
        table.addCell("Assigned To");
        table.addCell(incidentDetails.getAssignedUser());
        table.addCell(incidentDetails.getTitle());
        table.addCell(incidentDetails.getDescription());


        // Add the table to the document
        document.add(table);
        // Close the document
        document.close();
        // Return the PDF as a byte array
        return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
    }

    private static void addSectionHeader(Document document, String header) throws DocumentException {
        Font sectionFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14);
        Paragraph sectionHeader = new Paragraph(header, sectionFont);
        sectionHeader.setSpacingBefore(10);
        sectionHeader.setSpacingAfter(5);
        document.add(sectionHeader);
    }

}
