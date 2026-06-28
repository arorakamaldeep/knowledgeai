package com.knowledgeai.rag.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.knowledgeai.rag.dto.RagResponse;

@Service
public class RagServiceImpl implements RagService {

	private final VectorStore vectorStore;
	private final ChatClient chatClient;
	
	@Autowired
	SearchService searchService;

	@Override
	public RagResponse askQuestion(String question) {

		List<Document> documents = searchService.search(question);

		String context = documents.stream().map(Document::getText).collect(Collectors.joining("\n\n"));

		String prompt = """
				You are an AI assistant.

				Answer ONLY using the context below.

				If the answer is not present in the context, say:
				"I could not find that information in the uploaded document."

				Context:
				%s

				Question:
				%s
				""".formatted(context, question);

		String answer = chatClientBuilder.build().prompt(prompt).call().content();

		return RagResponse.builder().answer(answer).build();
	}

}
