package com.knowledgeai.chunk.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.knowledgeai.chunk.dto.ChunkResponse;
import com.knowledgeai.chunk.entity.DocumentChunk;

@Service
public interface ChunkService {
	ChunkResponse createChunks(Long documentId);

	List<DocumentChunk> getChunks(Long documentId);
}
