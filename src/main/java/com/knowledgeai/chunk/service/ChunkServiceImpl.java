package com.knowledgeai.chunk.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.knowledgeai.chunk.dto.ChunkResponse;
import com.knowledgeai.chunk.entity.DocumentChunk;
import com.knowledgeai.chunk.repository.DocumentChunkRepository;
import com.knowledgeai.document.entity.Document;
import com.knowledgeai.document.repository.DocumentRepository;
import com.knowledgeai.document.util.TextChunkUtil;

@Service
public class ChunkServiceImpl implements ChunkService {

	@Autowired
	DocumentRepository documentRepository;

	@Autowired
	DocumentChunkRepository documentChunkRepository;

	@Override
	public ChunkResponse createChunks(Long documentId) {
		Document document = documentRepository.findById(documentId)
				.orElseThrow(() -> new RuntimeException("Document not found"));

		List<String> chunks = TextChunkUtil.chunkText(document.getExtractedText(), 1000);

		int chunkNo = 1;

		for (String chunkText : chunks) {

			DocumentChunk chunk = DocumentChunk.builder().documentId(documentId).chunkNumber(chunkNo++)
					.content(chunkText).build();

			documentChunkRepository.save(chunk);
		}

		return ChunkResponse.builder().documentId(documentId).chunksCreated(chunks.size()).build();
	}

	@Override
	public List<DocumentChunk> getChunks(Long documentId) {
		return documentChunkRepository.findByDocumentId(documentId);
	}

}
