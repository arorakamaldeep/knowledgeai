package com.knowledgeai.rag.service;

import org.springframework.stereotype.Service;

import com.knowledgeai.rag.dto.RagResponse;

@Service
public interface RagService {

	RagResponse askQuestion(String question);
	

}