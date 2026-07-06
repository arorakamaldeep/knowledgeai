package com.knowledgeai.rag.service;

import java.util.List;

import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {

	private final VectorStore vectorStore;

	@Override
	public List<Document> search(String question, Long documentId) {
		log.info("Searching document {} for '{}'", documentId, question);
		SearchRequest request = SearchRequest.builder().query(question).topK(5)
				.filterExpression("documentId == " + documentId).build();
		return vectorStore.similaritySearch(request);
	}
}