package com.knowledgeai.chunk.comtroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.knowledgeai.chunk.dto.ChunkResponse;
import com.knowledgeai.chunk.entity.DocumentChunk;
import com.knowledgeai.chunk.service.ChunkService;

@RestController
@RequestMapping("/api/v1/documents")
public class ChunkController {

	@Autowired
	ChunkService chunkService;

	@PostMapping("/{id}/chunks")
	public ResponseEntity<ChunkResponse> createChunks(@PathVariable Long id) {

		return ResponseEntity.ok(chunkService.createChunks(id));
	}

	@GetMapping("/{id}/chunks")
	public ResponseEntity<List<DocumentChunk>> getChunks(@PathVariable Long id) {

		return ResponseEntity.ok(chunkService.getChunks(id));
	}
}
