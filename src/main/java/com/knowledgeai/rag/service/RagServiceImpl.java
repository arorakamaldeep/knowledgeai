package com.knowledgeai.rag.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.document.Document;
import org.springframework.stereotype.Service;

import com.knowledgeai.rag.dto.RagResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RagServiceImpl implements RagService {

    private final SearchService searchService;
    private final ChatClient chatClient;
    private final ChatMemory chatMemory;

    public RagServiceImpl(
            SearchService searchService,
            ChatClient chatClient,
            ChatMemory chatMemory) {
        this.searchService = searchService;
        this.chatClient = chatClient;
        this.chatMemory = chatMemory;
    }

    @Override
    public RagResponse askQuestion(
            String question,
            Long documentId,
            String conversationId) {

        List<Document> documents =
                searchService.search(question, documentId);

        if (documents == null || documents.isEmpty()) {
            return RagResponse.builder()
                    .answer("I could not find any relevant information in the uploaded document.")
                    .chunksUsed(0)
                    .build();
        }

        String context = documents.stream()
                .map(Document::getText)
                .collect(Collectors.joining("\n\n"));

        String answer = chatClient.prompt()
                .system("""
                        You are KnowledgeAI, an enterprise document assistant.
                        Answer using the supplied document context.
                        Use conversation history to understand follow-up questions.
                        Do not invent facts that are not supported by the context.
                        If the context does not contain the answer, say so.

                        Document context:
                        %s
                        """.formatted(context))
                .user(question)
                .advisors(a -> a
                        .advisors(
                                MessageChatMemoryAdvisor.builder(chatMemory).build()
                        )
                        .param(ChatMemory.CONVERSATION_ID, conversationId))
                .call()
                .content();

        log.info("Generated answer using {} chunks for conversation {}",
                documents.size(), conversationId);

        return RagResponse.builder()
                .answer(answer)
                .chunksUsed(documents.size())
                .build();
    }
}