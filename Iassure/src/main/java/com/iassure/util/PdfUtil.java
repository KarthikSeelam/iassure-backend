package com.iassure.util;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Component
public class PdfUtil {

    public List<String> extractTextByPages(String filePath) throws Exception {
        PDDocument document = PDDocument.load(new File(filePath));
        PDFTextStripper pdfStripper = new PDFTextStripper();
        List<String> textSections = new ArrayList<>();

        int pages = document.getNumberOfPages();
        for (int i = 0; i < pages; i++) {
            pdfStripper.setStartPage(i + 1);
            pdfStripper.setEndPage(i + 1);
            textSections.add(pdfStripper.getText(document));
        }
        document.close();
        return textSections;
    }
    public float cosineSimilarity(float[] vec1, float[] vec2) {
        float dotProduct = 0f, normVec1 = 0f, normVec2 = 0f;

        for (int i = 0; i < vec1.length; i++) {
            dotProduct += vec1[i] * vec2[i];
            normVec1 += (float) Math.pow(vec1[i], 2);
            normVec2 += (float) Math.pow(vec2[i], 2);
        }
        return (float) (dotProduct / (Math.sqrt(normVec1) * Math.sqrt(normVec2)));
    }
    public String extractSectionByPrompt(String text, String prompt) {
        String lowerCaseText = text.toLowerCase();
        String lowerCasePrompt = prompt.toLowerCase();

        // Find the starting point based on the prompt
        int startIndex = lowerCaseText.indexOf(lowerCasePrompt);
        if (startIndex == -1) {
            return "Section not found"; // Return if the prompt is not found in the text
        }

        // Find the end of the relevant section based on common section delimiters
        int endIndex = lowerCaseText.indexOf("\n\n", startIndex); // Use double newline as a separator
        if (endIndex == -1) {
            endIndex = text.length(); // If no delimiter is found, use the rest of the text
        }

        // Return the extracted section based on the prompt and delimiters
        return text.substring(startIndex, endIndex).trim();
    }
}
