package com.knowledgeai.rag.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.stereotype.Service;

import com.knowledgeai.rag.dto.RagResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RagServiceImpl implements RagService {

	private final SearchService searchService;
	private final ChatClient chatClient;

	@Override
	public RagResponse askQuestion(String question) {

		List<Document> documents = searchService.search(question);

		if (documents == null || documents.isEmpty()) {
			return RagResponse.builder().answer("I could not find any relevant information in the uploaded document.")
					.chunksUsed(0).build();
		}

		String context = documents.stream().map(Document::getText) // or getContent() if needed
				.collect(Collectors.joining("\n\n"));

		String prompt = """
				You are KnowledgeAI.

				Answer ONLY using the provided context.

				If the answer is not present in the context, reply:
				"I could not find that information in the uploaded document."

				Context:
				%s

				Question:
				%s
				""".formatted(context, question);

		String answer = chatClient.prompt().user(prompt).call().content();

		return RagResponse.builder().answer(answer).build();

	}
}