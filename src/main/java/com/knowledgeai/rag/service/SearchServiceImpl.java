package com.knowledgeai.rag.service;

import java.util.List;

import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {

	private final VectorStore vectorStore;

	@Override
	public List<Document> search(String question) {

		SearchRequest request = SearchRequest.builder().query(question).topK(5).build();

		return vectorStore.similaritySearch(request);
	}
}