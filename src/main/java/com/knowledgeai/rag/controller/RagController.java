package com.knowledgeai.rag.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.knowledgeai.rag.dto.RagRequest;
import com.knowledgeai.rag.dto.RagResponse;
import com.knowledgeai.rag.service.RagService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/rag")
@RequiredArgsConstructor
public class RagController {

	private final RagService ragService;

	@PostMapping
	public RagResponse ask(@RequestBody RagRequest request) {

		return ragService.askQuestion(request.getQuestion());

	}
}