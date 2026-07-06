package com.knowledgeai.embedding.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.knowledgeai.chunk.entity.DocumentChunk;
import com.knowledgeai.chunk.repository.DocumentChunkRepository;
import com.knowledgeai.embedding.provider.EmbeddingProvider;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EmbeddingServiceImpl implements EmbeddingService {

	private final DocumentChunkRepository chunkRepository;
	private final EmbeddingProvider embeddingProvider;
	private final ObjectMapper objectMapper;
	private final ExecutorService executorService;

	public EmbeddingServiceImpl(DocumentChunkRepository chunkRepository, EmbeddingProvider embeddingProvider,
			ObjectMapper objectMapper, ExecutorService executorService) {
		this.chunkRepository = chunkRepository;
		this.embeddingProvider = embeddingProvider;
		this.objectMapper = objectMapper;
		this.executorService = executorService;
	}

	@Override
	public void generateEmbeddings(Long documentId) {

		List<DocumentChunk> chunks = chunkRepository.findByDocumentId(documentId);

		if (chunks.isEmpty()) {
			throw new RuntimeException("No chunks found for document id : " + documentId);
		}

		log.info("Generating embeddings for {} chunks", chunks.size());

		List<Future<?>> futures = new ArrayList<>();

		for (DocumentChunk chunk : chunks) {

			Future<?> future = executorService.submit(() -> {

				try {
					List<Float> embedding = embeddingProvider.generateEmbedding(chunk.getContent());

					String embeddingJson = objectMapper.writeValueAsString(embedding);

					chunk.setEmbedding(embeddingJson);

				} catch (JsonProcessingException e) {
					throw new RuntimeException("Failed to serialize embedding", e);
				}

			});

			futures.add(future);
		}

		// Wait for all tasks to complete
		for (Future<?> future : futures) {
			try {
				future.get();
			} catch (Exception e) {
				throw new RuntimeException("Embedding generation failed", e);
			}
		}

		chunkRepository.saveAll(chunks);

		log.info("Embeddings generated successfully");
	}

}
