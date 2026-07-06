package com.knowledgeai.rag.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.stereotype.Service;

import com.knowledgeai.rag.dto.RagResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class RagServiceImpl implements RagService {

	private final SearchService searchService;
	private final ChatClient chatClient;

	@Override
	public RagResponse askQuestion(String question, Long documentId) {

		List<Document> documents = searchService.search(question, documentId);
		log.info("Generating answer using {} chunks", documents.size());

		if (documents == null || documents.isEmpty()) {
			return RagResponse.builder().answer("I could not find any relevant information in the uploaded document.")
					.chunksUsed(0).build();
		}

		String context = documents.stream().map(Document::getText) // or getContent() if needed
				.collect(Collectors.joining("\n\n"));

		String prompt = """
				Answer this question using the following context.
				Context:
				%s
				Question:
				%s
				   """.formatted(context, question);

		String answer = chatClient.prompt().system(prompt).user(question).call().content();

		return RagResponse.builder().answer(answer).chunksUsed(documents.size()).build();

	}
}