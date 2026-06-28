package com.knowledgeai.vector.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.knowledgeai.vector.service.VectorStoreService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/vector")
@RequiredArgsConstructor
public class VectorStoreController {

	private final VectorStoreService vectorStoreService;

	@PostMapping("/{documentId}")
	public String generate(@PathVariable Long documentId) {

		vectorStoreService.storeDocumentChunks(documentId);

		return "Stored Successfully";
	}

}