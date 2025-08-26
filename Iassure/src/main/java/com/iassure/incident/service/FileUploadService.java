package com.iassure.incident.service;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class FileUploadService {
    @Autowired
    private EmbeddingService embeddingService;

    @Autowired
    private QdrantService qdrantService;

    public void uploadAndProcessFiles(List<MultipartFile> files) throws IOException {
        qdrantService.createCollection("document_chunks");

        for (MultipartFile file : files) {
            String fileName = file.getOriginalFilename();

            try (PDDocument document = PDDocument.load(file.getInputStream())) {
                PDFTextStripper stripper = new PDFTextStripper();
                String text = stripper.getText(document);

                // Combine semantics and chunks for splitting
                List<String> chunks = splitIntoChunks(text, 500, 100);

                int index = 0; // For chunk numbering
                for (String chunk : chunks) {
                    // Generate embedding for the chunk
                    float[] embedding = embeddingService.generateEmbedding(chunk);

                    // Upload the chunk and its embedding to Qdrant
                    qdrantService.uploadVector(
                            "document_chunks",
                            fileName,
                            chunk,
                            embedding
                    );
                }
            }
        }
    }


    private List<String> splitIntoChunks(String text, int chunkSize, int overlap) {
        List<String> chunks = new ArrayList<>();

        // Step 1: Semantic segmentation - Split by paragraphs or sections
        String[] semanticSegments = text.split("\n\\s*\n"); // Matches one or more blank lines (paragraphs)

        for (String segment : semanticSegments) {
            // Step 2: Further split long segments into smaller chunks
            if (segment.length() > chunkSize) {
                int length = segment.length();
                for (int i = 0; i < length; i += chunkSize - overlap) {
                    chunks.add(segment.substring(i, Math.min(length, i + chunkSize)));
                }
            } else {
                // Add small segments directly
                chunks.add(segment);
            }
        }

        return chunks;
    }


}
