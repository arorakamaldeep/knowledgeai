package com.knowledgeai.rag.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.knowledgeai.chunk.entity.DocumentChunk;
import com.knowledgeai.chunk.repository.DocumentChunkRepository;
import com.knowledgeai.util.KeywordExtractorUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {

	private final VectorStore vectorStore;

	private final DocumentChunkRepository documentChunkRepository;

	@Override
	public List<Document> search(String question, Long documentId) {
		log.info("Searching document {} for '{}'", documentId, question);
		SearchRequest request = SearchRequest.builder().query(question).topK(5)
				.filterExpression("documentId == " + documentId).build();

		List<Document> vectorResults = vectorStore.similaritySearch(request);

		log.info("Vector search returned {} results", vectorResults.size());

		// String keywords = KeywordExtractorUtil.extractKeywords(question);

		List<String> keywords = KeywordExtractorUtil.extractKeywordsList(question);

		log.info("Extracted keywords : {}", keywords);
		// Keyword Search
		List<Document> keywordResults = keywordSearch(keywords, documentId);

		log.info("Keyword search returned {} results", keywordResults.size());

		// Merge Results
		List<Document> finalResults = mergeResults(vectorResults, keywordResults);

		log.info("Hybrid search returned {} unique results", finalResults.size());

		return finalResults;
	}

	private List<Document> keywordSearch(List<String> keywords, Long documentId) {
		Map<String, Document> resultMap = new LinkedHashMap<>();

		for (String keyword : keywords) {
			log.info("Searching keyword '{}'", keyword);
			List<DocumentChunk> chunks = documentChunkRepository
					.findTop5ByDocumentIdAndContentContainingIgnoreCase(documentId, keyword);

			for (DocumentChunk chunk : chunks) {
				log.info("Keyword '{}' returned {} chunks",
				        keyword,
				        chunks.size());
				Document document = new Document(chunk.getContent(),
						Map.of("documentId", chunk.getDocumentId(), "chunkNumber", chunk.getChunkNumber()));

				resultMap.putIfAbsent(chunk.getContent(), document);
			}
		}

		return new ArrayList<>(resultMap.values());
	}

	/**
	 * Merges vector and keyword search results while removing duplicates.
	 */
	private List<Document> mergeResults(List<Document> vectorResults, List<Document> keywordResults) {

		Map<String, Document> merged = new LinkedHashMap<>();

		vectorResults.forEach(doc -> merged.put(doc.getText(), doc));

		keywordResults.forEach(doc -> merged.putIfAbsent(doc.getText(), doc));

		return merged.values().stream().limit(5).toList();
	}
}