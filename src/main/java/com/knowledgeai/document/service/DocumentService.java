package com.knowledgeai.document.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.knowledgeai.document.dto.DocumentResponse;
import com.knowledgeai.document.entity.Document;

@Service
public interface DocumentService {

	public DocumentResponse uploadDocument(MultipartFile file);

	public Document findDocumentById(Long id);

}
