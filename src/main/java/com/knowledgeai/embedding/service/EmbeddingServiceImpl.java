package com.knowledgeai.embedding.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.knowledgeai.chunk.entity.DocumentChunk;
import com.knowledgeai.chunk.repository.DocumentChunkRepository;
import com.knowledgeai.embedding.provider.EmbeddingProvider;

@Service
public class EmbeddingServiceImpl implements EmbeddingService {

	private final DocumentChunkRepository chunkRepository;
	private final EmbeddingProvider embeddingProvider;
	private final ObjectMapper objectMapper;

	public EmbeddingServiceImpl(DocumentChunkRepository chunkRepository, EmbeddingProvider embeddingProvider,
			ObjectMapper objectMapper) {
		this.chunkRepository = chunkRepository;
		this.embeddingProvider = embeddingProvider;
		this.objectMapper = objectMapper;
	}

	@Override
	public void generateEmbeddings(Long documentId) {
		List<DocumentChunk> chunks = chunkRepository.findByDocumentId(documentId);

		if (chunks.isEmpty()) {
			throw new RuntimeException("No chunks found for document id : " + documentId);
		}

		try {

			for (DocumentChunk chunk : chunks) {

				List<Float> embedding = embeddingProvider.generateEmbedding(chunk.getContent());

				String embeddingJson = objectMapper.writeValueAsString(embedding);

				chunk.setEmbedding(embeddingJson);
			}

			chunkRepository.saveAll(chunks);

		} catch (JsonProcessingException e) {
			throw new RuntimeException("Failed to serialize embedding", e);
		}
	}

}
