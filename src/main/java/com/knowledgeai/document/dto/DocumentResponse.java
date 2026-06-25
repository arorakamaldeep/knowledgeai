package com.knowledgeai.document.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Builder
@Getter
@Setter
public class DocumentResponse {

	private Long id;
	private String fileName;
	private String message;
}
