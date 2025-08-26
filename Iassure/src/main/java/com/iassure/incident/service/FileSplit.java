package com.iassure.incident.service;


import com.iassure.incident.dto.SplitIndex;
import com.iassure.incident.dto.SplitPDFRequest;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class FileSplit {

    public void splitPDF(SplitPDFRequest request) {
        try {
            // Load the PDF document
            String fileName = (request.getOriginalFilePath() + request.getOriginalFile());
            File file = new File(fileName);
            if (!file.exists()) {
                System.out.println("File does not exist.");
                return;
            }

            PDDocument document = PDDocument.load(file);
            System.out.println("The PDF has " + document.getNumberOfPages() + " pages.");

            for (SplitIndex index : request.getSplitIndexes()) {
                String[] range = index.getPageRange().split("-");
                int startPage = Integer.parseInt(range[0]);
                int endPage = Integer.parseInt(range[1]);

                if (startPage < 1 || endPage > document.getNumberOfPages() || startPage > endPage) {
                    System.out.println("Invalid page range: " + index.getPageRange());
                    continue;
                }

                // Create a new document for the split range
                PDDocument splitDoc = new PDDocument();

                for (int i = startPage; i <= endPage; i++) {
                    splitDoc.addPage(document.getPage(i - 1));
                }

                // Save the split PDF
                String newFileName = request.getOriginalFilePath() + request.getOriginalFile().substring(0, request.getOriginalFile().lastIndexOf('.')) + "_" + (startPage - 1) + ".pdf";
                splitDoc.save(newFileName);
                splitDoc.close();


                System.out.println("Saved: " + newFileName);
            }

            document.close();
            System.out.println("PDF splitting completed.");

        } catch (IOException e) {
            System.out.println("An error occurred while processing the PDF: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Invalid page range format. Please enter valid ranges like '1-3'.");
        }
    }
}
