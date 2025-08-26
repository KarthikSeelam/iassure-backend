package com.iassure.incident.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SearchService {

    @Autowired
    private EmbeddingService embeddingService;

    @Autowired
    private QdrantService qdrantService;

    public String search(String query) {
        float[] queryEmbedding = embeddingService.generateEmbedding(query);
        return qdrantService.search("document_chunks", queryEmbedding,null,3);
    }
}
