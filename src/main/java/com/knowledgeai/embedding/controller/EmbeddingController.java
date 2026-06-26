package com.knowledgeai.embedding.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.knowledgeai.embedding.service.EmbeddingService;

@RestController
@RequestMapping("/api/v1/embeddings")
public class EmbeddingController {

	@Autowired
	EmbeddingService embeddingService;

	@PostMapping("/{documentId}")
	public ResponseEntity<String> generateEmbedding(@PathVariable Long documentId) {

		embeddingService.generateEmbeddings(documentId);

		return ResponseEntity.ok("Embeddings generated successfully");
	}
}
