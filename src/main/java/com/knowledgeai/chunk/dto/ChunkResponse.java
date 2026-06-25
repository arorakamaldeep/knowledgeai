package com.knowledgeai.chunk.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChunkResponse {

	private Long documentId;

	private Integer chunksCreated;
}
