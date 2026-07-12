package com.knowledgeai.util;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class KeywordExtractorUtil {

    private static final Set<String> STOP_WORDS = Set.of(
            "what",
            "is",
            "are",
            "the",
            "a",
            "an",
            "of",
            "to",
            "in",
            "on",
            "for",
            "how",
            "does",
            "do",
            "explain",
            "tell",
            "me",
            "about",
            "with"
    );

    public static String extractKeywords(String question) {

        List<String> keywords = Arrays.stream(question.toLowerCase().split("\\s+"))
                .filter(word -> !STOP_WORDS.contains(word))
                .collect(Collectors.toList());

        return String.join(" ", keywords);
    }
    
    public static List<String> extractKeywordsList(String question) {

        return Arrays.stream(question.toLowerCase().split("\\s+"))
                .filter(word -> !STOP_WORDS.contains(word))
                .toList();
    }

}