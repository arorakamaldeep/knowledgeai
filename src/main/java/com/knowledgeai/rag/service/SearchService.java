package com.knowledgeai.rag.service;

import java.util.List;

import org.springframework.ai.document.Document;
import org.springframework.stereotype.Service;


@Service
public interface SearchService {

    List<Document> search(String question);

}