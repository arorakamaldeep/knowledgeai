package com.knowledgeai.chunk.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.knowledgeai.chunk.entity.DocumentChunk;

public interface DocumentChunkRepository extends JpaRepository<DocumentChunk, Long> {

	List<DocumentChunk> findByDocumentId(Long documentId);
}