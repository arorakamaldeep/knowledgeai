package com.knowledgeai.document.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.knowledgeai.document.dto.DocumentResponse;
import com.knowledgeai.document.entity.Document;
import com.knowledgeai.document.service.DocumentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/documents")
@RequiredArgsConstructor
public class DocumentController {
	@Autowired
	private DocumentService documentService;

	@PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public DocumentResponse upload(@RequestParam("file") MultipartFile file) {

		return documentService.uploadDocument(file);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Document> getDocument(@PathVariable Long id) {

		return ResponseEntity.ok(documentService.findDocumentById(id));
	}
}
