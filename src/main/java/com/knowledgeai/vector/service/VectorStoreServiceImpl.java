package com.knowledgeai.vector.service;

import java.util.List;
import java.util.Map;

import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import com.knowledgeai.chunk.entity.DocumentChunk;
import com.knowledgeai.chunk.repository.DocumentChunkRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VectorStoreServiceImpl implements VectorStoreService {

	private final VectorStore vectorStore;
	private final DocumentChunkRepository chunkRepository;

	@Override
	public void storeDocumentChunks(Long documentId) {

		List<DocumentChunk> chunks = chunkRepository.findByDocumentId(documentId);

		List<Document> documents = chunks.stream().map(chunk -> new Document(chunk.getContent(),
				Map.of("documentId", chunk.getDocumentId(), "chunkNumber", chunk.getChunkNumber()))).toList();

		vectorStore.add(documents);
	}
}