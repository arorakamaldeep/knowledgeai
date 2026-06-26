package com.knowledgeai.embedding.provider;

import java.util.ArrayList;
import java.util.List;

import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.stereotype.Service;

@Service
public class OpenAIEmbeddingProvider implements EmbeddingProvider {

	private final EmbeddingModel embeddingModel;

	public OpenAIEmbeddingProvider(EmbeddingModel embeddingModel) {
		this.embeddingModel = embeddingModel;
	}

	@Override
	public List<Float> generateEmbedding(String text) {
		float[] embedding = embeddingModel.embed(text);

		List<Float> list = new ArrayList<>();

		for (float value : embedding) {
			list.add(value);
		}

		return list;
	}

}
